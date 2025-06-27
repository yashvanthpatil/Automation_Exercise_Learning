package com.qa.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReadConfig {

    private Properties prop;
    /**
     * This method is used to load the properties from config.properties file
     * @return it returns Properties prop object
     */
    public Properties init_prop() {

        prop = new Properties();
        try {
            FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
            prop.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }

    public Map<String, String> getUserDataFromExcel(String sheetName) {
        Map<String, String> userData = new HashMap<>();
        FileInputStream fis = null; // Declare outside try for finally block
        XSSFWorkbook workbook = null; // Declare outside try for finally block
        try {
            fis = new FileInputStream("./src/test/resources/config/NewUserData.xlsx");
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                System.err.println("Sheet '" + sheetName + "' not found in NewUserData.xlsx");
                return userData; // Return empty map if sheet not found
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("Header row (row 0) not found in sheet '" + sheetName + "'");
                return userData;
            }

            Row dataRow = sheet.getRow(1); // Assuming data is always in the second row (index 1) for now
            if (dataRow == null) {
                System.err.println("Data row (row 1) not found in sheet '" + sheetName + "'");
                return userData;
            }

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell headerCell = headerRow.getCell(i);
                Cell dataCell = dataRow.getCell(i);

                if (headerCell == null || dataCell == null) {
                    continue; // Skip if either header or data cell is null
                }

                String key = getCellValueAsString(headerCell);
                String value = getCellValueAsString(dataCell);

                if (key != null && !key.trim().isEmpty()) { // Ensure key is not null or empty
                    userData.put(key.trim(), value != null ? value.trim() : "");
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Excel file not found: ./src/test/java/resources/config/NewUserData.xlsx");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while reading Excel data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userData;
    }

    // Helper method to get cell value as String regardless of type
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string for null cells
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    // If it's a date, format as needed (e.g., "dd/MM/yyyy")
                    return new java.text.SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                } else {
                    // For general numbers, convert to String.
                    // If your HTML expects "3" instead of "03" for day/month:
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) { // Check if it's an integer
                        return String.valueOf((long) numericValue); // Return "3" for 3.0
                    } else {
                        return String.valueOf(numericValue); // Return "3.5" for 3.5
                    }
                    // OR if you ALWAYS want two digits (e.g., for months like "01", "02"):
                    // return String.format("%02d", (int) cell.getNumericCellValue()); // This would give "03" for 3
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                // Evaluate formula to get its result, then convert to String
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return cell.getStringCellValue();
                    case NUMERIC:
                        return String.valueOf((long) cell.getNumericCellValue()); // Example conversion
                    case BOOLEAN:
                        return String.valueOf(cell.getBooleanCellValue());
                    default:
                        return "";
                }
            case BLANK:
                return "";
            case ERROR:
                return "ERROR_CELL";
            default:
                return "";
        }
    }
}



