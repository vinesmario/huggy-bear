package com.vinesmario.microservice.server.document.excel.strategy;

import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Excel2007 implements ExcelStrategy {

    private String fileName;
    private String sheetName;
    private String title;
    private List<ExcelColumn> columns;

    public void doImport() throws Exception {

    }

    public void doExport() throws Exception {

    }
    
}
