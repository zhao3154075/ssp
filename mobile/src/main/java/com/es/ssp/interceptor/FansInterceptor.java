package com.es.ssp.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cloudplatform.SnsAuthorizeApi;
import com.es.ssp.annotation.NeedFans;
import com.es.ssp.model.Fans;
import com.es.ssp.service.FansManager;
import com.es.ssp.wechatapi.WechatApi;
import common.util.DeviceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import weixin.popular.bean.sns.SnsToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Component
public class FansInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private FansManager fansManager;
    @Autowired
    private WechatApi wechatApi;
    @Value("${wechat.app-id}")
    private  String appID ;//这里是AppId,我放到配置文件中了,也可以在这里写,直接定义全局变量,下面的开发者密码一样
    @Value("${wechat.app-secret}")
    private String appSecret;//AppSecret,开发者密码

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod))return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getBean().getClass().isAnnotationPresent(NeedFans.class)||handlerMethod.hasMethodAnnotation(NeedFans.class)){
            String openId=request.getParameter("openId");
            if(StringUtils.isEmpty(openId)){
                if(DeviceUtil.isWeChat(request)){
                    String q=request.getParameter("q");
                    String basePath=request.getScheme() + "://"
                            + request.getServerName()+request.getContextPath()+"/";
                    String uri=request.getRequestURI().replace(request.getContextPath()+"/","");
                    Enumeration<String> e= request.getParameterNames();
                    if(e!=null){
                        while (e.hasMoreElements()){
                            String name=e.nextElement();
                            if(name.equals("q")){

                            }else{
                                if(uri.indexOf("?")==-1){
                                    uri+="?";
                                }else{
                                    uri+="&";
                                }
                                uri+=(name+"="+request.getParameter(name));
                            }
                        }
                    }
                    if(StringUtils.isEmpty(q)){
                        String url= SnsAuthorizeApi.createRedirectUrl(appID,appSecret,basePath+uri);
                        System.out.println(url);
                        response.sendRedirect(url);
                        return false;
                    }else{
                        JSONObject userInfo=SnsAuthorizeApi.getSnsAuthorizeUserInfo(q);
                        Fans fans=fansManager.getUnknownFans(userInfo.get("user_id").toString());
                        if(fans!=null&&fans.getSubscribe()==1)
                            request.setAttribute("fans",fans);
                    }
                }

            }else{
                Fans fans=fansManager.findByOpenId(openId);
                if(fans!=null&&fans.getSubscribe()==1)
                request.setAttribute("fans",fans);
            }
        }
        return true;
    }
}
