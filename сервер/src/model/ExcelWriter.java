package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    private SXSSFWorkbook workbook;
    Map<String, Map<Integer, Integer>> columnWidth = new HashMap<String, Map<Integer, Integer>>();
    Map<String, Integer> rows = new HashMap<String, Integer>();

    public ExcelWriter() {
        workbook = new SXSSFWorkbook(100);
    }

    public Sheet createSheet(String name) {
        String sheetName = name.replaceAll("\\\\", "-").trim();
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        return sheet;
    }

    @SuppressWarnings("unchecked")
    public Row createRow(Sheet sheet, Object[] values, boolean header, boolean withStyle) {
        Row row;
        String sheetName = sheet.getSheetName();
        int rownum = 0;
        if (rows.containsKey(sheetName)) {
            rownum = rows.get(sheetName);
        }
        row = sheet.createRow(rownum);
        for (int x = 0; x < values.length; x++) {
            Object o = values[x];
            Cell cell = row.createCell(x);
            if (o != null) {
                if (o instanceof String) {
                    String value = (String) o;
                    cell.setCellValue(value);
                }
            }
        }
        rows.put(sheetName, ++rownum);
        return row;

    }

    public void setAutoSizeColumns(Sheet sheet, boolean withHeader) {
        if (sheet.getLastRowNum() > 0) {
            if (withHeader) {
                int x = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
                CellRangeAddress range = new CellRangeAddress(0, 0, 0, x - 1);
                sheet.setAutoFilter(range);
                sheet.createFreezePane(0, 1);
            }
            if (columnWidth.containsKey(sheet.getSheetName())) {
                Map<Integer, Integer> width = columnWidth.get(sheet.getSheetName());
                for (Map.Entry<Integer, Integer> entry : width.entrySet()) {
                    sheet.setColumnWidth(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public boolean saveToFile(String fileName) throws IOException {
        for (int x = 0; x < workbook.getNumberOfSheets(); x++) {
            SXSSFSheet sheet = workbook.getSheetAt(x);
            sheet.flushRows();
        }
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.dispose();
        return (true);
    }

    public ArrayList<Row> readFromExcel(String file1) throws IOException {
        ArrayList<Row> rowArrayList = new ArrayList<>();
        File file = new File(file1);
        if (file.exists()) {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));

            XSSFSheet myExcelSheet = myExcelBook.getSheet("Sheet");
            if (myExcelSheet != null) {
                XSSFRow row = myExcelSheet.getRow(0);
                int i = 1;
                while (row != null) {
                    rowArrayList.add(row);
                    row = myExcelSheet.getRow(i);
                    i++;
                }
            }
            myExcelBook.close();
        }
        return rowArrayList;
    }

}