package tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import atu.testrecorder.ATUTestRecorder;
import pages.GooglePage;
import utilities.ExcelFileUtility;
import utilities.WebSiteUtility;

public class SearchTest2WithExcelFile
{
	public RemoteWebDriver driver;
	public FluentWait<RemoteWebDriver> wait;
	public WebSiteUtility su;
	public ExcelFileUtility eu;
	public GooglePage gp;
	public SoftAssert sa;
	public SimpleDateFormat sf;
	public Date dt;
	public ATUTestRecorder recorder;
	
	@BeforeClass
	public void method1() throws Exception
	{
		//Start recording
		sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		dt=new Date();
		String vp="target\\"+sf.format(dt); //".mov" is default extension
		recorder=new ATUTestRecorder(vp,false); //false means no audio
		recorder.start();
	}
	
	@Test(priority=1)
	public void googleHomePageTitleTest() throws Exception
	{
		sa=new SoftAssert();
		eu=new ExcelFileUtility("src\\test\\resources\\testdatafiles\\testdata.xlsx");
		eu.openSheet("Sheet1");
		int nour=eu.getRowsCount();
		int nouc=eu.getColumnscount(0);
		eu.createResultColumn(nouc);
		//First row(index is 0) is names of columns, so start from 2nd row(index is 1)
		for(int i=1; i<nour;i++)
		{
			String bn=eu.getCellValue(i,0);
			String word=eu.getCellValue(i,1);
			//Create objects to Utility classes(have common methods)
			su=new WebSiteUtility();
			//Open browser
			driver=su.openBrowser(bn);
			wait=su.defineWait(driver);
			//Title testing
			try
			{
				//Define objects to page classes
				gp=new GooglePage(driver,wait);
				//Launch site by using URL in "config.properties" file
				su.launchSite(driver);
				String t=driver.getTitle();
				if(t.equals("Google"))
				{
					eu.setCellValue(i, nouc,"Google home page title test passed");
					Reporter.log("Google home page title test passed<br>");
					sa.assertTrue(true);
				}
				else
				{
					eu.setCellValue(i, nouc,"Google home page title test failed and see screenshot");
					Reporter.log("Google home page title test failed and see:");
					String fp=su.captureScreenshot(driver);
					Reporter.log(
			         "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
					sa.assertTrue(false);
				}
				su.closeSite(driver);
			}
			catch(Exception ex)
			{
				eu.setCellValue(i, nouc,ex.getMessage());
				Reporter.log(ex.getMessage()+"and see:");
				String fp=su.captureScreenshot(driver);
				Reporter.log(
		       "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
				sa.fail(); //equal to sa.assertTrue(false);
				su.closeSite(driver);
			}
		} //loop closing
		//Save and close excel
		eu.saveAndCloseExcel();
		//Collate(collect and check) all assertions
		sa.assertAll();
	}
	
	@Test(priority=2)
	public void googleSearchPageTitleTest() throws Exception
	{
		sa=new SoftAssert();
		eu=new ExcelFileUtility("src\\test\\resources\\testdatafiles\\testdata.xlsx");
		eu.openSheet("Sheet1");
		int nour=eu.getRowsCount();
		int nouc=eu.getColumnscount(0);
		eu.createResultColumn(nouc);
		//First row(index is 0) is names of columns, so start from 2nd row(index is 1)
		for(int i=1; i<nour;i++)
		{
			String bn=eu.getCellValue(i,0);
			String word=eu.getCellValue(i,1);
			//Create objects to Utility classes(have common methods)
			su=new WebSiteUtility();
			//Open browser
			driver=su.openBrowser(bn);
			wait=su.defineWait(driver);
			//Title testing
			try
			{
				//Define objects to page classes
				gp=new GooglePage(driver,wait);
				//Launch site by using URL in "config.properties" file
				su.launchSite(driver);
				gp.fill(word);
				Thread.sleep(5000);
				String t=driver.getTitle();
				if(t.contains(word))
				{
					eu.setCellValue(i, nouc,"Google search page title test passed");
					Reporter.log("Google search page title test passed<br>");
					sa.assertTrue(true);
				}
				else
				{
					eu.setCellValue(i, nouc,"Google search page title test failed and see screenshot");
					Reporter.log("Google search page title test failed and see:");
					String fp=su.captureScreenshot(driver);
					Reporter.log(
			         "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
					sa.assertTrue(false);
				}
				su.closeSite(driver);
			}
			catch(Exception ex)
			{
				eu.setCellValue(i, nouc,ex.getMessage());
				Reporter.log(ex.getMessage()+"and see:");
				String fp=su.captureScreenshot(driver);
				Reporter.log(
		       "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
				sa.fail(); //equal to sa.assertTrue(false);
				su.closeSite(driver);
			}
		} //loop closing
		//Save and close excel
		eu.saveAndCloseExcel();
		//Collate(collect and check) all assertions
		sa.assertAll();
	}
	
	@AfterClass
	public void method2() throws Exception
	{
		//stop recording
		recorder.stop();
	}
}
