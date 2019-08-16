/**
 * Copyright &copy; 2014-2015 plarform
 */
package com.vinesmario.microservice.client.document.excel.annotation;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel列注解定义
 *
 * @author Roc update 150325
 * @version 2013-03-10
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
     */
    String name() default "";

    /**
     * 字段排序（升序）
     */
    int sort() default 9999;

    /**
     * 如果是字典类型，根据字典的catalogCode值及列的fieldName取得value
     */
    String catalogCode() default "";

    /**
     * 如果是其他反射类型，根据字典的class及fieldName，反射取得value
     */
    Class<?> fieldTypeClass() default Object.class;

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    ColumnType columnType() default ColumnType.BOTH;

    /**
     * 字段标题
     */
    String title() default "";

    /**
     * 字段水平对齐方式
     *
     * @see HorizontalAlignment
     * GENERAL,LEFT,CENTER,RIGHT,FILL,JUSTIFY,CENTER_SELECTION,DISTRIBUTED
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.GENERAL;

    /**
     * 字段垂直对齐方式
     *
     * @see VerticalAlignment
     * TOP,CENTER,BOTTOM,JUSTIFY,DISTRIBUTED
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;

    /**
     * @see CellType
     * _NONE(-1),NUMERIC(0),STRING(1),FORMULA(2),BLANK(3),BOOLEAN(4),ERROR(5);
     */
    CellType cellType() default CellType._NONE;

    /**
     * 宽度
     */
    int width() default 100;

    enum ColumnType {
        BOTH,
        IMPORT,
        EXPORT;
    }

}
