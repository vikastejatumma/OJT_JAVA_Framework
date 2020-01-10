package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
	static WebDriver driver;
//method for launching browser
	public static WebDriver startBrowser()throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium_Evening\\ERP_Stock\\CommonDriver\\chromedriver.exe");
		driver=new ChromeDriver();
	}
	else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
	{
		System.setProperty("webdriver.gecko.driver", "");	
		driver = new FirefoxDriver();
		}
		return driver;
	}
	//launch url
public static void openApplication(WebDriver driver)throws Throwable
{
driver.get(PropertyFileUtil.getValueForKey("Url"));
driver.manage().window().maximize();
System.out.println("Executing openApllication method");
}
//method for wait element
public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String timewait)
{
	WebDriverWait mywait=new WebDriverWait(driver,Integer.parseInt(timewait));
	if(locatortype.equalsIgnoreCase("id"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
	}
	
}
//method  for close browser
public static void closeBrowser(WebDriver driver)
{
	driver.quit();
	}
//type action method

public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata)
{
	if(locatortype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatorvalue)).clear();
		driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).clear();
		driver.findElement(By.name(locatorvalue)).sendKeys(testdata);	
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).clear();
		driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);	
	}
	}
//click action method

public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
		{
	if(locatortype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).click();
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).click();
	}
		}

public static String generateDate()

{
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("YYY_MM_dd_ss");
	return sdf.format(date);
}
//method for capture data
public static void captureData(WebDriver driver, String locatortype, String locatorvalue) throws Throwable
{
	String snumber="";
	if(locatortype.equalsIgnoreCase("id"))
	{
		snumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		snumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	else if(locatortype.equalsIgnoreCase("xpath"))
	{
		snumber=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
	}
	//write supplier number into  note pad
	FileWriter fw=new FileWriter("D:\\Selenium_Evening\\ERP_Stock\\CaptureData\\supplier.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(snumber);
	bw.flush();
	bw.close();
}
public static void tableValidation(WebDriver driver, String testdata)throws Throwable
{
	FileReader fr=new FileReader("D:\\Selenium_Evening\\ERP_Stock\\CaptureData\\supplier.txt");	
	BufferedReader br=new BufferedReader(fr);
	String exp_data = br.readLine();
	//convert column data into integer
	int column=Integer.parseInt(testdata);
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed())
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
	Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(exp_data);
	Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("supp-table")));
	List<WebElement>rows=table.findElements(By.tagName("tr"));
	System.out.println("no of rows are::"+rows.size());
	for(int i=1;i<rows.size();i++)
	{
String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
		Thread.sleep(5000);
		System.out.println(exp_data+"  "+act_data);
		Assert.assertEquals(act_data, exp_data,"snumber is not matching");
		break;
		
}
}
}

	