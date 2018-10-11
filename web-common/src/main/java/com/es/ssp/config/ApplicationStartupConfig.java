package com.es.ssp.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:common.properties")
@Component
public class ApplicationStartupConfig {

    @Resource
    private Environment env;

    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver != null) {
            Map<String, Object> vars = new HashMap();
            vars.put("urlPrefix", env.getProperty("web.urlPrefix"));
            viewResolver.setStaticVariables(vars);
        }
    }

}
