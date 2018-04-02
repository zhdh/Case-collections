package com.data.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class ExcelHandler {
	
	@Test
	public void simpleExample() throws IOException {
		// create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// create new work sheet
		HSSFSheet sheet = workbook.createSheet("hello");
		// create row
		HSSFRow row = sheet.createRow(0);
		// create cell
		HSSFCell cell = row.createCell(2);
		// set cell value
		cell.setCellValue("Hello Excel");
		// output to disk
		FileOutputStream fos = new FileOutputStream(new File("E:\\simpleExample.xls"));
		workbook.write(fos);
		workbook.close();
		fos.close();
	}

}
