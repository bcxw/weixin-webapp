package com.zee.ordering.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXlsUtil {
	/**
	 * 读取xlsx文件
	 * @param fileName
	 */
	public static void readXlsx(String fileName) {
		XSSFWorkbook xssfWorkbook = null  ; 
		InputStream stream = null ; 
		try{
			File file = new File(fileName);  
	        stream = new FileInputStream(file);  
	        xssfWorkbook = new XSSFWorkbook(stream);  
	        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);  
	        int rowstart = xssfSheet.getFirstRowNum();  
	        int rowEnd = xssfSheet.getLastRowNum();  
	        for(int i=rowstart;i<=rowEnd;i++) {  
	            XSSFRow row = xssfSheet.getRow(i);  
	            if(null == row) continue;  
	            int cellStart = row.getFirstCellNum();  
	            int cellEnd = row.getLastCellNum();  
	            for(int k=cellStart;k<=cellEnd;k++){  
	                XSSFCell cell = row.getCell(k);  
	                if(null==cell) continue;  
	                switch (cell.getCellTypeEnum()) {  
	                    case NUMERIC: // 数字  
	                        System.out.print(cell.getNumericCellValue());  
	                        break;  
	                    case STRING: // 字符  
	                        System.out.print(cell.getStringCellValue());  
	                        break;  
	                    case BOOLEAN: // Boolean  
	                        System.out.println(cell.getBooleanCellValue());  
	                        break;  
	                    case FORMULA: // 公式  
	                        System.out.print(cell.getCellFormula());  
	                        break;  
	                    case BLANK: // 空
	                        System.out.println(" ");  
	                        break;  
	                    case ERROR: // 故障  
	                        System.out.println(" ");  
	                        break;  
	                    default:  
	                        System.out.print("未知类型   ");  
	                        break;  
	                }  
	            }  
	            System.out.print("\n");  
	        }  
		}catch(Exception e){
			//TODO
			
		}finally{
			if(null != xssfWorkbook){
				try {
					xssfWorkbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != stream){
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 读取Xls格式文件
	 * @param fileName
	 */
	public static void readXls(String fileName){
		HSSFWorkbook hssfWorkbook = null; 
		try{
			File file = new File(fileName);  
	        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));  
	        hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);  
	        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);  
	        int rowstart = hssfSheet.getFirstRowNum();  
	        int rowEnd = hssfSheet.getLastRowNum();  
	        for(int i=rowstart;i<=rowEnd;i++) {  
	            HSSFRow row = hssfSheet.getRow(i);  
	            if(null == row) continue;  
	            int cellStart = row.getFirstCellNum();  
	            int cellEnd = row.getLastCellNum();  
	  
	            for(int k=cellStart;k<=cellEnd;k++)  {  
	                HSSFCell cell = row.getCell(k);  
	                if(null==cell) continue;  
	                switch (cell.getCellTypeEnum())  {  
	                    case NUMERIC: // 数字  
	                                    System.out.print(cell.getNumericCellValue());  
	                        break;  
	                    case STRING: // 字符
	                        System.out.print(cell.getStringCellValue());  
	                        break;  
	                    case BOOLEAN: // Boolean  
	                        System.out.println(cell.getBooleanCellValue());  
	                        break;  
	                    case FORMULA: // 公式  
	                        System.out.print(cell.getCellFormula());  
	                        break;  
	                    case BLANK: // 空
	                        System.out.println(" ");  
	                        break;  
	                    case ERROR: // 故障
	                        System.out.println(" ");  
	                        break;  
	                    default:  
	                        System.out.print("未知类型   ");  
	                        break;  
	                }  
	            }  
	            System.out.print("\n");  
	        } 
		}catch(Exception e){
			//TODO
			
		}finally{
			if(null != hssfWorkbook){
				try {
					hssfWorkbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}
}
