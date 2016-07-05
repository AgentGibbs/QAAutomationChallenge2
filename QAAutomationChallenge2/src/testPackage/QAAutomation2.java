package testPackage;

import java.util.*;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.asserts.*;
import org.testng.internal.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class QAAutomation2 {

	public WebDriver browserDriver;

	private String baseUrl;
	private List<WebElement> topNavigationMenuItems;
	private List<WebElement> navigationSubMenuItems;
	private String topMenuClassName;
	private String subMenuClassName;
	
	
	@BeforeTest
	public void setUp(){
		baseUrl= "https://www.skiutah.com";
		topMenuClassName = "SuperfishMegaMenu-topLink";
		subMenuClassName = "SuperfishMegaMenu-subLink";
		Reporter reporter = new Reporter();
		
	}
	
	
	public void getMainMenu()
	{
	    browserDriver.navigate().to(baseUrl);
	    topNavigationMenuItems = browserDriver.findElements(By.className(topMenuClassName));
	     
	}
	
	public List<WebElement> getSubMenu(WebElement topMenuItem)
	{
		return topMenuItem.findElements(By.className(subMenuClassName));		
	}
	

	private TestResult verifyElement(WebElement menuItem)
	{
		TestResult result = new TestResult();
		result.setStatus(TestResult.STARTED);
				
		String navTarget = "";		
		
		String logMessage = "Verifying Menu Item: " + menuItem.getText() + System.lineSeparator();
		
		navTarget = getHref(menuItem);
		
		if(navTarget == "")
		{	
			String errMessage = "No href link found for menu item " + menuItem.getText() + ": Skipping Test.";
			logMessage  = logMessage + errMessage + System.lineSeparator();
			result.setStatus(TestResult.SKIP);
		}
		
		else
		{
			logMessage = logMessage+ "Expected web page: "+ navTarget + System.lineSeparator();
			menuItem.click();
			String cUrl = browserDriver.getCurrentUrl();
			logMessage = logMessage + "Actual web page: "+ cUrl+ System.lineSeparator();
			if(cUrl==navTarget){
				result.setStatus(TestResult.SUCCESS);
				logMessage = logMessage + "SUCCESS";
			}
			else {result.setStatus(TestResult.FAILURE);}
			logMessage = logMessage + "FAIL";
		}
		
		Reporter.log(logMessage);
		System.out.println(logMessage);
		return result;
	}	

	
	
	private String getHref(WebElement link)
	{
		String returnValue = "";
		if(link.getAttribute("href")!= null)
		{
			returnValue = link.getAttribute("href");
		}
		
		
		return returnValue;
	}
	
	
	@Test
	public void verifyChrome()
	{		
		boolean allTestPass = true;
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Christian Gibbs\\Java Libraries\\selenium-2.53.0\\chromedriver.exe");
	    browserDriver = new ChromeDriver();
	    getMainMenu();
	    Reporter.log("Launching ChromDriver tests");
	    
	    
	    for(int i = 0; i<topNavigationMenuItems.size(); i++)
	    {
	    	WebElement menuItemToTest = topNavigationMenuItems.get(i);
	    	TestResult navigatedSuccessfully = verifyElement(menuItemToTest);
	    	if (navigatedSuccessfully.getStatus() == TestResult.FAILURE)
	    			{
	    			allTestPass = false;
	    			}
	    	
	    	getMainMenu();
	    }
	    assert(allTestPass == true);
	    
	    browserDriver.close();
	   
	}

}
