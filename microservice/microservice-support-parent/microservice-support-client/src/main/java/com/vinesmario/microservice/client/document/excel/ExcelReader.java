package com.vinesmario.microservice.client.document.excel;

import com.monitorjbl.xlsx.StreamingReader;
import com.vinesmario.common.kit.ReflectionKit;
import com.vinesmario.microservice.client.common.config.Java8TimeConfig;
import com.vinesmario.microservice.client.document.excel.annotation.Excel;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumns;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Builder
public class ExcelReader {

    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";
    private static final String GETTER_PREFIX_IS = "is";

    private String filePath;
    private String fileExtension;
    private File file;
    private InputStream is;
    private Workbook workbook;
    private Sheet sheet;
    @Builder.Default
    private Integer sheetIndex = -1;
    @Builder.Default
    private Integer beginRowNum = 0;
    @Builder.Default
    private Integer endRowNum = -1;
    private Collection<Integer> skipRowNums;
    @Builder.Default
    private Integer beginCellNum = 0;
    @Builder.Default
    private Integer endCellNum = -1;
    private Collection<Integer> skipCellNums;

    public ExcelReader readHeader(int endRowNum) {
        this.endRowNum = endRowNum;
        return this;
    }

    public ExcelReader readHeaderWithSkip(int endRowNum, Collection<Integer> skipRowNums) {
        this.endRowNum = endRowNum;
        this.skipRowNums = skipRowNums;
        return this;
    }

    public ExcelReader readBody(int beginRowNum) {
        this.beginRowNum = beginRowNum;
        return this;
    }

    public ExcelReader readBodyWithSkip(int beginRowNum, Collection<Integer> skipRowNums) {
        this.beginRowNum = beginRowNum;
        this.skipRowNums = skipRowNums;
        return this;
    }

    public ExcelReader readLeft(int endCellNum) {
        this.endCellNum = endCellNum;
        return this;
    }

    public ExcelReader readLeftWithSkip(int endCellNum, Collection<Integer> skipCellNums) {
        this.endCellNum = endCellNum;
        this.skipCellNums = skipCellNums;
        return this;
    }

    public ExcelReader readRight(int beginCellNum) {
        this.beginCellNum = beginCellNum;
        return this;
    }

    public ExcelReader readRightWithSkip(int beginCellNum, Collection<Integer> skipCellNums) {
        this.beginCellNum = beginCellNum;
        this.skipCellNums = skipCellNums;
        return this;
    }

    public ExcelReader readHeaderLeft(int endRowNum, int endCellNum) {
        this.endRowNum = endRowNum;
        this.endCellNum = endCellNum;
        return this;
    }

    public ExcelReader readHeaderLeftWithSkip(int endRowNum, Collection<Integer> skipRowNums,
                                              int endCellNum, Collection<Integer> skipCellNums) {
        this.endRowNum = endRowNum;
        this.skipRowNums = skipRowNums;
        this.endCellNum = endCellNum;
        this.skipCellNums = skipCellNums;
        return this;
    }

    public ExcelReader readHeaderRight(int endRowNum, int beginCellNum) {
        this.endRowNum = endRowNum;
        this.beginCellNum = beginCellNum;
        return this;
    }

    public ExcelReader readHeaderRightWithSkip(int endRowNum, Collection<Integer> skipRowNums,
                                               int beginCellNum, Collection<Integer> skipCellNums) {
        this.endRowNum = endRowNum;
        this.skipRowNums = skipRowNums;
        this.beginCellNum = beginCellNum;
        this.skipCellNums = skipCellNums;
        return this;
    }

    public ExcelReader readBodyLeft(int beginRowNum, int endCellNum) {
        this.beginRowNum = beginRowNum;
        this.endCellNum = endCellNum;
        return this;
    }

    public ExcelReader readBodyLeftWithSkip(int beginRowNum, Collection<Integer> skipRowNums,
                                            int endCellNum, Collection<Integer> skipCellNums) {
        this.beginRowNum = beginRowNum;
        this.skipRowNums = skipRowNums;
        this.endCellNum = endCellNum;
        this.skipCellNums = skipCellNums;
        return this;
    }

    public ExcelReader readBodyRight(int beginRowNum, int beginCellNum) {
        this.beginRowNum = beginRowNum;
        this.beginCellNum = beginCellNum;
        return this;
    }

    public ExcelReader readBodyRightWithSkip(int beginRowNum, Collection<Integer> skipRowNums,
                                             int beginCellNum, Collection<Integer> skipCellNums) {
        this.beginRowNum = beginRowNum;
        this.skipRowNums = skipRowNums;
        this.beginCellNum = beginCellNum;
        this.skipCellNums = skipCellNums;
        return this;
    }

    public List<Map<Integer, String>> read() throws IOException {
        if (ObjectUtils.isEmpty(sheet)) {
            if (ObjectUtils.isEmpty(workbook)) {
                if (ObjectUtils.isEmpty(is)) {
                    if (!ObjectUtils.isEmpty(file)) {
                        this.is = FileUtils.openInputStream(file);
                    } else if (StringUtils.isNotBlank(filePath)) {
                        this.is = FileUtils.openInputStream(FileUtils.getFile(filePath));
                    } else {
                        throw new IllegalArgumentException("file、inputStream、workbook and sheet are empty");
                    }
                }

                if (StringUtils.isBlank(fileExtension)) {
                    if (!ObjectUtils.isEmpty(file)) {
                        this.fileExtension = FilenameUtils.getExtension(file.getName());
                    } else if (StringUtils.isNotBlank(filePath)) {
                        this.fileExtension = FilenameUtils.getExtension(filePath);
                    } else {
                        throw new IllegalArgumentException("file extension is empty");
                    }
                }

                if (StringUtils.equalsIgnoreCase(Excel.ExcelVersion.EXCEL_2003.getFileExtension(), fileExtension)) {
                    this.workbook = new HSSFWorkbook(is);
                } else if (StringUtils.equalsIgnoreCase(Excel.ExcelVersion.EXCEL_2007.getFileExtension(), fileExtension)) {
                    this.workbook = StreamingReader.builder()
                            .rowCacheSize(100)    // 缓存到内存中的行数，默认是10
                            .bufferSize(4096)     // 读取资源时，缓存到内存的字节大小，默认是1024
                            .open(is);            // 打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
                } else {
                    throw new IllegalArgumentException("unsupported file extension: " + fileExtension);
                }
            }
            if (sheetIndex < 0) {
                this.sheetIndex = 0;
            } else {
                if (workbook.getNumberOfSheets() < sheetIndex) {
                    throw new IllegalArgumentException("there is no sheet in the workbook");
                }
            }
            this.sheet = workbook.getSheetAt(sheetIndex);
        }
        List<Map<Integer, String>> dataList = new ArrayList<>(sheet.getLastRowNum());
        // getLastRowNum 为有填充值的行
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            if (rowNum < beginRowNum
                    || (endRowNum >= 0 && rowNum > endRowNum)
                    || (!CollectionUtils.isEmpty(skipRowNums) && skipRowNums.contains(rowNum))) {
                // 跳过
                continue;
            }
            Row row = sheet.getRow(rowNum);
            Map<Integer, String> map = new HashMap<>(row.getLastCellNum());
            // getLastCellNum 为无填充值的列
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                if (cellNum < beginCellNum
                        || (endCellNum >= 0 && rowNum > endCellNum)
                        || (!CollectionUtils.isEmpty(skipCellNums) && skipCellNums.contains(cellNum))) {
                    // 跳过
                    continue;
                }
                Cell cell = row.getCell(cellNum);
                map.put(cellNum, getCellValue(cell));
            }
            dataList.add(map);
        }
        return dataList;
    }

    public <T> List<T> read(Class<T> clazz) throws IOException {
        Excel excel = clazz.getAnnotation(Excel.class);
        if (sheetIndex < 0) {
            if (ObjectUtils.isEmpty(excel)) {
                this.sheetIndex = 0;
            } else {
                this.sheetIndex = excel.sheetIndex();
            }
        }
        String exceptionFieldName = null;
        if (!ObjectUtils.isEmpty(excel)
                || StringUtils.isNotBlank(excel.exceptionFieldName())) {
            exceptionFieldName = excel.exceptionFieldName();
        }
        if (ObjectUtils.isEmpty(sheet)) {
            if (ObjectUtils.isEmpty(workbook)) {
                if (ObjectUtils.isEmpty(is)) {
                    if (!ObjectUtils.isEmpty(file)) {
                        this.is = FileUtils.openInputStream(file);
                    } else if (StringUtils.isNotBlank(filePath)) {
                        this.is = FileUtils.openInputStream(FileUtils.getFile(filePath));
                    } else {
                        throw new IllegalArgumentException("file、inputStream、workbook and sheet are empty");
                    }
                }

                if (StringUtils.isBlank(fileExtension)) {
                    if (!ObjectUtils.isEmpty(file)) {
                        this.fileExtension = FilenameUtils.getExtension(file.getName());
                    } else if (StringUtils.isNotBlank(filePath)) {
                        this.fileExtension = FilenameUtils.getExtension(filePath);
                    } else {
                        throw new IllegalArgumentException("file extension is empty");
                    }
                }

                if (StringUtils.equalsIgnoreCase(Excel.ExcelVersion.EXCEL_2003.getFileExtension(), fileExtension)) {
                    this.workbook = new HSSFWorkbook(is);
                } else if (StringUtils.equalsIgnoreCase(Excel.ExcelVersion.EXCEL_2007.getFileExtension(), fileExtension)) {
                    this.workbook = StreamingReader.builder()
                            .rowCacheSize(100)    // 缓存到内存中的行数，默认是10
                            .bufferSize(4096)     // 读取资源时，缓存到内存的字节大小，默认是1024
                            .open(is);            // 打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
                } else {
                    throw new IllegalArgumentException("unsupported file extension: " + fileExtension);
                }
            }
            if (workbook.getNumberOfSheets() < sheetIndex) {
                throw new IllegalArgumentException("there are only " + (workbook.getNumberOfSheets() + 1) + "(less than " + (sheetIndex + 1) + ") sheet(s) in the workbook");
            }
            this.sheet = workbook.getSheetAt(sheetIndex);
        }

        // 字段配置
        Map<Integer, Method> methodByIndex = new HashMap<>();
        List<Field> fieldList = ReflectionKit.getAccessibleFields(clazz);
        for (Field field : fieldList) {
            ExcelColumns fieldAnnotation = field.getAnnotation(ExcelColumns.class);
            if (!ObjectUtils.isEmpty(fieldAnnotation)
                    && !ObjectUtils.isEmpty(fieldAnnotation.value())) {
                for (ExcelColumn excelColumn : fieldAnnotation.value()) {
                    if (ExcelColumn.PortType.BOTH.equals(excelColumn.portType())
                            || ExcelColumn.PortType.IMPORT.equals(excelColumn.portType())) {
                        String setterMethodName = null;
                        if (StringUtils.isNotBlank(excelColumn.name())) {
                            setterMethodName = SETTER_PREFIX + StringUtils.capitalize(excelColumn.name());
                        } else {
                            setterMethodName = SETTER_PREFIX + StringUtils.capitalize(field.getName());
                        }
                        Method setterMethod = ReflectionKit.getAccessibleMethod(clazz, setterMethodName, field.getType());
                        methodByIndex.put(excelColumn.index(), setterMethod);
                    }
                }
            } else {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                if (!ObjectUtils.isEmpty(excelColumn)) {
                    if (ExcelColumn.PortType.BOTH.equals(excelColumn.portType())
                            || ExcelColumn.PortType.IMPORT.equals(excelColumn.portType())) {
                        String setterMethodName = null;
                        if (StringUtils.isNotBlank(excelColumn.name())) {
                            setterMethodName = SETTER_PREFIX + StringUtils.capitalize(excelColumn.name());
                        } else {
                            setterMethodName = SETTER_PREFIX + StringUtils.capitalize(field.getName());
                        }
                        Method setterMethod = ReflectionKit.getAccessibleMethod(clazz, setterMethodName, field.getType());
                        methodByIndex.put(excelColumn.index(), setterMethod);
                    }
                }
            }
            if (StringUtils.isBlank(exceptionFieldName)) {
                ExcelException excelException = field.getAnnotation(ExcelException.class);
                if (!ObjectUtils.isEmpty(excelException)) {
                    exceptionFieldName = StringUtils.isNotBlank(excelException.name()) ? excelException.name() : field.getName();
                }
            }
        }

        Method setterExceptionMethod = null;
        if (StringUtils.isBlank(exceptionFieldName)) {
            throw new IllegalArgumentException("available annotation of 'ExcelException' is empty");
        } else {
            String setterExceptionMethodName = SETTER_PREFIX + StringUtils.capitalize(exceptionFieldName);
            setterExceptionMethod = ReflectionKit.getAccessibleMethod(clazz, setterExceptionMethodName, String.class);
        }
        if (CollectionUtils.isEmpty(methodByIndex)) {
            throw new IllegalArgumentException("available annotation collection of 'ExcelColumn' is empty");
        }

        List<T> dataList = new ArrayList<>(sheet.getLastRowNum());

        // getLastRowNum 为有填充值的行
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            if (rowNum < beginRowNum
                    || (endRowNum >= 0 && rowNum > endRowNum)
                    || (!CollectionUtils.isEmpty(skipRowNums) && skipRowNums.contains(rowNum))) {
                // 跳过
                continue;
            }
            Row row = sheet.getRow(rowNum);

            T vo = null;
            try {
                vo = clazz.newInstance();
            } catch (ReflectiveOperationException e) {
                // 跳出循环
                break;
            }
            // getLastCellNum 为无填充值的列

            List<String> fieldExceptionList = new ArrayList<>(row.getLastCellNum());
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                if (cellNum < beginCellNum
                        || (endCellNum >= 0 && rowNum > endCellNum)
                        || (!CollectionUtils.isEmpty(skipCellNums) && skipCellNums.contains(cellNum))) {
                    // 跳过
                    continue;
                }
                String cellValue = getCellValue(row.getCell(cellNum));
                Method method = methodByIndex.get(cellNum);
                if (!ObjectUtils.isEmpty(method)
                        && StringUtils.isNotBlank(cellValue)) {
                    Class<?> paramClazz = method.getParameterTypes()[0];
                    try {
                        if (paramClazz == String.class) {
                            method.invoke(vo, cellValue);
                        } else if (paramClazz == Byte.class) {
                            method.invoke(vo, Byte.parseByte(cellValue));
                        } else if (paramClazz == Short.class) {
                            method.invoke(vo, Short.parseShort(cellValue));
                        } else if (paramClazz == Integer.class) {
                            method.invoke(vo, Integer.parseInt(cellValue));
                        } else if (paramClazz == Long.class) {
                            method.invoke(vo, Long.parseLong(cellValue));
                        } else if (paramClazz == Float.class) {
                            method.invoke(vo, Float.parseFloat(cellValue));
                        } else if (paramClazz == Double.class) {
                            method.invoke(vo, Double.parseDouble(cellValue));
                        } else if (paramClazz == BigDecimal.class) {
                            method.invoke(vo, BigDecimal.valueOf(Double.parseDouble(cellValue)));
                        } else if (paramClazz == LocalDateTime.class) {
                            method.invoke(vo, LocalDateTime.parse(cellValue, Java8TimeConfig.LOCAL_DATE_TIME));
                        } else if (paramClazz == LocalDate.class) {
                            method.invoke(vo, LocalDate.parse(cellValue, Java8TimeConfig.LOCAL_DATE_TIME));
                        } else if (paramClazz == LocalTime.class) {
                            method.invoke(vo, LocalTime.parse(cellValue, Java8TimeConfig.LOCAL_DATE_TIME));
                        } else if (paramClazz == Date.class) {
                            method.invoke(vo, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cellValue));
                        }
                    } catch (ReflectiveOperationException e) {
                        String fieldException = "封装第" + (cellNum + 1) + "列数据(方法名:" + method.getName() + ",参数类型:" + paramClazz + ")出错，" + e.getMessage();
                        fieldExceptionList.add(fieldException);
                        log.error(fieldException);
                        e.printStackTrace();
                    } catch (ParseException e) {
                        String fieldException = "封装第" + (cellNum + 1) + "列数据(方法名:" + method.getName() + ",参数类型:" + paramClazz + ")出错，" + e.getMessage();
                        fieldExceptionList.add(fieldException);
                        log.error(fieldException);
                        e.printStackTrace();
                    }
                }
            }
            String exceptionDesc = "第" + (rowNum + 1) + "行：\r\n";
            if (!CollectionUtils.isEmpty(fieldExceptionList)) {
                exceptionDesc += StringUtils.join(fieldExceptionList, "\r\n");
                try {
                    setterExceptionMethod.invoke(vo, exceptionDesc);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
            dataList.add(vo);
        }
        return dataList;
    }

    private String getCellValue(Cell cell) {
        String cellValue = "";
        //判断是否为null或空串
        if (ObjectUtils.isEmpty(cell) || StringUtils.isBlank(cell.toString().trim())) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                //判断日期类型
                Date date = cell.getDateCellValue();
                LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                cellValue = localDateTime.format(Java8TimeConfig.LOCAL_DATE_TIME);
            } else {  //否
                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.FORMULA) {
            //表达式
            cellValue = cell.getCellFormula().trim();
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        }
        return cellValue;
    }

    public static void main(String[] arg) throws IOException, InstantiationException {
        ExcelReaderBuilder builder = new ExcelReaderBuilder();
//        ExcelReader excelReader = builder.filePath("D:/storage/38e7dbe8e9984859a705ecb1e3772b28/2019-12-17/e9fe49ea7be64ffa9a8b0248f5bc5229.xls").build();
//        List<Map<Integer, String>> dataList = excelReader.read();
//        if (!CollectionUtils.isEmpty(dataList)) {
//            log.info(JSONObject.toJSONString(dataList.get(0)));
//            log.info(JSONObject.toJSONString(dataList.get(2)));
//        }
//        List<ExcelDemo> dataList = excelReader.readBody(1).read(ExcelDemo.class);
//        if (!CollectionUtils.isEmpty(dataList)) {
//            log.info(JSONObject.toJSONString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataList.get(0).getField5())));
//            log.info(JSONObject.toJSONString(dataList.get(2)));
//        }

        ExcelReader excelReader = builder.filePath("D:/storage/38e7dbe8e9984859a705ecb1e3772b28/2019-12-18/3825beef979f4214a0937d6945eb1fb0.xls").build();
//        List<StaffVO> dataList = excelReader.readBody(1).read(StaffVO.class);
//        if (!CollectionUtils.isEmpty(dataList)) {
//            log.info(JSONObject.toJSONString(dataList.get(0)));
//            log.info(JSONObject.toJSONString(dataList.get(2)));
//        }
    }
}
