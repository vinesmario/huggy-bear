package com.vinesmario.microservice.server.document.excel;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import lombok.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.reflect.Field;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelConfig {

    /**
     * Excel版本信息
     */
    private final String version;
    /**
     * 文件扩展名
     */
    private final String extension;
    /**
     * 表单名
     */
    @Builder.Default
    private final String sheetName = "sheet1";
    /**
     * 表单标题
     */
    private final String tiltle;
    /**
     * DTO对象class名
     */
    private final Class<? extends BaseDTO> clazz;
    /**
     * 导入列配置
     */
    @Singular("columnImportConfig")
    private final List<ExcelColumnConfig> columnImportConfigList;
    /**
     * 导出列配置
     */
    @Singular("columnExportConfig")
    private final List<ExcelColumnConfig> columnExportConfigList;

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class ExcelColumnConfig {

        /**
         * DTO对象的属性
         */
        private Field field;
        /**
         * 字段排序（升序）
         */
        private int sort;
        /**
         * 如果是字典类型，根据字典的catalogCode值及列的fieldName取得value
         */
        private String catalogCode;
        /**
         * 字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
         */
        private String fieldName;
        /**
         * 如果是其他反射类型，根据字典的class及fieldName，反射取得value
         */
        private Class<?> fieldTypeClass;

        /**
         * 导出字段标题
         */
        private String title;
        /**
         * 字段水平对齐方式
         *
         * @see HorizontalAlignment
         * GENERAL,LEFT,CENTER,RIGHT,FILL,JUSTIFY,CENTER_SELECTION,DISTRIBUTED
         */
        private HorizontalAlignment horizontalAlignment;
        /**
         * 字段垂直对齐方式
         *
         * @see VerticalAlignment
         * TOP,CENTER,BOTTOM,JUSTIFY,DISTRIBUTED
         */
        private VerticalAlignment verticalAlignment;
        /**
         * @see CellType
         * _NONE(-1),NUMERIC(0),STRING(1),FORMULA(2),BLANK(3),BOOLEAN(4),ERROR(5);
         */
        private CellType cellType;
        /**
         * 列宽
         */
        private int width;
    }

    public static void main(String[] args) {
        String version = "version";
        ExcelConfig config = ExcelConfig.builder()
                .columnImportConfig(ExcelColumnConfig.builder().build())
                .columnImportConfig(ExcelColumnConfig.builder().build())
                .build();
        ExcelConfig.ExcelConfigBuilder builder = new ExcelConfig.ExcelConfigBuilder();
    }

}
