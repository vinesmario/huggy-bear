package com.vinesmario.microservice.server.document.excel;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.document.excel.annotation.Excel;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 * 1.HSSF是POI工程操作Excel 2003以前（包括2003）的版本文件（扩展名为.xls）的纯Java实现。
 * 每个Sheet最多可导出为65535行，一般不会发生内存不足的情况（OOM）。
 * <p>
 * 2.XSSF是POI工程操作Excel 2007的版本文件(扩展名为.xlsx)的纯Java实现。
 * 每个Sheet最多可导出1048576行、16384列，伴随着会发生OOM内存溢出的问题。
 * 原因是所创建的Sheet、Row、Cell等对象是存在内存中的，随着数据量增大，内存的需求量也就增大。
 * <p>
 * 3.从POI 3.8版本开始，提供了一种基于XSSF的低内存占用的SXSSFWorkbook。
 * ①SXSSF是对XSSF的一种流式扩展，特点是采用了滑动窗口的机制，低内存占用，
 * 主要用于数据量非常大的电子表格而虚拟机堆有限的情况。
 * ②SXSSFWorkbook.DEFAULT_WINDOW_SIZE默认值是100，表示在内存中最多存在100个Row对象。
 * 当写第101个Row对象的时候就会把第1个Row对象以XML格式写入当前用户本地临时文件夹下的临时文件中，
 * 后面的以此类推，始终保持内存中最多存在100个Row对象。
 * ③SXSSFWorkbook默认使用内联字符串而不是共享字符串表(SharedStringsTable)。
 * 启用共享字符串时，文档中的所有唯一字符串都必须保存在内存中，因此会占用更多的内存。
 * ④与XSSF的对比，在一个时间点上，只可以访问一定数量的Row；不再支持Sheet.clone()；不再支持公式的求值。
 * ⑤但是除了滑动窗口，其余的EXCLE操作仍然使用的是XSSF的API。
 * 另外官方提示导出EXCEL后应该调用wb.dispose()来删除之前保存的临时文件。
 * ⑥SXSSFWorkbook提供了一种低内存占用的EXCEL导出方法，但是没有提供读取文件流的方法。
 * 因此读入大数据量的时候还是只能使用XSSFWorkbook来读取。
 *
 * @author
 */
public class Excel2003 {

    /**
     * 是否远程调用
     */
    private boolean remote = false;

    private ExcelConfig config;

    private Class<? extends BaseDTO> clazz;

    private List<Object[]> dataList = new ArrayList<>();

    private CrudClient client;

    public Excel2003(Class<? extends BaseDTO> clazz, List<Object[]> dataList) {
        config(clazz);
        this.dataList = dataList;
    }

    public Excel2003(ExcelConfig config, List<Object[]> dataList) {
        this.config = config;
        this.dataList = dataList;
    }

    public Excel2003(Class<? extends BaseDTO> clazz, CrudClient client) {
        config(clazz);
        this.client = client;
        this.remote = true;
    }

    public Excel2003(ExcelConfig config, CrudClient client) {
        this.config = config;
        this.client = client;
        this.remote = true;
    }

    private void config(Class<? extends BaseDTO> clazz) {
        Excel excel = clazz.getAnnotation(Excel.class);
        ExcelConfig.ExcelConfigBuilder excelConfigBuilder = ExcelConfig.builder()
                .version(excel.version())
                .extension(excel.version())
                .fileName(excel.fileName())
                .sheetName(excel.sheetName())
                .title(excel.title())
                .dto(clazz)
                .feignClient(excel.feignClient());

        Field[] fields = clazz.getDeclaredFields();
        if (!ObjectUtils.isEmpty(fields)) {
            for (Field field : fields) {
                ExcelColumn[] excelColumns = field.getDeclaredAnnotationsByType(ExcelColumn.class);
                if (!ObjectUtils.isEmpty(excelColumns)) {
                    for (ExcelColumn excelColumn : excelColumns) {
                        if (!ObjectUtils.isEmpty(excelColumn.columnType())) {
                            if (ExcelColumn.ColumnType.BOTH.equals(excelColumn.columnType())
                                    || ExcelColumn.ColumnType.IMPORT.equals(excelColumn.columnType())) {
                                excelConfigBuilder.columnImportConfig(ExcelConfig.ExcelColumnConfig.builder()
                                        .field(field)
                                        .sort(excelColumn.sort())
                                        .catalogCode(excelColumn.catalogCode())
                                        .fieldName(excelColumn.name())
                                        .fieldTypeClass(excelColumn.fieldTypeClass())
                                        .build());
                            } else if (ExcelColumn.ColumnType.BOTH.equals(excelColumn.columnType())
                                    || ExcelColumn.ColumnType.EXPORT.equals(excelColumn.columnType())) {
                                excelConfigBuilder.columnExportConfig(ExcelConfig.ExcelColumnConfig.builder()
                                        .field(field)
                                        .sort(excelColumn.sort())
                                        .catalogCode(excelColumn.catalogCode())
                                        .fieldName(excelColumn.name())
                                        .fieldTypeClass(excelColumn.fieldTypeClass())
                                        .title(excelColumn.title())
                                        .horizontalAlignment(excelColumn.horizontalAlignment())
                                        .verticalAlignment(excelColumn.verticalAlignment())
                                        .cellType(excelColumn.cellType())
                                        .build());
                            }
                        }
                    }
                }
            }
        }
        this.config = excelConfigBuilder.build();
    }

    /*
     * 导出数据
     * */
    public void export() throws Exception {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(config.getSheetName());     // 创建工作表
            int columnNum = config.getColumnExportConfigList().size();

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnHeaderStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getColumnBodyStyle(workbook);           //单元格样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnNum - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(config.getTitle());

            // 定义所需列数
            HSSFRow rowRowName = sheet.createRow(2);                  // 在索引2的位置创建行(最顶端的行开始的第二行)

            config.getColumnExportConfigList().sort(Comparator.comparing(ExcelConfig.ExcelColumnConfig::getSort));
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
                cellRowName.setCellType(CellType.STRING);                       //设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(config.getColumnExportConfigList().get(n).getTitle());
                cellRowName.setCellValue(text);                                 //设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
            }

            //将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);//创建所需的行数

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, CellType.NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, CellType.STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());                        //设置单元格的值
                        }
                    }
                    cell.setCellStyle(style);                                    //设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

            if (workbook != null) {
                try {
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    OutputStream out = FileUtils.openOutputStream(new File(fileName));
                    workbook.write(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnHeaderStyle(HSSFWorkbook workbook) {
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /*
     * 列数据信息单元格样式
     */
    public HSSFCellStyle getColumnBodyStyle(HSSFWorkbook workbook) {
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //在样式用应用设置的字体;
        style.setFont(font);

        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


}
