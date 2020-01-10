package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CommonFunLibrary.ERP_Functions;
import Utilities.ExcelFileUtil;

public class DataDriven {
WebDriver driver;
@BeforeTest
public void setup()throws Throwable
{
	String launch=ERP_Functions.launchApp("http://webapp.qedge.com/");
	Reporter.log(launch,true);
	String login=ERP_Functions.VerifyLogin("admin", "master");
}
@Test
public void supplierCreation()throws Throwable
{
	//acessing excel util methods
	ExcelFileUtil xl=new ExcelFileUtil();
	//row count
	int rc=xl.rowcount("Supplier");
	int cc=xl.colcount("Supplier");
	Reporter.log("no of rows are::"+rc+"    "+"no of columns are::"+cc,true);
	for(int i=1;i<rc;i++)
	{
		String Sname=xl.getCellData("Supplier", i, 0);
		String Address=xl.getCellData("Supplier", i, 1);
		String City=xl.getCellData("Supplier", i, 2);
		String Country=xl.getCellData("Supplier", i, 3);
		String cperson=xl.getCellData("Supplier", i, 4);
		String pnumber=xl.getCellData("Supplier", i, 5);
		String mail=xl.getCellData("Supplier", i, 6);
		String mnumber=xl.getCellData("Supplier", i, 7);
		String note=xl.getCellData("Supplier", i, 8);
		//call supplier creation method
		String Results=ERP_Functions.VerifySupplier(Sname,Address,City,Country,cperson,pnumber,mail,mnumber,note);
		Reporter.log(Results,true);
		xl.setCellData("Supplier", i, 9, Results);
	}
	
}
@AfterTest
public void teardown()
{
	ERP_Functions.verifyLogout();
}












}
