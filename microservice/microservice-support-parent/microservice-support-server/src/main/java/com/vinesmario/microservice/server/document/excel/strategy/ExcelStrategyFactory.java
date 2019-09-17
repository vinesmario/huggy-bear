package com.vinesmario.microservice.server.document.excel.strategy;

import com.vinesmario.common.constant.DocumentVersion;
import com.vinesmario.common.constant.FileExtension;
import com.vinesmario.microservice.client.common.util.SpringContextUtil;
import com.vinesmario.microservice.client.document.excel.annotation.Excel;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import com.vinesmario.microservice.server.config.ExcelProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelStrategyFactory {

    public static ExcelStrategy build(Class<?> clazz, ExcelColumn.PortType portType) {
        Excel excel = clazz.getAnnotation(Excel.class);
        if (ObjectUtils.isEmpty(excel)) {
            throw new IllegalArgumentException("Annotation @Excel not found on class: " + clazz);
        }
        String version = excel.version();
        if (StringUtils.isBlank(version)) {
            ExcelProperties excelProperties = SpringContextUtil.getBean(ExcelProperties.class);
            if (!ObjectUtils.isEmpty(excelProperties.getVersion())) {
                version = excelProperties.getVersion();
            } else {
                throw new IllegalArgumentException("Property 'document.excel.version' is empty ");
            }
        }
        if (DocumentVersion.EXCEL_2007.equals(version)) {
            Excel2007.Excel2007Builder excel2003Builder = new Excel2007.Excel2007Builder();
            Excel2007 excel2007 = excel2003Builder.fileName(excel.name() + "." + FileExtension.XLSX)
                    .sheetName(excel.sheetName())
                    .title(excel.title())
                    .columns(getColumns(clazz, portType))
                    .build();
            return excel2007;
        } else if (DocumentVersion.EXCEL_2003.equals(version)) {
            Excel2003.Excel2003Builder excel2003Builder = new Excel2003.Excel2003Builder();
            Excel2003 excel2003 = excel2003Builder.fileName(excel.name() + "." + FileExtension.XLS)
                    .sheetName(excel.sheetName())
                    .title(excel.title())
                    .columns(getColumns(clazz, portType))
                    .build();
            return excel2003;
        } else {
            throw new IllegalArgumentException("unsupported document version: " + version);
        }
    }

    private static List<ExcelColumn> getColumns(Class<?> clazz, ExcelColumn.PortType portType) {
        List<ExcelColumn> columns = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        if (!ObjectUtils.isEmpty(fields)) {
            for (Field field : fields) {
                ExcelColumn[] excelColumns = field.getDeclaredAnnotationsByType(ExcelColumn.class);
                if (!ObjectUtils.isEmpty(excelColumns)) {
                    for (ExcelColumn excelColumn : excelColumns) {
                        if (!ObjectUtils.isEmpty(excelColumn.portType())) {
                            if (ExcelColumn.PortType.BOTH.equals(excelColumn.portType())
                                    || portType.equals(excelColumn.portType())) {
                                columns.add(excelColumn);
                            }
                        }
                    }
                }
            }
        }
        return columns;
    }

}
