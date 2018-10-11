package com.cloudplatform;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.RequestBuilder;
import weixin.popular.client.LocalHttpClient;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    public static JSONObject call(String url,String method, Map<String,String> params){
        String nonceStr= CloudPlatformApiUtil.getUUID();
        long timeStamp=System.currentTimeMillis()/1000-600;
        Map map=new HashMap();
        map.put("appId",Constants.appId);
        map.put("timeStamp",timeStamp+"");
        map.put("nonceStr",nonceStr);
        map.put("method",method);
        map.putAll(params);
        String temp= CloudPlatformApiUtil.makeEncryptTempString(map,Constants.key);
        String sign= CloudPlatformApiUtil.makeSignString(temp);
        map.put("sign",sign);

        RequestBuilder httpUriRequest = RequestBuilder.get().setUri(Constants.domain+url);
        for(Object key:map.keySet()){
            httpUriRequest.addParameter(key.toString(),map.get(key).toString());
        }
        return  LocalHttpClient.executeJsonResult(httpUriRequest.build(), JSONObject.class);
    }
    public static void main(String args[]){
        Map map=new HashMap();
        JSONObject jo=new JSONObject();
        jo.put("wx_appId","wx96a6086ce772dba2");
        jo.put("wx_appSecret","858356d452921261067dcf121bdac041");
        jo.put("time",System.currentTimeMillis()/1000-300);
        jo.put("correct_url","http://www.baidu.com");
        jo.put("error_url","http://www.baidu.com");
        map.put("text",jo.toJSONString());
        call("crypt/index","encode",map);
    }
}
