package com.example.demo.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.demo.bean.dto.MeiMeiFileDTO;
import com.example.demo.bean.vo.AddressEntity;
import com.example.demo.bean.vo.FileAddressEntity;
import com.example.demo.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel工具类
 *
 * @author wuzhe
 * @date 2023-9-18 16:48:37
 */
@Slf4j
public class ExcelUtils {

    /**
     * EasyExcel导入Excel
     *
     * @param cls
     * @param fileInputPath
     * @return
     */
    public static List<?> EasyExcelImport(Class cls, String fileInputPath) {
        return EasyExcel.read(Constant.StringFields.EASYIMPORT_PATH + fileInputPath).head(cls).sheet().doReadSync();
    }

    /**
     * EasyExcel根据过滤的数据导出Excel
     *
     * @param fileInputPath
     * @param copyFlag
     * @param dataList
     * @param fileOutputPath
     * @param cls
     * @throws FileNotFoundException
     */
    public static void EasyExcelOutport(String fileInputPath, Boolean copyFlag,
                                        List<?> dataList, String fileOutputPath, Class cls) throws FileNotFoundException {
        // 设置单元格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(ExcelStyleUtils.getHeadStyle(), ExcelStyleUtils.getContentStyle());
        // 列宽策略设置
        ExcelCellWidthStyleStrategy widthStyleStrategy = new ExcelCellWidthStyleStrategy();
        ExcelWriter excelWriter = null;
        if (copyFlag) {
            excelWriter = EasyExcel.write(new FileOutputStream(Constant.StringFields.EASYOUTPORT_PATH + fileOutputPath))
                    .withTemplate(fileInputPath).needHead(true).useDefaultStyle(false)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(widthStyleStrategy)
                    .build();
        } else {
            excelWriter = EasyExcel.write(new FileOutputStream(Constant.StringFields.EASYOUTPORT_PATH + fileOutputPath))
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .registerWriteHandler(widthStyleStrategy)
                    .build();
        }

        // 总体统计 sheet
        WriteSheet totalSheet = EasyExcel.writerSheet(0, "匹配数据").head(cls).build();
        excelWriter.write(dataList, totalSheet);

        // web导出，这里必须要有这个finish，不然导出的文件是空的，官方文档的案例没写
        excelWriter.finish();
    }

    /**
     * XSSF导入demo
     */
    public static void XSSFExcelUpload() {
        try (FileInputStream inputStream = new FileInputStream(Constant.StringFields.UPLOADPATH + "用户信息表2007BigData.xlsx")) {
            //1.创建工作簿,使用excel能操作的这边都看看操作
            Workbook workbook = new XSSFWorkbook(inputStream);
            //2.得到表
            Sheet sheet = workbook.getSheetAt(0);
            //3.得到行
            Row row = sheet.getRow(0);
            //4.得到列
            Cell cell = row.getCell(0);
            getValue(cell);
        } catch (IOException e) {
            log.error("XSSFExcelUpload error: " + e);
        }
    }

    /**
     * SXSSF导入demo
     *
     * @throws Exception
     */
    public static void SXSSFExcelUpload() throws Exception {
        //获取文件流
        //1.创建工作簿,使用excel能操作的这边都看看操作
        OPCPackage opcPackage = OPCPackage.open(Constant.StringFields.UPLOADPATH + "用户信息表2007BigData.xlsx");
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        StylesTable stylesTable = xssfReader.getStylesTable();
        ReadOnlySharedStringsTable sharedStringsTable = new ReadOnlySharedStringsTable(opcPackage);
        // 创建XMLReader，设置ContentHandler
        XMLReader xmlReader = SAXHelper.newXMLReader();
        xmlReader.setContentHandler(new XSSFSheetXMLHandler(stylesTable, sharedStringsTable, new SimpleSheetContentsHandler(), false));
        // 解析每个Sheet数据
        Iterator<InputStream> sheetsData = xssfReader.getSheetsData();
        while (sheetsData.hasNext()) {
            try (InputStream inputStream = sheetsData.next()) {
                xmlReader.parse(new InputSource(inputStream));
            } catch (IOException e) {
                log.error("SXSSFExcelUpload is error: " + e.getMessage());
            }
        }
    }

    /**
     * 内容处理器
     */
    public static class SimpleSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        protected List<String> row;

        /**
         * A row with the (zero based) row number has started
         *
         * @param rowNum
         */
        @Override
        public void startRow(int rowNum) {
            row = new ArrayList<>();
        }

        /**
         * A row with the (zero based) row number has ended
         *
         * @param rowNum
         */
        @Override
        public void endRow(int rowNum) {
            if (row.isEmpty()) {
                return;
            }
            // 处理数据
            System.out.println(row.stream().collect(Collectors.joining("   ")));
        }

        /**
         * A cell, with the given formatted value (may be null),
         * and possibly a comment (may be null), was encountered
         *
         * @param cellReference
         * @param formattedValue
         * @param comment
         */
        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            row.add(formattedValue);
        }

        /**
         * A header or footer has been encountered
         *
         * @param text
         * @param isHeader
         * @param tagName
         */
        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
    }

    public static String getValue(Cell cell) {
        String cellValue = "";
        //匹配类型数据
        if (cell != null) {
            CellType cellType = cell.getCellType();

            switch (cellType) {
                case STRING: //字符串
                    System.out.print("[String类型]");
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN: //布尔类型
                    System.out.print("[boolean类型]");
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK: //空
                    System.out.print("[BLANK类型]");
                    break;
                case NUMERIC: //数字（日期、普通数字）
                    System.out.print("[NUMERIC类型]");
                    if (HSSFDateUtil.isCellDateFormatted(cell)) { //日期
                        System.out.print("[日期]");
                        Date date = cell.getDateCellValue();
                        cellValue = new DateTime(date).toString("yyyy-MM-dd");
                    } else {
                        //不是日期格式，防止数字过长
                        System.out.print("[转换为字符串输出]");
                        cell.setCellType(CellType.STRING);
                        cellValue = cell.toString();
                    }
                    break;
                case ERROR:
                    System.out.print("[数据类型错误]");
                    break;
            }
        }
        return cellValue;
    }

    /**
     * XSSF写入
     *
     * @throws Exception
     */
    public static void XSSFExcelWrite() throws Exception {
        log.info("XSSFExcelWrite start...");
        //创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        //创建表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNumber = 0; rowNumber < 65537; rowNumber++) {
            Row row = sheet.createRow(rowNumber);
            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber);
                cell.setCellValue(cellNumber);
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(Constant.StringFields.PATH + "用户信息表2007BigData.xlsx")) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            log.error("XSSFExcelWrite error: " + e);
        }
        log.info("XSSFExcelWrite end");
    }

    /**
     * SXSSF写入
     */
    public static void SXSSFExcelWrite() {
        log.info("SXSSFExcelWrite start...");
        //创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();
        //创建表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNumber = 0; rowNumber < 100000; rowNumber++) {
            Row row = sheet.createRow(rowNumber);
            for (int cellNumber = 0; cellNumber < 10; cellNumber++) {
                Cell cell = row.createCell(cellNumber);
                cell.setCellValue(cellNumber);
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(Constant.StringFields.PATH + "用户信息表2007BigDataS.xlsx")) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            log.error("SXSSFExcelWrite error: " + e);
        }

    }

}
