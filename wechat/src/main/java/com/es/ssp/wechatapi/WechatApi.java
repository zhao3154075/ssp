package com.es.ssp.wechatapi;

import common.util.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weixin.popular.api.MediaAPI;
import weixin.popular.api.PayMchAPI;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.media.MediaGetResult;
import weixin.popular.bean.paymch.Gethbinfo;
import weixin.popular.bean.paymch.GethbinfoResult;
import weixin.popular.bean.paymch.Sendredpack;
import weixin.popular.bean.paymch.SendredpackResult;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;
import weixin.popular.util.JsUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;


@Component
public class WechatApi {

    @Value("${wechat.app-id}")
    private  String appID ;//这里是AppId,我放到配置文件中了,也可以在这里写,直接定义全局变量,下面的开发者密码一样
    @Value("${wechat.app-secret}")
    private String appSecret;//AppSecret,开发者密码
    @Value("${web.realPath}")
    private String realPath;
    private String uploadDir="upload/";
    @Value("${wechat.redpack.key}")
    private String key;
    @Value("${wechat.redpack.mch-id}")
    private String mchId;
    @Value("${wechat.redpack.act-name}")
    private String actName;
    @Value("${wechat.redpack.client-ip}")
    private String clientIp;
    @Value("${wechat.redpack.send-name}")
    private String sendName;
    @Value("${wechat.redpack.wishing}")
    private String wishing;

    /**
     * 网页授权base
     * @param url
     * @return
     */
    public String snsBase(String url){
        try {
            return SnsAPI.connectOauth2Authorize(appID,"https://weixin.zjol.com.cn/weixin/snsapi?target_url="+ URLEncoder.encode(url,"utf-8"),false,"STATE");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 网页授权userinfo
     * @param url
     * @return
     */
    public String snsInfo(String url){
        return SnsAPI.connectOauth2Authorize(appID,url,true,"STATE");
    }

    /**
     * 获取snsToken
     * @param code
     * @return
     */
    public SnsToken snsToken(String code){
        return SnsAPI.oauth2AccessToken(appID,appSecret,code);
    }

    /**
     * 网页授权获取用户信息
     * @param token
     * @param openId
     * @return
     */
    public User userinfo(String token, String openId){
        return SnsAPI.userinfo(token,openId,"zh_CN");
    }

    /**
     * 获取用户信息
     * @param openid
     * @return
     */
    public User userinfo(String openid){
        String token= TokenManager.getDefaultToken();
        return UserAPI.userInfo(token,openid);
    }

    /**
     * 生成分享参数
     * @param url
     * @param debug
     * @return
     */
    public String generateConfigJson(String url,boolean debug){
        return JsUtil.generateConfigJson(TicketManager.getTicket(appID),debug,appID,url,null);
    }

    /**
     * 下载媒体文件
     * @param media_id
     * @return
     */
    public String mediaGet(String media_id){
        MediaGetResult result = MediaAPI.mediaGet(TokenManager.getDefaultToken(),media_id);
        if(result!=null&&result.getFilename()!=null){
            try {
                OutputStream out = null ;
                try {
                    String  imageFileName =result.getFilename();
                    String dir=realPath;
                    String fileName=dir+uploadDir+imageFileName;
                    if(!(new File(dir+uploadDir)).isDirectory())new File(dir+uploadDir).mkdirs();
                    File imageFile = new File(fileName);
                    out = new FileOutputStream(imageFile);
                    out.write(result.getBytes());
                    return uploadDir+imageFileName;
                } finally {
                    if ( null != out) {
                        out.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String generatMchBillno(){
        String temp=System.currentTimeMillis()+"";
        temp=(int)(Math.random()*10)+temp.substring(4);
        return mchId+ DateUtils.dateToString(new Date(), "yyyyMMdd")+temp;
    }

    /**
     * 发送红包
     * @param openId
     * @param amount 单位 分
     * @param remark
     * @return
     */
    public SendredpackResult sendRedpack(String billno,String openId,int amount,String remark){
        Sendredpack sendredpack=new Sendredpack();
        sendredpack.setNonce_str(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        sendredpack.setAct_name(actName);
        sendredpack.setRemark(remark);
        sendredpack.setRe_openid(openId);
        sendredpack.setTotal_amount(amount);
        sendredpack.setClient_ip(clientIp);
        sendredpack.setSend_name(sendName);
        sendredpack.setTotal_num(1);
        sendredpack.setWishing(wishing);
        sendredpack.setMch_id(mchId);
        sendredpack.setMch_billno(billno);
        sendredpack.setWxappid(appID);
        return PayMchAPI.mmpaymkttransfersSendredpack(sendredpack,key);
    }

    /**
     * 获取红包状态
     * @param billno
     * @return
     */
    public GethbinfoResult gethbinfo(String billno){
        Gethbinfo gethbinfo=new Gethbinfo();
        gethbinfo.setAppid(appID);
        gethbinfo.setBill_type("MCHT");
        gethbinfo.setMch_billno(billno);
        gethbinfo.setNonce_str(UUID.randomUUID().toString().toUpperCase().replace("-", ""));
        gethbinfo.setMch_id(mchId);
        return PayMchAPI.mmpaymkttransfersGethbinfo(gethbinfo,key);
    }
}
