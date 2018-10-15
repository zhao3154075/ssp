package com.es.ssp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Value;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;

@WebListener
public class WeixinPopularInitListener implements ServletContextListener{

    @Value("${wechat.app-id}")
    public  String appID ;//这里是AppId,我放到配置文件中了,也可以在这里写,直接定义全局变量,下面的开发者密码一样
    @Value("${wechat.app-secret}")
    private String appSecret;//AppSecret,开发者密码

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //@since 2.7.0
         //请求超时 5秒
        LocalHttpClient.setTimeout(5000);

        //@since 2.7.0
        //异常重试次数 2
        LocalHttpClient.setRetryExecutionCount(1);

        //设置请求连接池
        LocalHttpClient.init(200,50);
        //WEB容器 初始化时调用
        TokenManager.setDaemon(true);
        TokenManager.init(appID, appSecret,0,6);
        //2.6.1 版本新增方式,5秒延迟执行，间隔119分钟。
        TicketManager.setDaemon(true);
        TicketManager.init(appID,appSecret,5,6);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //WEB容器  关闭时调用
        TokenManager.destroyed();
        TicketManager.destroyed();
    }
}
