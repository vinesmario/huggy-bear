/**
 * Copyright &copy; 2014-2015 plarform
 */
package com.vinesmario.microservice.server.document.excel.annotation;

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
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
     */
    String value() default "";

    /**
     * 字段标题
     */
    String title();

    /**
     * 字段排序（升序）
     */
    int sort() default 0;

    /**
     * 如果是字典类型，请设置字典的type值
     */
    String dictType() default "";

    /**
     * 反射类型
     */
    Class<?> fieldType() default Class.class;

    /**
     * @see org.apache.poi.ss.usermodel.CellType
     * _NONE(-1),NUMERIC(0),STRING(1),FORMULA(2),BLANK(3),BOOLEAN(4),ERROR(5);
     */
    CellType cellType() default CellType._NONE;

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

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    int type() default 0;

    /**
     * 字段归属组（根据分组导出导入）
     */
    int[] groups() default {};
}
