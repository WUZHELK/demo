package com.example.demo.annotation;

import com.example.demo.Enum.DesensitizationTypeEnum;
import com.example.demo.util.DesensitizationSerialize;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerialize.class)
public @interface Desensitization {

    /**
     * 在自定义类型的时候，start和end生效
     */
    DesensitizationTypeEnum type() default DesensitizationTypeEnum.CUSTOM_RULE;

    /**
     * 开始位置（包含）
     */
    int start() default 0;

    /**
     * 结束位置（不包含）
     */
    int end() default 0;

}
