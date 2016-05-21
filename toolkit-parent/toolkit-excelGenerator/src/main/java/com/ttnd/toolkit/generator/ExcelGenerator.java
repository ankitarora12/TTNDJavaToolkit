/**
 * 
 */
package com.ttnd.toolkit.generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/** A class to generate excel file in .xsl or .xslx format.
 *  This class with accept a List which contain list of Objects(List<List<String>data).
 *  
 * @author nidhi
 *
 */
public class ExcelGenerator {

	private String fileName;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private String sheetName;

	/**
	 * 
	 */
	public ExcelGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	/**
	 * @param workbook the workbook to set
	 */
	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * @param sheet the sheet to set
	 */
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * @param sheetName the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		if(sheetName==null)
			sheetName="Sheet1";
		else{
			int count=getWorkbook().getNumberOfSheets()+1;
			sheetName="Sheet"+count;
		}
		return sheetName;
	}

	/**
	 * @return the workbook
	 */
	public HSSFWorkbook getWorkbook() {
		if (workbook == null)
			workbook = new HSSFWorkbook();
		return workbook;
	}

	/**
	 * @return the sheet
	 */
	private HSSFSheet getSheet() {
		if (sheet == null)
			sheet = getWorkbook().createSheet(getSheetName());
		return sheet;
	}

	public void createExcel(List<List<String>> dataList) throws FileNotFoundException {

		int rownum=0;
		int colnum=0;
		if(dataList!=null){
			HSSFSheet sheet=getSheet();
			for (List<String> list : dataList) {
				colnum=0;
				
				Row row = sheet.createRow(rownum);
				List<String> rowList=list;
				for (String data : rowList) {
					Cell cell = row.createCell(colnum);
					cell.setCellValue(data);
					sheet.autoSizeColumn(colnum);
					colnum++;
				}
				rownum++;
			}
			
			
		}else{
			throw new NullPointerException("dataList is Empty");
		}
	}

	public void addToExcelFile() throws FileNotFoundException {
		FileOutputStream fileOut = new FileOutputStream(getFileName());
		try {
			getWorkbook().write(fileOut);
			fileOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*public static void main(String[] arg) {
		ExcelGenerator e=new ExcelGenerator();
		e.setFileName("/home/nidhi/generator.xls");
		List<String> headerRow = new ArrayList<String>();
		headerRow.add("Employee No");
		headerRow.add("Employee Name");
		headerRow.add("Employee Address");
		 
		List<String> firstRow = new ArrayList<String>();
		firstRow.add("1111");
		firstRow.add("Gautam");
		firstRow.add("India");
		 
		List<List<String>> recordToAdd = new ArrayList<List<String>>();
		recordToAdd.add(headerRow);
		recordToAdd.add(firstRow);
		
		try {
			e.createExcel(recordToAdd);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}*/
}
