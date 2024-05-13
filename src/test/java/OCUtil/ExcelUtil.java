package OCUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public FileInputStream fileInputStream;
	public FileOutputStream fileOutputStream;
	public XSSFWorkbook workBook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle cellStyle;
	String path;
	String returnLines;
	public ExcelUtil(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws Exception {

		fileInputStream = new FileInputStream(path);
		workBook = new XSSFWorkbook(fileInputStream);
		sheet = workBook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		
		//Imprime las lineas que se ejecutaran del excel
		StringBuilder linesExecuted = new StringBuilder();
		for (int i = 1; i <= rowCount; i++) {
			linesExecuted.append(i + 1);
	        if (i < rowCount) {
	            linesExecuted.append(", ");
	        }
		}
		System.out.println(">>> Ejecutando líneas [" + linesExecuted.toString() + "]");
		
		workBook.close();
		fileInputStream.close();

		return rowCount;
	}
	public int getCellCount(String sheetName, int rowNumber) throws Exception {
		fileInputStream = new FileInputStream(path);
		workBook = new XSSFWorkbook(fileInputStream);
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNumber);
		int cellCount = row.getLastCellNum();
		workBook.close();
		fileInputStream.close();

		return cellCount;
	}

	public String getCellData(String sheetName, int rowNumber, int cellNumber) throws Exception {
		fileInputStream = new FileInputStream(path);
		workBook = new XSSFWorkbook(fileInputStream);
		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(rowNumber);
		cell = row.getCell(cellNumber);

		DataFormatter formatter = new DataFormatter();
		String data;

		try {
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		workBook.close();
		fileInputStream.close();
		return data;
	}

	public void setCellData(String sheetName, int rowNumber, int cellNumber, String data) throws Exception {
		File xlFile = new File(path);
		if (!xlFile.exists()) {
			workBook = new XSSFWorkbook();
			fileOutputStream = new FileOutputStream(path);
			workBook.write(fileOutputStream);
		}

		fileInputStream = new FileInputStream(xlFile);
		workBook = new XSSFWorkbook(fileInputStream);

		if (workBook.getSheetIndex(sheetName) == -1) // If the sheet does not exist, a new sheet will be created.
			workBook.createSheet(sheetName);
		sheet = workBook.getSheet(sheetName);

		if (sheet.getRow(rowNumber) == null) // If the row does not exist, a new row will be created.
			sheet.createRow(rowNumber);
		row = sheet.getRow(rowNumber);

		cell = row.createCell(cellNumber);
		cell.setCellValue(data);
		fileOutputStream = new FileOutputStream(path);
		workBook.write(fileOutputStream);

		workBook.close();
		fileInputStream.close();
		fileOutputStream.close();
	}

	public void fillGreenCellColor(String sheetName, int rowNumber, int cellNumber) throws Exception {
		fileInputStream = new FileInputStream(path);
		workBook = new XSSFWorkbook(fileInputStream);
		sheet = workBook.getSheet(sheetName);

		row = sheet.getRow(rowNumber);
		cell = row.getCell(cellNumber);

		cellStyle = workBook.createCellStyle();

		cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(cellStyle);
		workBook.write(fileOutputStream);
		workBook.close();
		fileInputStream.close();
		fileOutputStream.close();
	}

	public void fillRedCellColor(String sheetName, int rowNumber, int cellNumber) throws Exception {
		fileInputStream = new FileInputStream(path);
		workBook = new XSSFWorkbook(fileInputStream);
		sheet = workBook.getSheet(sheetName);

		row = sheet.getRow(rowNumber);
		cell = row.getCell(cellNumber);

		cellStyle = workBook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(cellStyle);
		workBook.write(fileOutputStream);
		workBook.close();
		fileInputStream.close();
		fileOutputStream.close();

	}
}
