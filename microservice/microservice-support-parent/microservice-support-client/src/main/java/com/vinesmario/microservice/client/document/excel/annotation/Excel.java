package com.vinesmario.microservice.client.document.excel.annotation;

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
     * 导出时文件名
     *
     * @return
     */
    String name() default "filename";

    /**
     * 导出时Excel版本信息
     *
     * @return
     */
    ExcelVersion version() default ExcelVersion.EXCEL_2007;

    /**
     * 表单索引
     *
     * @return
     */
    int sheetIndex() default 0;

    /**
     * 导出时表单名
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

    String exceptionFieldName() default "";

    enum ExcelVersion {
        EXCEL_2003("xls"),
        EXCEL_2007("xlsx");

        private String fileExtension;

        ExcelVersion(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public String getFileExtension() {
            return fileExtension;
        }
    }
}
