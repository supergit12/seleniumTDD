package tests;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.GooglePage;
import utilities.WebSiteUtility;

public class SearchTest5WithXml
{
	public WebSiteUtility su;
	public RemoteWebDriver driver;
	public FluentWait<RemoteWebDriver> wait;
	public GooglePage gp;
	public SoftAssert sa;

	
	@Test(priority=1)
	@Parameters({"browser","word"})
	public void launch(String bn, String word) throws Exception
	{
		sa=new SoftAssert();
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
			//Launch site by using URL in properties file
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
		//Collate(collect and check) all assertions
		sa.assertAll();	
	}
}

