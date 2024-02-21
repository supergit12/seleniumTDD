package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class GooglePage
{
	//Properties for locating elements
	private RemoteWebDriver driver;
	private FluentWait<RemoteWebDriver> wait;
	@FindBy(name="q") private WebElement search;
	
	//Constructor method for connecting to runner classes
	public GooglePage(RemoteWebDriver driver, FluentWait<RemoteWebDriver> wait)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
		this.wait=wait;
	}
	
	//Operational methods to operate and observe elements
	public void fill(String word)
	{
		wait.until(ExpectedConditions.visibilityOf(search)).sendKeys(word,Keys.ENTER);
	}
}



