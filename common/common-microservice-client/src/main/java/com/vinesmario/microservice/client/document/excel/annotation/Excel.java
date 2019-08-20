package com.vinesmario.microservice.client.document.excel.annotation;

import com.vinesmario.common.constant.DocumentVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Excel注解定义
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

    /**
     * ID
     *
     * @return
     */
    String id() default "";
    /**
     *
     *
     * @return
     */
    String name() default "filename";

    /**
     * Excel版本信息
     *
     * @return
     */
    String version() default DocumentVersion.EXCEL_2007;

    /**
     * 表单名
     *
     * @return
     */
    String sheetName() default "sheet1";

    /**
     * 表单标题
     *
     * @return
     */
    String title() default "title";

}
