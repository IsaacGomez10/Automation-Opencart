package OCUtil;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// Data provider 1
	@DataProvider(name = "LoginData")
	public String[][] getData() throws Exception {
		String path =".\\testData\\Opencart_LoginData.xlsx";// Take the excel file from testData

		ExcelUtil excelUtil = new ExcelUtil(path); // Creating an object for ExcelUtil

		int totalRows = excelUtil.getRowCount("Sheet1");
		int totalCells = excelUtil.getCellCount("Sheet1", 1);

		String loginData[][] = new String[totalRows][totalCells];// Created for two dimension array which can

		for (int r = 1; r <= totalRows; r++) { // 1 //Read the data from excel storing in two dimensional array
			for (int c = 0; c < totalCells; c++) { // 0 (r) is row and (c) is cell or column
				loginData[r - 1][c] = excelUtil.getCellData("Sheet1", r, c);
			}
		}

		return loginData; // returning two dimension array
	}

}
