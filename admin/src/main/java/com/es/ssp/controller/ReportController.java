
package com.es.ssp.controller;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.util.DateConvertUtils;
import cn.org.rapid_framework.util.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.es.ssp.annotation.ResumeList;
import com.es.ssp.model.GlobalSetting;
import com.es.ssp.model.Report;
import com.es.ssp.model.ReportType;
import com.es.ssp.query.ReportQuery;
import com.es.ssp.service.*;
import common.base.BaseController;
import common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static common.util.DateFormats.DATE_TIME_MIN_FORMAT;
import static common.util.SpringMVCUtils.toModelMap;


/**
 * @author pettyzhao email:515280634(a)qq.com
 * @version 1.0
 * @since 1.0
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "createTime desc";

	@Autowired
	private ReportManager reportManager;
	@Autowired
	private ReportTypeManager reportTypeManager;
	@Autowired
	private OperateRecordManager operateRecordManager;
	@Autowired
	private GlobalSettingManager globalSettingManager;
	@Autowired
	private ReportRecordManager reportRecordManager;
	@Autowired
	private ReportTaskManager reportTaskManager;
	
	private final String LIST_ACTION = "redirect:/report";
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}

    @ModelAttribute
    public void init(ModelMap model,Long reportId) {
		if(!ObjectUtils.isNullOrEmptyString(reportId)){
		    model.addAttribute("report",reportManager.getById(reportId));
		    }
	}
	
	/** 列表 */
	@RequestMapping
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	public String index(ModelMap model,ReportQuery query,String startTimeStr,String endTimeStr) {
		Report report = new Report();
		String type = "信息汇总";
		if (query.getStatus() != null){
			report.setStatus(query.getStatus());
			type = report.getStatusString();
		}
		if (StringUtils.isNotBlank(startTimeStr)){
			query.setStartTime(DateConvertUtils.parse(startTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000);
		}
		if (StringUtils.isNotBlank(endTimeStr)){
			query.setEndTime(DateConvertUtils.parse(endTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000);
		}
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		Page page = reportManager.findPageOfFans(query);
		GlobalSetting globalSetting = globalSettingManager.getOne();
		model.addAttribute("isShow",isShow(query,globalSetting));
		model.addAttribute("globalSet",globalSetting);
		model.addAttribute("reportTypeList",reportTypeManager.getAllReport());
		model.addAllAttributes(toModelMap(page, query));
		model.addAttribute("type",type);
		return "report/list";
	}
	
	/** 显示 */
	@RequestMapping(value="/{id}")
	public String show(ModelMap model,@PathVariable Long id) throws Exception {
		Report report = (Report)reportManager.getById(id);
		model.addAttribute("report",report);
		return "report/show";
	}


	/** 编辑 */
	/*@RequestMapping(value="/{id}/edit")
	public String edit(ModelMap model,@PathVariable Long id) throws Exception {
		Report report = (Report)reportManager.getById(id);
		model.addAttribute("report",report);
		return "report/edit";
	}*/
	
	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	/*@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(ModelMap model,@PathVariable Long id,Report report,BindingResult errors) throws Exception {
		try {
			reportManager.update(report);
		}catch(ConstraintViolationException e) {
			convert(e, errors);
			return  "report/edit";
		}catch(ValidationException e) {
			return  "report/edit";
		}
		return LIST_ACTION;
	}*/
	

	@RequestMapping(value = "/{id}/chooseType")
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	@ResponseBody
	public String chooseType(@PathVariable Long id,Integer reportType,String type){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			if (StringUtils.isNotBlank(type)){
				if (type.equals("1")){
					report.setReportType1(reportType);
					List<ReportType> list = reportTypeManager.getChildReport(reportType);
					if (list != null && list.size()>0){
						JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
						object.put("list",array);
					}
				}else {
					report.setReportType2(reportType);
				}
			}
			reportManager.update(report);
			object.put("flag",true);
			return object.toString();
		}
		object.put("flag",false);
		return object.toString();
	}

	@RequestMapping("/getReports")
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	@ResponseBody
	public String getReportTypes(Integer[] typeIds,Integer[] reportIds){
		JSONArray jsonArray = new JSONArray();
		if (typeIds.length > 0){
			for(int i=0;i<reportIds.length;i++){
				List<ReportType> typeList = reportTypeManager.getChildReport(typeIds[i]);
				JSONObject jsonObject = new JSONObject();
				JSONArray array = JSONArray.parseArray(JSON.toJSONString(typeList));
				Report report = reportManager.getById(new Long(reportIds[i]));
				jsonObject.put("id",typeIds[i]);
				jsonObject.put("type2",report.getReportType2());
				jsonObject.put("reportId",reportIds[i]);
				jsonObject.put("arr",array);
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray.toJSONString();

	}

	/**
	 * 初审通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/firstCheckPass")
	public String firstCheckPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 0){
				report.setStatus(1);
				//report = setPrizeStatus(report);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
				reportTaskManager.statistics(2,report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[0]修改为[1]");
			}
		}
		return "";
	}

	/**
	 * 取消初审通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/cancelFirstCheckPass")
	public String cancelFirstCheckPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 1){
				report.setStatus(0);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
				reportTaskManager.statistics(2,report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[1]修改为[0]");
			}
		}
		return "";
	}

	/**
	 * 初审不通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/firstCheckNoPass")
	public String firstCheckNoPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 0){
				report.setStatus(2);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[0]修改为[2]");
			}
		}
		return "";
	}

	/**
	 * 取消初审不通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin","secondLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/cancelFirstCheckNoPass")
	public String cancelFirstCheckNoPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 2){
				report.setStatus(0);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[2]修改为[0]");
			}
		}
		return "";
	}

	/**
	 * 复审通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/reviewPass")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String reviewPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 1){
				report.setStatus(3);
				//report = setPrizeStatus(report);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[1]修改为[3]");
			}
		}
		return "";
	}

	/**
	 * 取消复审通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/cancelReviewPass")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String cancelReviewPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 3){
				report.setStatus(1);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[3]修改为[1]");
			}
		}
		return "";
	}

	/**
	 * 复审不通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/reviewNoPass")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String reviewNoPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 1){
				report.setStatus(4);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[1]修改为[4]");
			}
		}
		return "";
	}

	/**
	 * 取消复审未通过
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/cancelReviewNoPass")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String cancelReviewNoPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 4){
				report.setStatus(1);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[4]修改为[1]");
			}
		}
		return "";
	}

	/**
	 * 核实通过，待整改
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/verifyPass")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String verifyPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 3){
				report.setStatus(5);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[3]修改为[5]");
			}
		}
		return "";
	}

	/**
	 * 撤回核实
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/cancelVerifyPass")
	public String cancelVerifyPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 5){
				report.setStatus(3);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[5]修改为[3]");
			}
		}
		return "";
	}


	/**
	 * 信息不准确
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/verifyNoPass")
	public String verifyNoPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 3){
				report.setStatus(6);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[3]修改为[6]");
			}
		}
		return "";
	}

	/**
	 * 撤销信息不准确操作
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@RequestMapping(value = "/{id}/cancelVerifyNoPass")
	public String cancelVerifyNoPass(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null){
			if (report.getStatus() == 6){
				report.setStatus(3);
				reportManager.update(report);
                reportRecordManager.reportStatistics(report.getReportId());
                operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[6]修改为[3]");
			}
		}
		return "";
	}

	/**
	 * 已整改操作
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/processed")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String processed(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null && report.getStatus()==5){
			report.setStatus(7);
			reportManager.update(report);
            reportRecordManager.reportStatistics(report.getReportId());
            operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[5]修改为[7]");
		}
		return "";
	}

	/**
	 * 取消已整改
	 * @param model
	 * @param id
	 * @return
	 */
	@ResumeList
	@RequestMapping(value = "/{id}/cancelProcessed")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String cancelProcessed(ModelMap model,@PathVariable Long id){
		Report report = reportManager.getById(id);
		if (report != null && report.getStatus()==7){
			report.setStatus(5);
			reportManager.update(report);
            reportRecordManager.reportStatistics(report.getReportId());
            operateRecordManager.create((Integer) getSessionAttr("userId"),id,"状态由[7]修改为[5]");
		}
		return "";
	}


	/**
	 * 备注
	 * @param id
	 * @param remarkText
	 * @return
	 */
	@RequestMapping(value = "/{id}/remark")
	@ResponseBody
	public String remark(@PathVariable Long id,String remarkText){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			report.setRemark(remarkText);
			reportManager.update(report);
			object.put("flag",true);
		}else {
			object.put("flag",false);
		}
		return object.toString();
	}

	/**
	 * 回复用户
	 * @param id
	 * @param replyText
	 * @return
	 */
	@RequestMapping(value = "/{id}/reply")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@ResponseBody
	public String reply(@PathVariable Long id,String replyText){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			report.setReply(replyText);
			reportManager.update(report);
			object.put("flag",true);
		}else {
			object.put("flag",false);
		}
		return object.toString();
	}

	@RequestMapping(value = "/{id}/getRemark")
	@ResponseBody
	public String getRemark(@PathVariable Long id){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			object.put("remark",report.getRemark());
			object.put("flag",true);
		}else {
			object.put("flag",false);
		}
		return object.toString();
	}

	@RequestMapping(value = "/{id}/getReply")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@ResponseBody
	public String getReply(@PathVariable Long id){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			object.put("reply",report.getReply());
			object.put("flag",true);
		}else {
			object.put("flag",false);
		}
		return object.toString();
	}

	@RequestMapping(value = "/{id}/firstPrize")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@ResponseBody
	public String firstPrize(@PathVariable Long id){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			if (report.getPrizeStatus1() == 0){
				report.setPrizeStatus1(1);
				reportManager.update(report);
				object.put("flag",true);
			}else {
				object.put("message","初次奖励已发放");
				object.put("flag",false);
			}
		}else {
			object.put("flag",false);
		}
		return object.toString();
	}

	@RequestMapping(value = "/{id}/secondPrize")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	@ResponseBody
	public String secondPrize(@PathVariable Long id){
		Report report = reportManager.getById(id);
		JSONObject object = new JSONObject();
		if (report != null){
			if (report.getPrizeStatus2() == 0){
				report.setPrizeStatus2(1);
				reportManager.update(report);
				object.put("flag",true);
			}else {
				object.put("message","追加奖励已发放");
				object.put("flag",false);
			}
		}else {
			object.put("flag",false);
		}
		return object.toString();
	}

	@RequestMapping(value = "/showVideo")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String showVideo(String src,ModelMap map){

		map.addAttribute("src",src);
		return "report/video";
	}

	@RequestMapping(value = "/showAudio")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public String showAudio(String src,ModelMap map){
		map.addAttribute("src",src);
		return "report/audio";
	}

	@RequestMapping(value = "/export")
	@RequiresRoles(value = {"admin","firstLevelAdmin"},logical = Logical.OR)
	public void export(ModelMap model, ReportQuery query, String startTimeStr, String endTimeStr, HttpServletResponse response){
		String typeStr = getParam("type");
		Report report = new Report();
		String type = "";
		if (StringUtils.isNotBlank(typeStr)){
			report.setStatus(Integer.parseInt(typeStr));
			type = report.getStatusString();
			query.setStatus(Integer.parseInt(typeStr));
		}
		if (StringUtils.isNotBlank(startTimeStr)){
			query.setStartTime(DateConvertUtils.parse(startTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000);
		}
		if (StringUtils.isNotBlank(endTimeStr)){
			query.setEndTime(DateConvertUtils.parse(endTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class).getTime()/1000);
		}
		setDefaultSortColumns(query,DEFAULT_SORT_COLUMNS);
		List<Report> list = reportManager.findForExport(query);
		String[] heads = new String[]{"openid","微信昵称","信息提交时间","姓名","联系方式","发生时间","发生地点","发生事件描述","发生事件语音描述","佐证材料（图片）","佐证材料视频","状态"};
		String exportTime = "";
		if (query.isByTime()){
			if (StringUtils.isNotBlank(startTimeStr) && StringUtils.isNotBlank(endTimeStr)){
				exportTime = CommonUtils.formatDate(DateConvertUtils.parse(startTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class),"yyyy年MM月dd日HH点mm分")
						+ "到" + CommonUtils.formatDate(DateConvertUtils.parse(endTimeStr, DATE_TIME_MIN_FORMAT,java.util.Date.class),"yyyy年MM月dd日HH点mm分");
			}
		}else {
			exportTime = CommonUtils.formatDate(new Date(),"yyyy年MM月dd日HH点mm分");
		}
		try {
			reportManager.export(heads,list,getOutputStream(response, exportTime+"举报信息.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShow(ReportQuery query,GlobalSetting globalSetting){
		if (query.getStatus() != null){
			if (globalSetting.getReceiveType() == 1){
				if (query.getStatus() == 1 || query.getStatus() == 3 || query.getStatus() == 5 || query.getStatus() == 7){
					return true;
				}
			}else if (globalSetting.getReceiveType() ==2){
				if (query.getStatus() == 3 || query.getStatus() == 5 || query.getStatus() == 7){
					return true;
				}
			}
		}else {
			return true;
		}
		return false;
	}

	public Report setPrizeStatus(Report report){
		GlobalSetting globalSetting = globalSettingManager.getOne();
		if (globalSetting != null){
			if (globalSetting.getReceiveType() == 1){
				report.setPrizeStatus1(1);
			}
		}
		return report;
	}
}

