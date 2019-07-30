package com.vinesmario.microservice.server.document.excel.annotation;

import com.vinesmario.common.constant.DocumentVersion;
import com.vinesmario.common.constant.FileExtension;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

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
     * 文件名
     *
     * @return
     */
    String value() default "";

    /**
     * Excel版本信息
     *
     * @return
     */
    String version() default DocumentVersion.EXCEL_2007;

    Class<? extends Workbook> version2() default HSSFWorkbook.class;

    /**
     * 文件扩展名
     *
     * @return
     */
    String extension() default FileExtension.XLSX;

    // 单元格全局属性

    /**
     * 字段水平对齐方式（0：自动；1：靠左；2：居中；3：靠右）
     *
     * @see org.apache.poi.ss.usermodel.HorizontalAlignment
     * GENERAL,LEFT,CENTER,RIGHT,FILL,JUSTIFY,CENTER_SELECTION,DISTRIBUTED
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.GENERAL;

    /**
     * 字段垂直对齐方式（0：顶部；1：居中；2：底部；）
     *
     * @see org.apache.poi.ss.usermodel.VerticalAlignment
     * TOP,CENTER,BOTTOM,JUSTIFY,DISTRIBUTED
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;
}
