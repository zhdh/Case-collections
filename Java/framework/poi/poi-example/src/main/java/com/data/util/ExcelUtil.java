package com.data.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel took bulk export
 * 
 * @author github.com/zhdh
 *
 */
public class ExcelUtil {

	/***
	 * ������
	 */
	private static HSSFWorkbook workbook;

	/***
	 * sheet
	 */
	private static HSSFSheet sheet;
	/***
	 * �����п�ʼλ��
	 */
	private static final int TITLE_START_POSITION = 0;

	/***
	 * ʱ���п�ʼλ��
	 */
	private static final int DATEHEAD_START_POSITION = 1;

	/***
	 * ��ͷ�п�ʼλ��
	 */
	private static final int HEAD_START_POSITION = 2;

	/***
	 * �ı��п�ʼλ��
	 */
	private static final int CONTENT_START_POSITION = 3;

	/**
	 * @param dataList
	 *            ���󼯺�
	 * @param titleMap
	 *            ��ͷ��Ϣ��������������->Ҫ��ʾ�ı���ֵ)[��˳�����]
	 * @param sheetName
	 *            sheet���ƺͱ�ͷֵ
	 */
	public static void excelExport(List<?> dataList, Map<String, String> titleMap, String sheetName) {
		// ��ʼ��workbook
		initHSSFWorkbook(sheetName);
		// ������
		// createTitleRow(titleMap, sheetName);
		// ʱ����
		// createDateHeadRow(titleMap);
		// ��ͷ��
		createHeadRow(titleMap);
		// �ı���
		createContentRow(dataList, titleMap);
		// �����п�
		// fixedSizeColumn(titleMap.size());
		// �Զ��п�
		autoSizeColumn(titleMap.size());
		// д�봦����
		try {
			// ����UUID�ļ�����
			UUID uuid = UUID.randomUUID();
			String filedisplay = uuid + ".xls";
			// ���web��Ŀ��1���������ؿ�ĵ���������response��ز���)��2��ͨ��httpservletresponse.getOutputStream()��ȡ
			OutputStream out = new FileOutputStream("D:\\" + filedisplay);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HSSFWorkbook excelExport(List<?> dataList, Map<String, String> titleMap, String sheetName,
			String fileName) {
		// ��ʼ��workbook
		initHSSFWorkbook(sheetName);
		// ������
		// createTitleRow(titleMap, sheetName);
		// ʱ����
		// createDateHeadRow(titleMap);
		// ��ͷ��
		createHeadRow(titleMap);
		// �ı���
		createContentRow(dataList, titleMap);
		// �����п�
		fixedSizeColumn(titleMap.size());
		// д�봦����
		/*
		 * try { //����UUID�ļ����� String filedisplay = fileName + ".xls";
		 * //���web��Ŀ��1���������ؿ�ĵ���������response��ز���)��2��ͨ��httpservletresponse.getOutputStream(
		 * )��ȡ //OutputStream out = new FileOutputStream("D:\\" + filedisplay);
		 * 
		 * // д�� //workbook.write(out); //out.close(); return workbook; } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		return workbook;
	}

	/***
	 *
	 * @param sheetName
	 *            sheetName
	 */
	private static void initHSSFWorkbook(String sheetName) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetName);
	}

	/**
	 * ���ɱ��⣨�����д�����
	 *
	 * @param titleMap
	 *            ������������->��ͷ��ʾ����
	 * @param sheetName
	 *            sheet����
	 */
	private static void createTitleRow(Map<String, String> titleMap, String sheetName) {
		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, titleMap.size() - 1);

		sheet.addMergedRegion(titleRange);

		HSSFRow titleRow = sheet.createRow(TITLE_START_POSITION);
		// �����и�
		titleRow.setHeight((short) 350);
		HSSFCell titleCell = titleRow.createCell(TITLE_START_POSITION);
		titleCell.setCellValue(sheetName);
	}

	/**
	 * ����ʱ���У���һ�д�����
	 *
	 * @param titleMap
	 *            ������������->��ͷ��ʾ����
	 */
	private static void createDateHeadRow(Map<String, String> titleMap) {
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, titleMap.size() - 1);
		sheet.addMergedRegion(dateRange);
		HSSFRow dateRow = sheet.createRow(DATEHEAD_START_POSITION);
		dateRow.setHeight((short) 350);
		HSSFCell dateCell = dateRow.createCell(TITLE_START_POSITION);
		dateCell.setCellValue(new SimpleDateFormat("yyyy��MM��dd��").format(new Date()));
	}

	/**
	 * ������ͷ�У��ڶ��д�����
	 *
	 * @param titleMap
	 *            ������������->��ͷ��ʾ����
	 */
	private static void createHeadRow(Map<String, String> titleMap) {
		// ��1�д���
		/* HSSFRow headRow = sheet.createRow(HEAD_START_POSITION); */
		HSSFRow headRow = sheet.createRow(0);
		headRow.setHeight((short) 450);

		int i = 0;
		for (String entry : titleMap.keySet()) {
			HSSFCell headCell = headRow.createCell(i);

			headCell.setCellStyle(createCellStyle(workbook, (short) 10));

			headCell.setCellValue(titleMap.get(entry));
			sheet.setColumnWidth(i, 5000);
			i++;
		}
	}

	/**
	 * @param dataList
	 *            �������ݼ���
	 * @param titleMap
	 *            ��ͷ��Ϣ
	 */
	private static void createContentRow(List<?> dataList, Map<String, String> titleMap) {
		try {
			int i = 0;
			for (Object obj : dataList) {
				/* HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i); */
				HSSFRow textRow = sheet.createRow(1 + i);
				// �����и�
				textRow.setHeight((short) 350);
				int j = 0;
				for (String entry : titleMap.keySet()) {
					String method = "get" + entry.substring(0, 1).toUpperCase() + entry.substring(1);
					Method m = obj.getClass().getMethod(method, (Class<?>[]) null);

					// �������
					String value = "";
					if (m.invoke(obj, (Object[]) null) != null) {
						value = m.invoke(obj, (Object[]) null).toString();
					}

					HSSFCell textcell = textRow.createCell(j);
					// ����ʽ
					textcell.setCellStyle(cellAndRowCenter(workbook));
					textcell.setCellValue(value);
					j++;
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �Զ������У���Ǳ�Ҫ������򿪴˷��������ڴ棩
	 *
	 * @param size
	 *            ����
	 */
	private static void autoSizeColumn(Integer size) {
		for (int j = 0; j < size; j++) {
			sheet.autoSizeColumn(j);
		}
	}

	/**
	 * �п�
	 * 
	 * @param size
	 *            row
	 */
	private static void fixedSizeColumn(Integer size) {
		for (int i = 0; i < size - 1; i++) {
			sheet.setColumnWidth(i, 3000);
		}
		sheet.setColumnWidth(size, 6500);
	}

	/**
	 * ���þ��� �� �����С
	 * 
	 * @param workbook
	 *            workbook
	 * @param fontSize
	 *            �����С
	 * @return HSSFCelStyle
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		// ˮƽ����
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// ��ֱ����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// ��������
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(fontSize);
		// ��������
		style.setFont(font);
		return style;
	}

	/**
	 * �о���
	 * 
	 * @param workbook
	 *            workbook
	 * @return HSSFCelStyle
	 */
	private static HSSFCellStyle cellAndRowCenter(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		// ˮƽ����
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// ��ֱ����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;
	}

}
