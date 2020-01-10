package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
//Constructor for read excel path
public ExcelFileUtil()throws Throwable
{ 
	FileInputStream fi=new FileInputStream("D:\\Selenium_Evening\\ERP_Stock\\TestInput\\InputSheet.xlsx");
	wb=WorkbookFactory.create(fi);
	
		
	}
//count no of rows in a sheet
public int rowcount(String sheetname)
{
return wb.getSheet(sheetname).getLastRowNum();	
}
//count no of columns in a row
public int colcount(String sheetname)
{
	return wb.getSheet(sheetname).getRow(0).getLastCellNum();
	}

//reading data from cell
public String getCellData(String sheetname,int row,int column)
{
	String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{

int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
//convert celldata into string type
data=String.valueOf(celldata);
}
	else
	{
		data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
//write results into status column
public void setCellData(String sheetname,int row,int column,String status)
throws Throwable{
//get sheet from wb
	Sheet ws=wb.getSheet(sheetname);
	//get row from sheet
	Row rownum=ws.getRow(row);
	//get column
	Cell cell=rownum.createCell(column);
	//write status
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("pass")){
		//create a cell style
		CellStyle style=wb.createCellStyle();
		//create a font
		Font font=wb.createFont();
		//apply color to test
		font.setColor(IndexedColors.GREEN.getIndex());
		//apply bold to text
		font.setBold(true);
		//set font
		style.setFont(font);
		//set cell style
		rownum.getCell(column).setCellStyle(style);
		}
	else if(status.equalsIgnoreCase("Fail")){
		//create a cell style
				CellStyle style=wb.createCellStyle();
				//create a font
				Font font=wb.createFont();
				//apply color to test
				font.setColor(IndexedColors.RED.getIndex());
				//apply bold to text
				font.setBold(true);
				//set font
				style.setFont(font);
				//set cell style
				rownum.getCell(column).setCellStyle(style);
	}
	
	else if(status.equalsIgnoreCase("not Executed")){
		//create a cell style
				CellStyle style=wb.createCellStyle();
				//create a font
				Font font=wb.createFont();
				//apply color to test
				font.setColor(IndexedColors.BLUE.getIndex());
				//apply bold to text
				font.setBold(true);
				//set font
				style.setFont(font);
				//set cell style
				rownum.getCell(column).setCellStyle(style);
	
	}
	FileOutputStream fo=new FileOutputStream("D:\\Selenium_Evening\\ERP_Stock\\TestOutput\\hybrid.xlsx");
	wb.write(fo);
	fo.close();
	
}
}

