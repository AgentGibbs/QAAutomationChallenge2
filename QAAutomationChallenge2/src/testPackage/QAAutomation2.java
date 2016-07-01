package testPackage;

import java.util.*;
import org.testng.*;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
	
	public boolean verifyElement(WebElement menuItem)
	{
		boolean result= false;
		boolean aTestFailed = false;
		String navTarget;
		
		try{
			navTarget = menuItem.getAttribute("href");
			System.out.println(navTarget);
		}
		
		catch(Error E){
			String errmsg = "No href link exists for menu item " + menuItem.getAttribute("title");
			result = false;
			throw new TestException(errmsg, new java.lang.Throwable());
		}
		
		menuItem.click();
		String cUrl = browserDriver.getCurrentUrl();
		System.out.println(cUrl);
		System.out.println(navTarget);
		if(cUrl.contains(navTarget)){
			
			result=true;
		}
		
		return result;
	}	
	
	
	@Test
	public void verifyChrome()
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Christian Gibbs\\Java Libraries\\selenium-2.53.0\\chromedriver.exe");
	    browserDriver = new ChromeDriver();
	    getMainMenu();
	    
	    for(int i = 0; i<topNavigationMenuItems.size(); i++)
	    {
	    	//if(browserDriver.getCurrentUrl() != baseUrl)
	    	{
	    		System.out.println("Actual URL: " + browserDriver.getCurrentUrl());
	    	}//end if
	    		    	
	    	System.out.println("Before click: " + browserDriver.getCurrentUrl());
	    	WebElement menuItemToTest = topNavigationMenuItems.get(5);
	    	Boolean navigatedSuccessfully = verifyElement(menuItemToTest);
	    	System.out.println(navigatedSuccessfully.booleanValue());
	    	//Assert.assertEquals(navigatedSuccessfully, true );
	    	System.out.println("After click: " + browserDriver.getCurrentUrl());
	    	/*navigationSubMenuItems = getSubMenu(menuItemToTest);
	    	for(int nestedI = 0; nestedI < navigationSubMenuItems.size(); nestedI ++)
	    	{
	    		if(browserDriver.getCurrentUrl()!= baseUrl)
	    		{
	    			browserDriver.get(baseUrl);	    	
	    		}//end if
	    	
	    		boolean navigatedSubMenu = verifyElement(navigationSubMenuItems.get(nestedI));
	    		Assert.assertEquals(navigatedSubMenu, true ); 
		    }//end nested for loop
	    	*/
	    }
//	    browserDriver.close();
	   
	}

}
