package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

public class WebSiteUtility
{
	//Operational methods
	public RemoteWebDriver openBrowser(String browsername)
	{
		RemoteWebDriver driver;
		if(browsername.equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();  
		}
		else if(browsername.equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else if(browsername.equalsIgnoreCase("edge"))
		{
			driver=new EdgeDriver();
		}
		else
		{
			//Set IE browser zoom level to 100% manually
			//System.setProperty("webdriver.ie.driver","path of iedriverserver.exe software");
			driver=new InternetExplorerDriver();	
		}
		return(driver);
	}
	
	public FluentWait<RemoteWebDriver> defineWait(RemoteWebDriver driver) throws Exception
	{
		String temp1=PropertiesFileUtility.getValueInPropertiesFile("maxwait");
		int value1=Integer.parseInt(temp1);
		String temp2=PropertiesFileUtility.getValueInPropertiesFile("interval");
		int value2=Integer.parseInt(temp2);
		FluentWait<RemoteWebDriver> wait=new FluentWait<RemoteWebDriver>(driver);
		wait.withTimeout(Duration.ofSeconds(value1));
		wait.pollingEvery(Duration.ofMillis(value2));
		return(wait);
	}
	
	public void launchSite(RemoteWebDriver driver) throws Exception
	{
		String temp=PropertiesFileUtility.getValueInPropertiesFile("url");
		driver.get(temp);
		driver.manage().window().maximize();
	}
	
	public String captureScreenshot(RemoteWebDriver driver) throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(dt)+".png";
		File dest=new File(fn); //create a new file in HDD
		File src=driver.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src,dest);
		return(dest.getAbsolutePath());
	}
	
	public String fullPageScreenshot(RemoteWebDriver driver) throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(dt)+".png";
		File dest=new File(fn); //create a new file in HDD
		AShot as=new AShot();
	    ShootingStrategy shs=ShootingStrategies.viewportPasting(1000);
	    Screenshot ss=as.shootingStrategy(shs).takeScreenshot(driver);
	    ImageIO.write(ss.getImage(),"PNG",dest); 
	    return(dest.getAbsolutePath());
	}
	
	public void closeSite(RemoteWebDriver driver)
	{
		driver.quit();
	}
}











