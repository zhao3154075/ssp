package com.es.ssp.controller;

import com.alibaba.fastjson.JSONObject;
import com.es.ssp.annotation.NeedFans;
import com.es.ssp.model.*;
import com.es.ssp.service.*;
import com.es.ssp.wechatapi.WechatApi;
import common.base.BaseController;
import common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class IndexController extends BaseController{

    @Autowired
    private WechatApi wechatApi;
    @Autowired
    private ReportManager reportManager;
    @Autowired
    private FansManager fansManager;
    @Autowired
    private GlobalSettingManager globalSettingManager;
    @Autowired
    private RedpackManager redpackManager;
    @Autowired
    private PrizeRecordManager prizeRecordManager;
    @Autowired
    private ReportRecordManager reportRecordManager;
    @Autowired
    private ReportStatisticsManager reportStatisticsManager;
    @Autowired
    private ReportStatisticsYearManager reportStatisticsYearManager;
    @Value("${web.realPath}")
    private String realPath;
    private static final String videoPath="video/";


    @RequestMapping("/index")
    @NeedFans
    public String index(){
        return "index";
    }

    @RequestMapping("/reportList")
    @NeedFans
    public String reportList(){
        Fans fans=getAttribute("fans");
        if(fans!=null){
            ReportStatistics reportStatistics=reportStatisticsManager.getByFansId(fans.getFansId());
            ReportStatisticsYear reportStatisticsYear=reportStatisticsYearManager.getByFansId(fans.getFansId());
            List<Report> reportList=reportManager.findAllByFansId(fans.getFansId());
            for(Report report:reportList){
                if(report.getReply()!=null){
                    report.setReply(report.getReply().replaceAll("\n", "<br/>"));
                }
            }
            setAttribute("list",reportList);
            if(reportStatistics!=null){
                setAttribute("thisYearAmount",reportStatistics.getTotalAmount());
            }

            if(reportStatisticsYear!=null){
                setAttribute("totalAmount",reportStatisticsYear.getTotalAmount());
            }
            GlobalSetting globalSetting=globalSettingManager.getGlobalSetting();
            setAttribute("dayLimit",globalSetting);
        }else{
            return "no_fans";
        }
        return "report_list";
    }

    @RequestMapping("/checkReceive")
    @NeedFans
    @ResponseBody
    public String checkReceive(){
        Fans fans=getAttribute("fans");
        String result="";
        if(fans!=null){
            GlobalSetting globalSetting=globalSettingManager.getGlobalSetting();
            Long dayTotalCount=prizeRecordManager.getDayTotalCount(fans.getFansId(),0);
            if(dayTotalCount>=globalSetting.getDayLimit()){
                result+="0";
            }
            dayTotalCount=prizeRecordManager.getDayTotalCount(fans.getFansId(),1);
            if(dayTotalCount>=globalSetting.getDayLimit()){
                result+="1";
            }
        }
        return result;
    }

    @RequestMapping("/report")
    @NeedFans
    public String report(){
        String method=getRequest().getMethod().toUpperCase();
        Fans fans=getAttribute("fans");
        if(method.equals("GET")){
            if(fans==null){
                return "no_fans";
            }
            return "report";
        }else if(method.equals("POST")){
            if(fans!=null){
                String vrifyCode=getParam("vrifyCode");
                if(!getSessionAttr("vrifyCode").equals(vrifyCode)){
                    return "forward:/message?code=-2&msg=invalid vrifyCode";
                }
                Report report=new Report();
                report.setCreateTime(System.currentTimeMillis()/1000);
                report.setEventDesc(getParam("eventDesc"));
                report.setFansId(fans.getFansId());
                report.setHappenPlace(getParam("happenPlace"));
                report.setHappenTime(DateUtils.getDate(getParam("happenTimeString").replace("T"," ").substring(0,16),"yyyy-MM-dd HH:mm").getTime()/1000);
                report.setStatus(0);
                report.setIsHide(Integer.parseInt(getParam("isHide")));
                report.setPrizeStatus1(0);
                report.setPrizeStatus2(0);
                report.setDescVideo(getParam("descVideo"));
                if(getParam("descType").equals("1")){
                    String url=wechatApi.mediaGet(getParam("voiceServerId"));
                    if(url!=null){
                        report.setDescVoice(url);
                    }
                }
                String imgServerIds[]=getParams("imgServerIds");
                String images="";
                if(imgServerIds!=null){
                    for(String imgServerId:imgServerIds){
                        if(!images.equals("")){
                            images+=",";
                        }
                        String url=wechatApi.mediaGet(imgServerId);
                        if(url!=null){
                            images+=url;
                        }else{
                            images+=imgServerId;
                        }
                    }
                    report.setDescImages(images);
                }
                GlobalSetting globalSetting=globalSettingManager.getGlobalSetting();
                int receiveType=globalSetting.getReceiveType();
                if(receiveType==0) {
                    report.setPrizeStatus1(1);
                }
                reportManager.save(report);
                reportRecordManager.reportStatistics(report.getReportId());
                fans.setRealName(getParam("realName"));
                fans.setMobile(getParam("mobile"));
                fansManager.update(fans);
                return "forward:/message?code="+receiveType+"&msg="+report.getReportId();
            }else{
                return "forward:/message?code=-1&msg=no fans";
            }
        }
        return "";
    }

    @RequestMapping("/reloadImage/{reportId}")
    @ResponseBody
    public String reloadImage(@PathVariable Long reportId){
        Report report=reportManager.getById(reportId);
        if(report!=null){
            String descImages=report.getDescImages();
            String images[]=descImages.split(",");
            if(images.length==0){
                return "no image";
            }
            for(String image:images){
                if(!image.contains(".jpg")){
                    String url=wechatApi.mediaGet(image);
                    if(url!=null){
                        descImages=descImages.replace(image,url);
                    }else{
                        return "fail";
                    }
                }
            }
            report.setDescImages(descImages);
            reportManager.update(report);
        }else{
            return "no report";
        }
        return "success";
    }

    @RequestMapping("/receive/{reportId}/{type}")
    @NeedFans
    public String receive(@PathVariable Long reportId,@PathVariable Integer type){
        Fans fans=getAttribute("fans");
        if(fans!=null){
            Report report=reportManager.getById(reportId);
            if(report!=null&&report.getFansId().equals(fans.getFansId())){
                GlobalSetting globalSetting=globalSettingManager.getGlobalSetting();
                if(type==0){
                    setAttribute("amount",globalSetting.getFirstAmount());
                    if(report.getPrizeStatus1()==1||report.getPrizeStatus1()==2){
                        PrizeRecord prizeRecord=prizeRecordManager.findSentRecord(report.getReportId(),0);
                        if(prizeRecord!=null){
                            setAttribute("amount",prizeRecord.getAmount());
                        }else{
                            Long dayTotalCount=prizeRecordManager.getDayTotalCount(fans.getFansId(),type);
                            if(dayTotalCount>=globalSetting.getDayLimit()){
                                setAttribute("dayLimit",globalSetting.getDayLimit());
                                return "day_limit";
                            }
                            report.setPrizeStatus1(2);
                            reportManager.update(report);
                            prizeRecord=new PrizeRecord();
                            String billno=wechatApi.generatMchBillno();
                            prizeRecord.setBillno(billno);
                            prizeRecord.setAmount(globalSetting.getFirstAmount());
                            prizeRecord.setCreateTime(System.currentTimeMillis()/1000);
                            prizeRecord.setFansId(report.getFansId());
                            prizeRecord.setReportId(report.getReportId());
                            prizeRecord.setType(type);
                            prizeRecord.setStatus(0);
                            prizeRecord.setReportTime(report.getCreateTime());
                            prizeRecordManager.save(prizeRecord);
                            redpackManager.sendRedpack(prizeRecord.getRecordId());
                        }

                    }
                }else if(type==1){
                    ReportType reportType=report.getReportType();
                    if(reportType!=null){
                        int amount=reportType.getAmount();
                        setAttribute("amount",amount);
                        if(report.getPrizeStatus2()==1||report.getPrizeStatus2()==2){
                            PrizeRecord prizeRecord=prizeRecordManager.findSentRecord(report.getReportId(),1);
                            if(prizeRecord!=null){
                                setAttribute("amount",prizeRecord.getAmount());
                            }else{
                                Long dayTotalCount=prizeRecordManager.getDayTotalCount(fans.getFansId(),type);
                                if(dayTotalCount>=globalSetting.getDayLimit()){
                                    setAttribute("dayLimit",globalSetting.getDayLimit());
                                    return "day_limit";
                                }
                                report.setPrizeStatus2(2);
                                reportManager.update(report);
                                prizeRecord=new PrizeRecord();
                                String billno=wechatApi.generatMchBillno();
                                prizeRecord.setBillno(billno);
                                prizeRecord.setAmount(amount);
                                prizeRecord.setCreateTime(System.currentTimeMillis()/1000);
                                prizeRecord.setFansId(report.getFansId());
                                prizeRecord.setReportId(report.getReportId());
                                prizeRecord.setType(type);
                                prizeRecord.setStatus(0);
                                prizeRecord.setReportTime(report.getCreateTime());
                                prizeRecordManager.save(prizeRecord);
                                redpackManager.sendRedpack(prizeRecord.getRecordId());
                            }
                        }
                    }
                }

            }
        }else{
            return "no_fans";
        }
        return "receive";
    }

    @PostMapping("/upload")
    @ResponseBody
    public JSONObject upload(MultipartFile file){
        if(file.isEmpty()){
            return jsonMsg(-1,"文件不允许为空");
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String filepath=realPath+videoPath;
            File dir=new File(filepath);
            if(!dir.exists()) {
                dir.mkdir();
            }
            String fileName=file.getOriginalFilename();
            Path path = Paths.get(filepath + fileName);
            Files.write(path, bytes);
            return jsonMsg(0,videoPath + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return jsonMsg(-1,e.getMessage());
        }

    }


    @RequestMapping("/message")
    @ResponseBody
    public String message(Integer code,String msg){
        return jsonMsg(code,msg).toString();
    }

    /**
     * 获取分享签名参数
     * @return
     */
    @RequestMapping("/jsconfig")
    @ResponseBody
    public String jsconfig(){
        return wechatApi.generateConfigJson(getParam("url"),"true".equals(getParam("debug")));
    }
}
