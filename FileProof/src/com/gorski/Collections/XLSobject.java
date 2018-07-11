package com.gorski.Collections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSobject {
	private HashMap<String, HashMap<Integer,String>> dataMap = 
			new HashMap<String,HashMap<Integer,String>>();
	private HashMap<Integer, String> rowMap = new HashMap<Integer, String>();
	private String fileName;
	private String sheetName;
	/*
	 * store cells completely
	 */
	private ArrayList<Cell> rowCells = new ArrayList<Cell>();
	private HashMap<Integer, ArrayList<Cell>> rowCellMap = new HashMap<Integer, ArrayList<Cell>>();
	private HashMap<String,HashMap<Integer, ArrayList<Cell>>> sheetMap = 
			new HashMap<String,HashMap<Integer, ArrayList<Cell>>> ();

	public XLSobject(String fileInput) {
		super();
		String[] fileData = fileInput.split("@");
		this.fileName = fileData[0];
		this.sheetName = fileData[1];
		this.collectData();
	}
	/***
	 * 
	 * @param sheetName
	 * @return
	 */
	public HashMap<Integer,String> getData(String sheetName) {
		/*
		 * create comma seperated strings of cell values, one per row
		 */
		HashMap<Integer, ArrayList<Cell>> rowDataMap = sheetMap.get(sheetName);
		for(Integer i:rowDataMap.keySet()) {
			String rowString="";
			int colNum = 0;
			for(Cell c:rowDataMap.get(i)) {
				if(c.getColumnIndex()==colNum) {
                    c.setCellType(Cell.CELL_TYPE_STRING); 
					rowString=rowString.concat(c.getStringCellValue()+",");
				} else {
					for(int j=colNum;j<c.getColumnIndex();j++) {
						rowString=rowString.concat(",");
					}
                    c.setCellType(Cell.CELL_TYPE_STRING); 
					rowString=rowString.concat(c.getStringCellValue()+",");
				}
				colNum=c.getColumnIndex()+1;
			}
			rowMap.put(i, rowString);
		}
		System.out.println("sheetName get " + sheetName);
		return rowMap;
	}

	/***
	 * read xls file and save cells
	 */
	private void collectData() {
		/*
		 * open workbook file save data in map of row based cell arrays
		 */
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		/*
		 * for each sheet
		 */
		for (Sheet sheet : workbook) {
			String sheetName = sheet.getSheetName();
			Integer rowNum = 0;
			HashMap<Integer, ArrayList<Cell>> rowCellMap = new HashMap<Integer, ArrayList<Cell>>();
			/*
			 * for each row
			 */
			HashMap<Integer, String> rowMap = new HashMap<Integer, String>();
			for (Row row : sheet) {
					ArrayList<Cell> rowCells = new ArrayList<Cell>();
					for ( Cell cell : row) {
						rowCells.add(cell);
					}
					rowCellMap.put(rowNum, rowCells);
					try {
						workbook.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				rowNum++;
			}
			/*
			 * save data array in map by sheet name
			 */
			sheetMap.put(sheetName, rowCellMap);
			System.out.println(" saving " + sheetName);
		}
		
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
