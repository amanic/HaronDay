package com.haron.pro.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenhaitao on 2018/8/9.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOperationTag {

    String value() default "";

    /**
     * 是否需要入库
     * @return
     */
    boolean required() default true;


    /**
     * 是否是实体类，非实体类的情况下是多参数传值
     * @return
     */
    boolean isEntity() default true;

    /**
     * 需要额外动作的参数名
     * @return
     */
    String propertyName() default "";
}
