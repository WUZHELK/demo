package com.example.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.demo.bean.vo.Demo;
import com.example.demo.bean.vo.FileEntity;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtils {

    public static final String EASYIMPORT_PATH = "D:/meimei/easyimport/";

    public static final String EASYOUTPORT_PATH = "D:/meimei/easyoutport/";

    public static final String PATH = "D:/meimei/download/";

    public static final String UPLOADPATH = "D:/meimei/upload/";

    public static void main(String[] args) throws FileNotFoundException {
        EasyExcelOutport2();
    }

    public static void EasyExcelImport() {
        String filePath = EASYIMPORT_PATH + "easyexcel-int.xlsx";
        List<FileEntity> list = EasyExcel.read(filePath).head(FileEntity.class).sheet().doReadSync();
        System.out.println(list);
    }

    public static void EasyExcelOutport() {
        List<FileEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName("张三" + i);
            fileEntity.setAge(20 + i);
            fileEntity.setTime(new Date(System.currentTimeMillis() + i));
            dataList.add(fileEntity);
        }
        EasyExcel.write(EASYOUTPORT_PATH + "easyexcel-out.xlsx", FileEntity.class).sheet("Sheet1").doWrite(dataList);
    }

    public static void EasyExcelOutport2() throws FileNotFoundException {
        ArrayList<Demo> ls = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            demo.setUsername("name" + i);
            demo.setPassword("password" + i);
            demo.setAge(i);
            demo.setGender((i % 10 == 0) ? "男" : "女");

            ls.add(demo);
        }

        // 设置单元格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(ExcelStyleUtils.getHeadStyle(), ExcelStyleUtils.getContentStyle());

        ExcelWriter excelWriter = EasyExcel.write(new FileOutputStream(EASYOUTPORT_PATH + "demo.xlsx"))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .build();
        // 总体统计 sheet
        WriteSheet totalSheet = EasyExcel.writerSheet(0, "总体统计").head(Demo.class).build();
        excelWriter.write(ls, totalSheet);
        // web导出，这里必须要有这个finish，不然导出的文件是空的，官方文档的案例没写
        excelWriter.finish();
    }

    public static void XSSFExcelUpload() {
        try (FileInputStream inputStream = new FileInputStream(UPLOADPATH + "用户信息表2007BigData.xlsx")) {
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

    public static void SXSSFExcelUpload() throws Exception {
        //获取文件流
        //1.创建工作簿,使用excel能操作的这边都看看操作
        OPCPackage opcPackage = OPCPackage.open(UPLOADPATH + "用户信息表2007BigData.xlsx");
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        StylesTable stylesTable = xssfReader.getStylesTable();
        ReadOnlySharedStringsTable sharedStringsTable = new ReadOnlySharedStringsTable(opcPackage);
        // 创建XMLReader，设置ContentHandler
        XMLReader xmlReader = SAXHelper.newXMLReader();
        xmlReader.setContentHandler(new XSSFSheetXMLHandler(stylesTable, sharedStringsTable, new SimpleSheetContentsHandler(), false));
        // 解析每个Sheet数据
        Iterator<InputStream> sheetsData = xssfReader.getSheetsData();
        while (sheetsData.hasNext()) {
            try (InputStream inputStream = sheetsData.next();) {
                xmlReader.parse(new InputSource(inputStream));
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

    public static void getValue(Cell cell) {
        //匹配类型数据
        if (cell != null) {
            CellType cellType = cell.getCellType();
            String cellValue = "";
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
            System.out.println(cellValue);
        }
    }

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

        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH + "用户信息表2007BigData.xlsx")) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            log.error("XSSFExcelWrite error: " + e);
        }
        log.info("XSSFExcelWrite end");
    }

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
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH + "用户信息表2007BigDataS.xlsx")) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            log.error("SXSSFExcelWrite error: " + e);
        }

    }

}
