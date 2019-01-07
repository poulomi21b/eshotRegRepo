package com.eshot.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase 
{
	public static WebDriver driver;
	public static Properties prop;
	public static ChromeOptions options;
	
	public TestBase() throws IOException
		{
		
		        try
		        {
		        	prop = new Properties();
		        	FileInputStream ip = new FileInputStream("C:\\Selenium\\e-shotTestAutomation\\src\\main\\java\\com\\eshot\\qa\\config\\config.properties");
		        	prop.load(ip);
		        }
			    catch(FileNotFoundException e)
		        {
			    	e.printStackTrace();
		        }
		        catch(IOException e)
		        {
		        	e.printStackTrace();
		        }
		}
	 
	
	public static void initialization()
	{
		String browsername = prop.getProperty("browser");
		
		if(browsername.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",prop.getProperty("chromefilepath"));
			//System.setProperty("webdriver.chrome.driver","C:\\Users\\Poulomi\\Desktop\\Selenium\\chromedriver.exe");
			options = new ChromeOptions();
			options.addArguments("disable-infobars");
			
			//setting the path for downloading Sign-Up forms 
			String downloadFilepath = "C:\\Users\\Poulomi\\Desktop\\Selenium\\Signupform";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		    chromePrefs.put("profile.default_content_settings.popups", 0);
		    chromePrefs.put("download.default_directory", downloadFilepath);
		       
		    HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		    options.setExperimentalOption("prefs", chromePrefs);
		    options.addArguments("--test-type");
		    options.addArguments("--disable-extensions"); //to disable browser extension pop up
		  
		    DesiredCapabilities cap = DesiredCapabilities.chrome();
		    cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		    cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		    cap.setCapability(ChromeOptions.CAPABILITY, options);
		    
		    driver = new ChromeDriver(options);
		}
		else if(browsername.equals("FF")){
			System.setProperty("webdriver.gecko.driver", "/Users/naveenkhunteta/Documents/SeleniumServer/geckodriver");	
			driver = new FirefoxDriver(); 
		{
			
		}
	}
	driver.get(prop.getProperty("env"));
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	}
	
}
