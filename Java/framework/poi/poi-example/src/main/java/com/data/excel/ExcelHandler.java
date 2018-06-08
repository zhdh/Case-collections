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
	public void simpleWriteExample() throws IOException {
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
	
	@Test
	public void simpleReadExample() throws IOException {
		// create output stream
		FileInputStream fis = new FileInputStream(new File("E:\\simpleExample.xls"));
		// set parameter
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		// get sheet
		HSSFSheet sheet = workbook.getSheetAt(0);
		// get row
		HSSFRow row = sheet.getRow(0);
		// get cell 
		HSSFCell cell = row.getCell(2);
		// get cell value
		String cellValue = cell.getStringCellValue();
		System.out.println("content" + cellValue);
		workbook.close();
		fis.close();
	}
	
	/**
	 * HSSF is the POI Project's pure Java implementation of the Excel '97(-2007)
	 * file format. XSSF is the POI Project's pure Java implementation of the Excel
	 * 2007 OOXML (.xlsx) file format.
	 * 
	 * @throws IOException
	 */
	@Test
	public void XSSFWriteExampl() throws IOException {
		// create xssf type workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// create sheet
		XSSFSheet sheet = workbook.createSheet("hello");
		// create row
		XSSFRow row = sheet.createRow(0);
		// create cell
		XSSFCell cell = row.createCell(2);
		// set cell value
		cell.setCellValue("Hello Excel");
		// create output stream
		FileOutputStream fos = new FileOutputStream(new File("E:\\XSSFWriteExample.xls"));
		workbook.write(fos);
		workbook.close();
		fos.close();
	}

	@Test
	public void XSSFReadExample() throws IOException {
		// create input stream
		FileInputStream fis = new FileInputStream(new File("E:\\\\XSSFWriteExample.xls"));
		// create workbook
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		// get sheet
		XSSFSheet sheet = workbook.getSheet("hello");
		// get row
		XSSFRow row = sheet.getRow(0);
		// create cell
		XSSFCell cell = row.getCell(2);
		// set cell value
		String cellValue = cell.getStringCellValue();
		System.out.println("content: " + cellValue);
		workbook.close();
		fis.close();
	}

}
