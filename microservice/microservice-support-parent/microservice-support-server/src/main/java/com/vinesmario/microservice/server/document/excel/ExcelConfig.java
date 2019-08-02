package com.vinesmario.microservice.server.document.excel;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.reflect.Field;
import java.util.List;

@Data
public class ExcelConfig {

    /**
     * Excel版本信息
     */
    private String version;
    /**
     * 文件扩展名
     */
    private String extension;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 表单名
     */
    private String sheetName;
    /**
     * 表单标题
     */
    private String tiltle;
    /**
     * DTO对象class名
     */
    private Class<? extends BaseDTO> clazz;
    /**
     * 列配置
     */
    private List<ExcelColumnConfig> columnConfigList;

    @Data
    class ExcelColumnConfig {

        /**
         * DTO对象的属性
         */
        private Field field;
        /**
         * 字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
         */
        private String fieldName;
        /**
         * 字段标题
         */
        private String title;
        /**
         * 字段排序（升序）
         */
        private int sort = 9999;
        /**
         * 如果是字典类型，根据字典的catalog值及fieldName取得value
         */
        private String dictCatalog;
        /**
         * 如果是其他反射类型，根据字典的class及fieldName，反射取得value
         */
        private Class<?> fieldTypeClass;

        /**
         * @see org.apache.poi.ss.usermodel.CellType
         * _NONE(-1),NUMERIC(0),STRING(1),FORMULA(2),BLANK(3),BOOLEAN(4),ERROR(5);
         */
        private CellType cellType = CellType._NONE;

        /**
         * 字段水平对齐方式（0：自动；1：靠左；2：居中；3：靠右）
         *
         * @see org.apache.poi.ss.usermodel.HorizontalAlignment
         * GENERAL,LEFT,CENTER,RIGHT,FILL,JUSTIFY,CENTER_SELECTION,DISTRIBUTED
         */
        private HorizontalAlignment horizontalAlignment = HorizontalAlignment.GENERAL;

        /**
         * 字段垂直对齐方式（0：顶部；1：居中；2：底部；）
         *
         * @see org.apache.poi.ss.usermodel.VerticalAlignment
         * TOP,CENTER,BOTTOM,JUSTIFY,DISTRIBUTED
         */
        private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;

    }

}
