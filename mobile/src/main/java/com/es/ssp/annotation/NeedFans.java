package com.es.ssp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用此注解的controller将通过FansInterceptor
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedFans {
}
