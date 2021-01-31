package serialnumber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


    final class ReadWriteFile {
	
     public static final ArrayList<String> readExcel(File file, String sheetname) throws IOException{
		
    	 if(file.exists()) {
    		 
    		 ArrayList<String> sheet = new ArrayList<>();
    		 String fileName = file.getName();
    		 DataFormatter formatter = new DataFormatter();
    		 String val = null;
    		 StringBuilder sb = new StringBuilder();
    		 int rowCount = 0;
    		 
    		 FileInputStream inputStream = new FileInputStream(file);

    		 Workbook wb = null;

    		 String fileExtensionName = fileName.substring(fileName.indexOf("."));


    		 if(fileExtensionName.equals(".xlsx")){

    		    wb = new XSSFWorkbook(inputStream);

    		 }

    		 else if(fileExtensionName.equals(".xls")){

    		     wb = new HSSFWorkbook(inputStream);
    		     }
    		 
    		    
    		 Sheet sheet1 = wb.getSheet(sheetname);
    		     		 
    		     int r = sheet1.getLastRowNum()- sheet1.getFirstRowNum();
                
    	    	 
    	    	 for (int i = 0; i < r; i ++) {
    	    		 
    	    		 if(sheet1.getRow(i).getCell(0) != null) {rowCount++;};
    	    	 }


    	    	 
    		    for (int i = 0; i < rowCount; i++) {

    		        Row row = sheet1.getRow(i);
    		        

    		        for (int j = 0; j < row.getLastCellNum(); j++) {

    		         val = formatter.formatCellValue(row.getCell(j));
    		         
    		         sb.append(val + ",");

    		        }

    		        sheet.add(sb.toString());
    		        sb.setLength(0);   // empty string builder
    		        
    		    } 

    		    inputStream.close();  
    		     		    
    		 return sheet;
    	 }
    	 
    	 else throw new FileNotFoundException();
    	 
     }
     
     public static final void writeExcel(File file, String sheetname, String row) throws IOException {
    	 
    	 FileInputStream inputStream = new FileInputStream(file);
    	 Workbook workbook = WorkbookFactory.create(inputStream);

		 String[] data = row.split(",");
         int rowCount = 0;
		 Sheet sheet1 = workbook.getSheet(sheetname);
		 
    	 int r = sheet1.getLastRowNum();
    	 for (int i = 0; i < r; i ++) {
    		 
    		 if(sheet1.getRow(i).getCell(0) != null) {rowCount++;};
    	 }
    	 

    	 Row newrow = sheet1.createRow(rowCount);
         int columnCount = 0;
         
         Cell cell = newrow.createCell(columnCount);
         cell.setCellValue(rowCount);
             for (Object field : data) {
            	 
                 cell = newrow.createCell(columnCount);
                 if (field instanceof String) {
                     cell.setCellValue((String) field);
                 } else if (field instanceof Integer) {
                     cell.setCellValue((Integer) field);
                 }
                 columnCount++;
               }
             
          
         inputStream.close();

         FileOutputStream outputStream = new FileOutputStream(file);
         workbook.write(outputStream);
         workbook.close();
         outputStream.close();
    	 
    	 
     }
     



}
