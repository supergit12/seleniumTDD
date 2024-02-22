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

import utilities.TextFileUtility;
import utilities.WebSiteUtility;

public class SearchTest1WithTextFile
{
	public WebSiteUtility su;
	public RemoteWebDriver driver;
	public FluentWait<RemoteWebDriver> wait;
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
	public void homePageTileTest() throws Exception
	{
		sa=new SoftAssert();
		int size=TextFileUtility.getCountOfLinesInTextFile(
				         "src\\test\\resources\\testdatafiles\\testdata.txt");
		for(int i=1;i<=size;i++) 
		{
			String pieces[]=TextFileUtility.getValueInTextFile(
					"src\\test\\resources\\testdatafiles\\testdata.txt",i);
			String bn=pieces[0];
			String word=pieces[1];
			//Common code
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
					Reporter.log("Google home page title test passed<br>");
					sa.assertTrue(true);
				}
				else
				{
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
				Reporter.log(ex.getMessage()+"and see:");
				String fp=su.captureScreenshot(driver);
				Reporter.log(
				    "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
				sa.assertTrue(false);
				su.closeSite(driver);
			}
		} //loop closing
		//Collate(collect and check) all assertions
		sa.assertAll();	
	}
	
	@Test(priority=2)
	public void searchPageTileTest() throws Exception
	{
		sa=new SoftAssert();
		int size=TextFileUtility.getCountOfLinesInTextFile(
				         "src\\test\\resources\\testdatafiles\\testdata.txt");
		for(int i=1;i<=size;i++) 
		{
			String pieces[]=TextFileUtility.getValueInTextFile(
					"src\\test\\resources\\testdatafiles\\testdata.txt",i);
			String bn=pieces[0];
			String word=pieces[1];
			//Common code
			//Create objects to Utility classes(have common methods)
			su=new WebSiteUtility();
			//Open browser
			driver=su.openBrowser(bn);
			wait=su.defineWait(driver);
			//Title testing after search a word
			try
			{
				//Define objects to page classes
				gp=new GooglePage(driver,wait);
				//Launch site by using URL in "config.properties" file
				su.launchSite(driver);
				gp.fill(word); //search a word
				Thread.sleep(5000); //wait for next page
				String t=driver.getTitle();
				if(t.contains(word))
				{
					Reporter.log("After search, Google page title test passed<br>");
					sa.assertTrue(true);
				}
				else
				{
					Reporter.log("After search, Google page title test failed and see:");
					String fp=su.captureScreenshot(driver);
					Reporter.log(
			        "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
					sa.assertTrue(false);
				}
				su.closeSite(driver);
			}
			catch(Exception ex)
			{
				Reporter.log(ex.getMessage()+"and see:");
				String fp=su.captureScreenshot(driver);
				Reporter.log(
				     "<a href=\""+fp+"\"><img src=\""+fp+"\" height=\"100\" width=\"100\"/></a><br>");
				sa.assertTrue(false);
				su.closeSite(driver);
			}
		} //loop closing
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
