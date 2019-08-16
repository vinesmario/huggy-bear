package com.vinesmario.microservice.client.document.excel.annotation;

import com.vinesmario.common.constant.DocumentVersion;
import com.vinesmario.common.constant.FileExtension;
import com.vinesmario.microservice.client.common.web.feign.CrudClient;

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
     * Excel版本信息
     *
     * @return
     */
    String version() default DocumentVersion.EXCEL_2007;

    /**
     * 文件扩展名
     *
     * @return
     */
    String extension() default FileExtension.XLSX;

    /**
     * 文件名
     *
     * @return
     */
    String fileName() default "fileName";

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

    /**
     * 查询数据接口类
     */
    Class<? extends CrudClient> feignClient() default CrudClient.class;

}
