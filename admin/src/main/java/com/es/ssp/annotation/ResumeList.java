package com.es.ssp.annotation;


import com.es.ssp.annotation.enums.ResumeListMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于类似在某个列表中，当翻页进行到第5页时，对列表中的一条记录进行了修改操作并保存后，依然返回到第5页的当前列表
 * 使用方法：以上述例子为例，在进入修改页面的方法前加入@ResumeList(action = ResumeListMethod.BEFORE)标签，在修改完成后的保存更新方法前加入@ResumeList(action = ResumeListMethod.AFTER)标签
 * 如果只进行单个操作时，只要在改方法前加入@ResumeList标签即可
 * created by administrator on 2018/03/22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ResumeList {
    ResumeListMethod action() default ResumeListMethod.NULL;
}
