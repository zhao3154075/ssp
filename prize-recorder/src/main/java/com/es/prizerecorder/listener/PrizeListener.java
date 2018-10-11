package com.es.prizerecorder.listener;

import com.es.prizerecorder.service.FansManager;
import com.es.prizerecorder.service.PrizeRecordManager;
import com.es.ssp.model.Fans;
import com.es.ssp.model.PrizeRecord;
import com.es.ssp.pojo.Redpack;
import com.es.ssp.wechatapi.WechatApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weixin.popular.bean.paymch.GethbinfoResult;
import weixin.popular.bean.paymch.SendredpackResult;

@Component
@RabbitListener(queues = "redpack")
public class PrizeListener {

    @Autowired
    private WechatApi wechatApi;
    @Autowired
    private FansManager fansManager;
    @Autowired
    private PrizeRecordManager prizeRecordManager;
    @Value("${wechat.redpack.first-remark}")
    private String firstRemark;
    @Value("${wechat.redpack.second-remark}")
    private String secondRemark;

    @RabbitHandler
    public void process(Redpack redpack){
        PrizeRecord prizeRecord;
        if(redpack.getRecordId()!=null){
            prizeRecord=prizeRecordManager.getById(redpack.getRecordId());
            if(prizeRecord==null){
                return;
            }
            if(redpack.getType()==1){//type为1表示重发
                GethbinfoResult result=wechatApi.gethbinfo(prizeRecord.getBillno());//重发的时候先检查该单号是否确实发送失败
                if(result==null||result.getReturn_code().equals("FAIL")){//如果结果为空或者请求失败直接返回
                    return;
                }
                if(result.getResult_code().equals("SUCCESS")&& StringUtils.isEmpty(result.getReason())){//如果该单号其实已经发送成功，直接修改发送记录的状态
                    prizeRecord.setStatus(1);
                    prizeRecord.setErrorInfo("");
                    prizeRecordManager.update(prizeRecord);
                    return;
                }
            }
            prizeRecord.setStatus(2);
            prizeRecord.setErrorInfo("");
            prizeRecordManager.update(prizeRecord);
        }else{
            return;
        }
        Fans fans=fansManager.getById(prizeRecord.getFansId());
        if(fans==null){
            return;
        }
        SendredpackResult result=wechatApi.sendRedpack(prizeRecord.getBillno(),fans.getOpenId(),prizeRecord.getAmount(),prizeRecord.getType()==0?firstRemark:secondRemark);
        if(result==null){
            prizeRecord.setStatus(-1);
            prizeRecord.setErrorInfo("系统错误，请开发人员检查服务器日志");
        }else{
            if(result.getReturn_code().equals("SUCCESS")){
                if(result.getResult_code().equals("SUCCESS")){
                    prizeRecord.setStatus(1);
                    prizeRecord.setErrorInfo("");
                }else{
                    prizeRecord.setStatus(-1);
                    prizeRecord.setErrorInfo(result.getErr_code_des());
                }
            }else{
                prizeRecord.setStatus(-1);
                prizeRecord.setErrorInfo(result.getReturn_msg());
            }
        }

        prizeRecordManager.update(prizeRecord);
    }
}
