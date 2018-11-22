package com.parinati.greetings.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class xlsReader {
	static XSSFWorkbook wb;
	static FileInputStream fis;
	static FileOutputStream fout;
	static File src;
	private XSSFSheet sheet = null;

	public xlsReader(String excelPath) {
		try {
			src = new File(excelPath); // specify file path
			fis = new FileInputStream(src);// read file
			wb = new XSSFWorkbook(fis); // load workbook
			sheet = wb.getSheetAt(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// returns the row count in a sheet
	public int getRowCount(String sheetName) {
		int index = wb.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = wb.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	public String getData(int sheetnum, int rownum, int colnum) {
		String data = "";
		Cell cellData = wb.getSheetAt(sheetnum).getRow(rownum).getCell(colnum);
		switch (cellData.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			data = "";
			break;
		case Cell.CELL_TYPE_STRING:
			
			//data = String .format("%s", cellData.getRichStringCellValue());
			data = cellData.getStringCellValue();
			break;
		}
		return data; // return cell data
	}

	public void pushData(int sheetnum, int rownum, int colnum) {
		wb.getSheetAt(sheetnum).getRow(rownum).createCell(colnum).setCellValue("Pass"); // write data to rownum and
																						// colnum
		try {
			wb.write(new FileOutputStream(src));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void closeWorkbook() throws Exception {
		wb.close();
	}

}
