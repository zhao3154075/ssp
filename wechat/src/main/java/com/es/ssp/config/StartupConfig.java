package com.es.ssp.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import weixin.popular.client.LocalHttpClient;

import javax.annotation.Resource;
import java.io.FileNotFoundException;

@PropertySources({
        @PropertySource("classpath:wechat.properties"),
        @PropertySource("classpath:common.properties")
})
@Component
public class StartupConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private Environment env;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                org.springframework.core.io.Resource resource = new ClassPathResource("key/key.p12");
                //初始化MCH HttpClient KeyStore,仅需要调用一次。
                LocalHttpClient.initMchKeyStore(env.getProperty("wechat.redpack.mch-id"),resource.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
