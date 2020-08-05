package com.cloudplatform;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/7.
 */
public class SnsAuthorizeApi {
    public static final String AUTHORIZE_URL= "https://api.tmuyun.com/home/Authority/new?q=";
    public enum Scope {
        SNSAPI_BASE("snsapi_base"),SNSAPI_USERINFO("snsapi_userinfo");

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        Scope(String value) {
            this.value=value;
        }

    }
    public static String createRedirectUrl(String appId,String appSecret,String redirectUrl){
        Map map=new HashMap();
        JSONObject jo=new JSONObject();
        jo.put("wx_appId",appId);
        jo.put("wx_appSecret",appSecret);
        jo.put("time",System.currentTimeMillis()/1000-300);
        jo.put("correct_url",redirectUrl);
        jo.put("error_url",redirectUrl);
        jo.put("authorize_type","snsapi_base");
        map.put("text",jo.toJSONString());
        JSONObject token=ApiClient.call("crypt/index","encode",map);
        String url=new StringBuilder(AUTHORIZE_URL+ token.getString("msg")).toString();
        return url;
    }

    /**
     * 解密网页授权返回的q值
     * @param data
     * @return
     */
    public static JSONObject getSnsAuthorizeUserInfo(String data){
        Map map=new HashMap();
        map.put("text",data);
        return JSONObject.parseObject(ApiClient.call("crypt/index","decode",map).getString("msg"));
    }
}
