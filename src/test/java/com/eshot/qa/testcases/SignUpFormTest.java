package com.eshot.qa.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.eshot.qa.base.TestBase;
import com.eshot.qa.utils.TestUtils;


public class SignUpFormTest extends TestBase
{
	
	public SignUpFormTest() throws IOException 
	{
		super();
	}
	
	@BeforeMethod
	public void SetupAndLogin() throws IOException
	{
		initialization();
		driver.findElement(By.id("txtUName")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("txtPwd")).sendKeys(prop.getProperty("pwd"));
		driver.findElement(By.id("txtEmail")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("lvSubaccounts_ctrl9_Literal1")).click();
		driver.findElement(By.id("ctl00_lnkSettings")).click();
	}
		
	@Test(priority=1)
	public void CreateSingleSignupForm() throws InterruptedException, IOException
	{
		//TestUtils TestUtils = new TestUtils();
		//TestUtils.CreateGroup();
				

		//Single Opt-In Form
		driver.findElement(By.xpath("//*[@id=\"body\"]/div[1]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"btnMmanageDoubleOptIn\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"body\"]/div[3]/div[2]/div/a[1]")).click();
		
		
		//wait for the pop-up to display
		WebDriverWait wait =new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"modal-container\"]")));
		Thread.sleep(2000);
		
		//Enter Form name
		driver.findElement(By.xpath("//*[@id=\"SignUpFormType\"][@value=\"Single\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"Name\"]")).sendKeys("SingleOpt");
		driver.findElement(By.xpath("//*[@id=\"signUpForm-new\"]/div[2]/button[1]")).click();
		
		//Step-1 -- Sign-up Form
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"signupFormOptions\"]/div[1]/div/label[4]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"signupFormOptions\"]/div[1]/div/label[5]/input")).click();
		
		//Add visible group
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"signupFormOptions\"]/div[2]/div/div[1]/span/span[1]/span/ul")).click();
		Thread.sleep(2000);
		WebElement group= driver.findElement(By.xpath("//*[@id=\"select2-visibleGroups-results\"]"));
		List<WebElement> groupList=group.findElements(By.tagName("li"));
		
					for (WebElement li : groupList)
							
					{
					if (li.getText().equals("!va"))
						
					   {
					     li.click();
					     break;
					   }
					
					}
					
		driver.findElement(By.id("IncludeConsentStatement")).click();
		driver.findElement(By.xpath("//*[@id=\"ConsentStatementDiv\"]/div/div")).sendKeys("I Agree");
		driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
		
		//Step-2 -- Logo
		driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
		
		
		//Step-3 -- Confirmation Page
		Select fruits = new Select(driver.findElement(By.id("ConfirmationPageAction")));
		fruits.selectByVisibleText("Show a thank you message");
		driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
		 
		//Step-4 -- Signup code
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"body\"]/form/div[2]/div/div/div/div[1]/a")).click(); 
		
		Thread.sleep(2000);
		String filePath = "C:/Users/Poulomi/Desktop/Selenium/Signupform/SignupFormCode.html";
		driver.get(filePath);
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"esEmail_0\"]")).sendKeys("user70001.poulomitesting@forfront.net");
		driver.findElement(By.xpath("//*[@id=\"esFirstname_11\"]")).sendKeys("user7000");
		driver.findElement(By.xpath("//*[@id=\"esGrp_349\"]")).click(); 
		driver.findElement(By.xpath("//*[@id=\"esSignupConsent\"]")).click();
		Thread.sleep(6000);
		driver.findElement(By.xpath("//input[@name='submit']")).click();
		
		//Verifying Form creation
		Thread.sleep(6000);
		WebElement confM = driver.findElement(By.xpath("//*[@id=\"body\"]/div/div/div[2]/h1")); 
	    String Message = confM.getText();
	    Assert.assertTrue(Message.contains("Thank you for registering"));
	    driver.close();
	    System.out.println("CreateSingleSignupForm Successful");
	    
	}
	
	@Test(priority=2)
	public void CloneSingleSignupForm() throws InterruptedException
	{
		driver.findElement(By.xpath("//a[text() ='Subscription Management']")).click();
		driver.findElement(By.xpath("//*[@id=\"btnMmanageDoubleOptIn\"]")).click();
		Thread.sleep(6000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='SingleOpt']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::button")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='SingleOpt']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::ul/li/a[text()='Clone']")).click();
		
		List<WebElement> singlesignupforms = driver.findElements(By.xpath("//div/p[contains(text(),'Single')]"));
		
		//System.out.println(singlesignupforms.size());
		
		for(int i = 0;i<singlesignupforms.size();i++)
		{
			
			String formname = singlesignupforms.get(i).getText();
			System.out.println(formname);
			if (formname.contains("SingleOpt - Clone"))
			{
				Assert.assertTrue(formname.contains("SingleOpt - Clone"));
				break;
			}
			else
			{
				System.out.println("Not found - FAIL");
			}
		}
		System.out.println("Cloning Single SignUp Successful");
	}
	
	
	
	@Test(priority=3)
	public void CreateDoubleSignupForm() throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
				
		driver.findElement(By.xpath("//*[@id=\"body\"]/div[1]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"btnMmanageDoubleOptIn\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"body\"]/div[3]/div[2]/div/a[1]")).click();
		
	  
	  //wait for the pop-up to display
	  WebDriverWait wait1 =new WebDriverWait(driver, 120);
	  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"modal-container\"]")));
	  Thread.sleep(2000);
			
	  
	  //Enter Form name
	   driver.findElement(By.xpath("//*[@id=\"Name\"]")).sendKeys("DoubleOpt");
	   driver.findElement(By.xpath("//*[@id=\"signUpForm-new\"]/div[2]/button[1]")).click();
	
	        //Step-1
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id=\"signupFormOptions\"]/div[1]/div/label[4]/input")).click(); 
			driver.findElement(By.xpath("//*[@id=\"signupFormOptions\"]/div[1]/div/label[5]/input")).click();
			
			//Add visible group
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id=\"signupFormOptions\"]/div[2]/div/div[1]/span/span[1]/span/ul")).click();
			Thread.sleep(2000);
			WebElement group1= driver.findElement(By.xpath("//*[@id=\"select2-visibleGroups-results\"]"));
			List<WebElement> groupList1=group1.findElements(By.tagName("li"));
			
						for (WebElement li : groupList1)
								
						{
						if (li.getText().equals("!va"))
							
						   {
						     li.click();
						     break;
						   }
						
						}
						
			driver.findElement(By.id("IncludeConsentStatement")).click();
			//driver.findElement(By.xpath("//*[@id=\"ConsentStatementDiv\"]/div/div")).sendKeys("I Agree");
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
			
			//Step-2
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
			
			
			//Step-3 -- Confirmation Page
			Select option1 = new Select(driver.findElement(By.id("ConfirmationPageAction")));
			Thread.sleep(2000);
			option1.selectByVisibleText("Show a thank you message");
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click(); 
			 
            //Step-4 -- Opt-in Email
			Select option2 = new Select(driver.findElement(By.id("identityDropDown")));
			option2.selectByVisibleText("UK Fuels Ipswich");
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
			
			
            //Step-5 -- Final Confirmation
			Select option3 = new Select(driver.findElement(By.id("FinalConfirmationAction")));
			option3.selectByVisibleText("Show a thank you message");
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
			
            //Step-6 -- Welcome Email
			driver.findElement(By.xpath("//*[@id=\"WelcomeEmailEnabled\"]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id=\"btnContinue\"]")).click(); 
			
			//Step-7 --- Sign up code
			driver.findElement(By.xpath("//*[@id=\"body\"]/form/div[2]/div/div/div/div[1]/a")).click(); 
			
			Thread.sleep(2000);
			String filePath1 = "C:\\Users\\Poulomi\\Desktop\\Selenium\\Signupform\\SignupFormCode.html";
			driver.get(filePath1);
			
			driver.findElement(By.xpath("//*[@id=\"esEmail_0\"]")).sendKeys("user21212129.poulomitesting@forfront.net");
			driver.findElement(By.xpath("//*[@id=\"esFirstname_11\"]")).sendKeys("user7000");
			driver.findElement(By.xpath("//*[@id=\"esGrp_349\"]")).click(); 
			driver.findElement(By.xpath("//*[@id=\"esSignupConsent\"]")).click();
			driver.findElement(By.xpath("//input[@name='submit']")).click();
			
			// Verifying form creation			
			Thread.sleep(2000);
			WebElement confM1 = driver.findElement(By.xpath("//*[@id=\"body\"]/div/div/div[2]/h1")); 
		    String Message1 = confM1.getText();
		    Assert.assertTrue(Message1.contains("Thank you for registering"));
		    System.out.println("CloneSingleSignupForm Successful");
			   
			    
}
	@Test(priority=4)
	public void CloneDoubleSignupForm() throws InterruptedException, ClassNotFoundException, SQLException
	{
		driver.findElement(By.xpath("//a[text() ='Subscription Management']")).click();
		driver.findElement(By.xpath("//*[@id=\"btnMmanageDoubleOptIn\"]")).click();
		Thread.sleep(6000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='DoubleOpt']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::button")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='DoubleOpt']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::ul/li/a[text()='Clone']")).click();
		
		List<WebElement> singlesignupforms = driver.findElements(By.xpath("//div/p[contains(text(),'Double')]"));
		
		System.out.println(singlesignupforms.size());
		
		for(int i = 0;i<singlesignupforms.size();i++)
		{
			
			String formname = singlesignupforms.get(i).getText();
			System.out.println(formname);
			if (formname.contains("DoubleOpt - Clone"))
			{
				Assert.assertTrue(formname.contains("DoubleOpt - Clone"));
				break;
			}
			else
			{
				System.out.println("Not found - FAIL");
			}
		}
		
		System.out.println("cleaning Test data from database");
		//Connection URL Syntax: "jdbc:sqlserver://ipAddress:portNumber/dbName"
		
		System.out.println("Starting to Delete");
		//Query to Execute
			String query1 = "DELETE FROM SignUpFormCustomField where SignUpFormID IN (select ID from SignUpForm where Name In ('SingleOpt','DoubleOpt','SingleOpt - Clone'));";
			String query2 = "DELETE FROM SignUpFormGroup where SignUpFormID IN (select ID from SignUpForm where Name In ('SingleOpt','DoubleOpt','SingleOpt - Clone'));";
			String query3 = "DELETE FROM SignUpFormEndUserData where SignUpFormID IN (select ID from SignUpForm where Name In ('SingleOpt','DoubleOpt','SingleOpt - Clone'));";
			String query4 = "DELETE FROM SignUpForm where Name In ('SingleOpt','DoubleOpt','SingleOpt - Clone');";
			
	    System.out.println("Loading Driver");	
	    //Load mysql jdbc driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		
		//Create Connection to DB
			System.out.println("Connecting Database");
		//	Connection conn = DriverManager.getConnection(dbUrl, userName, password);
			Connection conn = DriverManager.getConnection(prop.getProperty("databasename"), prop.getProperty("userName"),prop.getProperty("password"));
			System.out.println("Connection Successful");
			
		//Create Statement Object
			Statement st = conn.createStatement();
		
		System.out.println("Executing Query ");
		// Execute the SQL Query. Store results in ResultSet
			st.executeUpdate(query1);
			st.executeUpdate(query2);
			st.executeUpdate(query3);
			st.executeUpdate(query4);
		System.out.println("Execution Successful");
		// closing DB Connection
			conn.close();
			
		// Deleting the SignUpForms
			System.out.println("Start Deleting file");
			File file = new File("C:\\Users\\Poulomi\\Desktop\\Selenium\\Signupform");
			File[] files = file.listFiles();
		
			for (File f: files)
			{
				System.out.println(f.getName());
				f.delete();
			}
			System.out.println("File Deleted successfully");
			System.out.println("CloneDoubleSignupForm Successful");
	}

	@AfterMethod
	public void endSession()
	{
		driver.quit();
	}

	
}
