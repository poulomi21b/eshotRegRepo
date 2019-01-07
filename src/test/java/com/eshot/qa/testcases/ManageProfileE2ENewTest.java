package com.eshot.qa.testcases;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eshot.qa.base.TestBase;
import com.eshot.qa.utils.*;

public class ManageProfileE2ENewTest extends TestBase
{

	TestUtils TestUtils;
	
	public ManageProfileE2ENewTest() throws IOException 
	{
		super();
		
	}

	@BeforeMethod
	public void SetupAndLogin() throws IOException, InterruptedException
	{
		initialization();
		TestUtils = new TestUtils();
		driver.findElement(By.id("txtUName")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("txtPwd")).sendKeys(prop.getProperty("pwd"));
		driver.findElement(By.id("txtEmail")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("lvSubaccounts_ctrl9_Literal1")).click();
		Thread.sleep(1000);
	}
	
	@Test(priority=1)
	@Parameters({"email","mobile","title","fname","lname"})
	public void CreateMessageTest(String email,String mobile,String title, String fname, String lname) throws InterruptedException, SQLException, ClassNotFoundException
	{
		TestUtils.CreateGroup();
		TestUtils.AddContact(email, mobile, title, fname, lname);
		
		driver.findElement(By.xpath("//a[text()='Design Studio']")).click(); 
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='New message']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='select-message-item']//following-sibling::div/div[@class='element text campaign-block well']")).click();
		driver.findElement(By.xpath("//button[@id='btnContinue']")).click();
		driver.findElement(By.xpath("//input[@id='MessageEntity_MessageName']")).sendKeys("AutomationTest");
		driver.findElement(By.xpath("//input[@id='MessageEntity_SubjectLine']")).sendKeys("AutomationTest");
		driver.findElement(By.xpath("//select[@id='drpIdentity']")).click();
		driver.findElement(By.xpath("//select[@id='drpIdentity']//following-sibling::option[text()='UK Fuels Ipswich']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		driver.findElement(By.xpath("//textarea[@id='txtContent']")).sendKeys("Hello,\r\n" + 
				"\r\n" + 
				"This message is for Test Automation.");
		driver.findElement(By.xpath("//a[text()='Save & Continue']")).click();
		Thread.sleep(1000);
        
		WebDriverWait waitforconf =new WebDriverWait(driver, 10);
		waitforconf.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='kendoConfirmationWindow']")));
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@id='kendoConfirmationWindow']/div/p/input[@id='btnSaveAndExit']")).click();
	}
	
	@Test(priority=2)
	public void CreateManageProfileTest() throws InterruptedException, SQLException, ClassNotFoundException 
	{
 
	        //Setting the status to Inactive for all the Forms		
			//Query to Execute
			String query1 = "UPDATE ManageProfile set IsActive = 0 where SubAccountId = 3";
							
		    System.out.println("Loading Driver");	
		    //Load mysql jdbc driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			
			//Create Connection to DB
			System.out.println("Connecting Database");
			//Connection connone = DriverManager.getConnection(dbUrl, userName, password);
			Connection connone = DriverManager.getConnection(prop.getProperty("databasename"), prop.getProperty("userName"),prop.getProperty("password"));
			System.out.println("Connection Successful");
				
			//Create Statement Object
		    Statement stone = connone.createStatement();
			
			System.out.println("Executing Query ");
			// Execute the SQL Query. Store results in ResultSet
			stone.executeUpdate(query1);
				
			System.out.println("Execution Successful");
			// closing DB Connection
			connone.close();
			
			//Create Manage Profile
			
			driver.findElement(By.xpath("//a[text()='Settings']")).click(); 
			driver.findElement(By.xpath("//a[text()='Subscription Management']")).click(); 
			driver.findElement(By.xpath("//a[text()='Manage']")).click();
			driver.findElement(By.xpath("//a[@href='/Settings/ManageProfile/New']")).click();
		
			
			//wait for the pop-up to display
			WebDriverWait wait =new WebDriverWait(driver, 120);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"modal-container\"]/div")));
		    driver.findElement(By.xpath("//*[@id=\"ManageProfileName\"]")).sendKeys("Automation Test");
		    driver.findElement(By.xpath("//*[@id=\"manageProfile-create\"]/div[2]/button[1]")).click();
		    Thread.sleep(2000);
		    driver.findElement(By.xpath("//*[@id=\"manageProfileOptions\"]/div[2]/div[1]/div/span/span[1]/span/ul")).click();
		    
		    WebElement group= driver.findElement(By.id("select2-groups-results"));
			List<WebElement> groupList=group.findElements(By.tagName("li"));
			
			
			for (WebElement li : groupList)
					
			{
			if (li.getText().equals("!va"))
				
			   {
			     li.click();
			     driver.findElement(By.id("btnContinueBottomRight")).click();
			     break;
			   }
			
			}
			Thread.sleep(2000);
			//System.out.println("After first step");
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
			//System.out.println("After second step");
			driver.findElement(By.xpath("//*[@id=\"btnContinue2\"]")).click();
			//System.out.println("After third step");
			Thread.sleep(4000);
	        driver.findElement(By.xpath("//button[contains(text(),'Finish')]")).click(); 
			
			Thread.sleep(2000);
		    //wait for the Manage Profile List page to load
		    driver.findElement(By.xpath("//*[@id=\"campaignList\"]/div/div/div[1]/div/div/div[8]/a/span")).click();
		    Thread.sleep(2000);
		    
		    //Reading tooltip for the active Form
		    WebElement googleLogo = driver.findElement(By.xpath("//*[@id=\"campaignList\"]/div/div/div[1]/div/div/div[8]/a"));
		    String ToolTipText = googleLogo.getAttribute("title");
				    
		    Assert.assertTrue(ToolTipText.contains("Active"));   		
		    driver.close();	
		    System.out.println("Create Manage profile Form Successful");
			
		}
	
	@Test(priority=3)
	public void CreateCampaign() throws InterruptedException, SQLException, ClassNotFoundException
	{
		driver.findElement(By.xpath("//a[@id='ctl00_ucMasterMenu_lnkCampaigns']")).click(); 
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()=\"New campaign\"]")).click(); 
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id=\"Name\"]")).sendKeys("AutomationTest14");
		driver.findElement(By.xpath("//a[text()=\"Email\"]")).click();
		
		driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
		driver.findElement(By.xpath("//div/p/span[text()='AutomationTest']/../../following-sibling::div/a[text()='Use this message']")).click();
		driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
		
		driver.findElement(By.xpath("//div[@id='selectedGroups']/ul/li/div/span/following-sibling::span[text()= '" + prop.getProperty("groupname")+ "']/../span[@class='k-checkbox']")).click();
		
		//define Delivery
		driver.findElement(By.xpath("//button[@id='btnContinueTopLeft']")).click();
		Thread.sleep(1000);
		
		//Filtering
		driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
		
		//Summary
		driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
		Thread.sleep(1000);
		
		//Test
		driver.findElement(By.xpath("//button[@id='btnFinishBottomRight']")).click();
		
		//Send
		driver.findElement(By.xpath("//div[@class='gridrow campaign-singlesend ']//div/div/div/div/p[text()='AutomationTest14']/../../following-sibling::div/div/div/a[text()='Send']")).click();
		Thread.sleep(1000);
		WebDriverWait waitforconf =new WebDriverWait(driver, 10);
		waitforconf.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sendModal']")));
		
		driver.findElement(By.xpath("//input[@id='btn-single-send-continue']")).click();
		Thread.sleep(1000);
		Thread.sleep(1000);
		WebDriverWait waitforconf1 =new WebDriverWait(driver, 10);
		waitforconf1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sendModal']")));
		
		driver.findElement(By.xpath("//button[@id='btn-single-send-confirm']")).click();
	}
	
	@Test(priority=4)
	public void CheckMail() throws InterruptedException, SQLException, ClassNotFoundException
	{
		
		
		//Ckeck Email
		Thread.sleep(10000);
		driver.get("https://outlook.office.com/owa/");
		driver.findElement(By.xpath("//*[@id=\"i0116\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"i0116\"]")).sendKeys("poulomi.biswas@forfront.com");
		driver.findElement(By.xpath("//*[@id=\"idSIButton9\"]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"i0118\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"i0118\"]")).sendKeys("Printer01");
		driver.findElement(By.xpath("//*[@id=\"idSIButton9\"]")).click();
				
		driver.findElement(By.xpath("//*[@id=\"idBtn_Back\"]")).click();
		Thread.sleep(9000);
		driver.findElement(By.xpath("//span[@title='Inbox']")).click();
		
		Thread.sleep(9000);
		
		//Double click the email in the Inbox
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//span[text()='AutomationTest']"))).doubleClick().build().perform();
		Thread.sleep(3000);
	    
		//Switch to the Pop Up window
		String parenthandle = driver.getWindowHandle();
		System.out.println(parenthandle);
		
		for (String winhandle : driver.getWindowHandles())
		{
			//System.out.println(winhandle);
			if(!winhandle.equalsIgnoreCase(parenthandle))
			{
				driver.switchTo().window(winhandle);
			}
		}
				
		Thread.sleep(2000);
		String oldTab = driver.getWindowHandle();
		System.out.println(oldTab);
		
		//Click Manage profile
		 Thread.sleep(8000);
		 //driver.findElement(By.xpath("//a[contains(@href,'https://securevesc2.e-shot.local/NLI/ManageProfile.aspx')]")).click(); 
		 driver.findElement(By.xpath("//a[contains(@href,'https://securevesc')]")).click();
		 Thread.sleep(2000);
		
		 //Switch to New Form
		 ArrayList<String> newwindow = new ArrayList<String>(driver.getWindowHandles());
		 System.out.println(newwindow.size());
		 System.out.println(newwindow.get(0));
		 driver.switchTo().window(newwindow.get(2));
		 Thread.sleep(2000);
		 
		 driver.findElement(By.xpath("//div/input[@id='esFirstname_11']")).click();
		 driver.findElement(By.xpath("//div/input[@id='esFirstname_11']")).clear();
		 driver.findElement(By.xpath("//div/input[@id='esFirstname_11']")).sendKeys("Updated");
		 driver.findElement(By.xpath("//button[text()='Save']")).click();
		 Thread.sleep(3000);
		 
		 
         //Switch to confirmation screen
		 ArrayList<String> confwindow = new ArrayList<String>(driver.getWindowHandles());
		 System.out.println(confwindow.size());
		 System.out.println(confwindow.get(0));
		 //newTab.remove(oldTab);
		 driver.switchTo().window(confwindow.get(2));
		 Thread.sleep(3000);
 
		//Delete created campaign
		 TestUtils.DeleteCampaigns();
		 
		//Delete created Message
		 TestUtils.DeleteMessage();
		 
		//Delete created Message
		 TestUtils.DeleteGroup();
	}
	
	@AfterMethod
	public void endSession()
	{
		driver.quit();
	}
	
}
