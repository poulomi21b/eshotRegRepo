package com.eshot.qa.testcases;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eshot.qa.base.TestBase;
import com.eshot.qa.utils.TestUtils;

public class Notifications extends TestBase
{
	TestUtils TestUtils;
    public Notifications() throws IOException
    {
    	super();
    }


@BeforeMethod
public void SetupAndLogin() throws IOException
{
	initialization();
	TestUtils = new TestUtils();
	driver.findElement(By.id("txtUName")).sendKeys(prop.getProperty("username"));
	driver.findElement(By.id("txtPwd")).sendKeys(prop.getProperty("pwd"));
	driver.findElement(By.id("txtEmail")).sendKeys(prop.getProperty("email"));
	driver.findElement(By.id("btnSubmit")).click();
	driver.findElement(By.id("lvSubaccounts_ctrl9_Literal1")).click();
	//driver.findElement(By.xpath("//a[text()='Campaigns']")).click();
}

@Parameters({"email","mobile","title","fname","lname"})
@Test(priority=1)
public void CreateSMSMessageTest(String email,String mobile,String title, String fname, String lname) throws InterruptedException, SQLException, ClassNotFoundException
{
	TestUtils.CreateGroup();
	TestUtils.AddContact(email, mobile, title, fname, lname);
	
	//System.out.println("Done");
	
	driver.findElement(By.xpath("//a[text()='Design Studio']")).click(); 
	Thread.sleep(1000);
	driver.findElement(By.xpath("//a[text()='New message']")).click();
	Thread.sleep(2000);
	
	//driver.findElement(By.xpath("//div[@class='select-message-item']//following-sibling::div/div[@class='element dynamichtml campaign-block well']")).click();
	
	
	driver.findElement(By.xpath("//*[@id=\"createCampaign\"]/div[1]/div/div/div/div[3]/div/div")).click();
	
	
	//driver.findElement(By.xpath("//form[@id='createCampaign']/div/div/div/div/div/div/div[@class='element sms campaign-block well selected']")).click();
	driver.findElement(By.xpath("//button[@id='btnContinue']")).click();
	
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//input[@id='MessageName']")).sendKeys("AutomationTest");
	
	Thread.sleep(2000);
	driver.findElement(By.xpath("//div/button[@value='SaveAndContinue']")).click();
	Thread.sleep(2000);
	
	driver.findElement(By.xpath("//select[@id='Personalisation']")).click();
	Thread.sleep(4000);
	
	driver.findElement(By.xpath("//select/option[text()='Firstname']")).click();
	Thread.sleep(4000);
	
	
	Actions action = new Actions(driver);
	action.sendKeys(Keys.ENTER).build().perform();
	action.sendKeys(Keys.ENTER).build().perform();
	driver.findElement(By.xpath("//textarea[@id='MessageText']")).sendKeys("The UK leaves the EU in March, and the current plan is for a transition period to finish at the end of 2020.Some Tory MPs and Brexit campaigners");
	
	Thread.sleep(4000);
	
	
	
	
	driver.findElement(By.xpath("//button[@id='continueBtn']")).click();
	Thread.sleep(1000);
	driver.findElement(By.xpath("//button[@id='exitBtn']")).click();
	
}

@Test(priority=2)
public void InsufficientSMScreditsNotification() throws ClassNotFoundException, SQLException, InterruptedException
{
	
	//Query to Execute
	//Enable SMS for the sub account
	String query1 = "UPDATE Accountstbl set SmsEnabled = 1 where ID = 3";
	String query2 = "UPDATE Accountstbl SET SmsRemainingCredits = 1 WHERE ID = 3";
	
	//set Remaining credit to 1
					
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
	stone.executeUpdate(query2);
		
	driver.findElement(By.xpath("//a[text()='Campaigns']")).click();
	driver.findElement(By.xpath("//a[text()=\"New campaign\"]")).click();
	driver.findElement(By.xpath("//input[@id=\"Name\"]")).sendKeys("AutomationTest");
	driver.findElement(By.xpath("//a[text()='SMS']")).click();
	driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
	
	driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div/div//following-sibling::div/a[@data-messagename='AutomationTest' and text()='Use this message']")).click();
	Thread.sleep(2000);
	
	Select drpidentity = new Select(driver.findElement(By.xpath("//select[@id='identity']")));
	drpidentity.selectByIndex(1);
	
	//driver.findElement(By.xpath("//select[@id='identity']")).click();
	Thread.sleep(2000);
	
	
	//Actions action = new Actions(driver);
	//action.moveToElement(driver.findElement(By.xpath("//select/option[@value='2']"))).click().build().perform();
	//Thread.sleep(3000);

	driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
	driver.findElement(By.xpath("//div[@id='selectedGroups']/ul/li/div/span/following-sibling::span[text()= '" + prop.getProperty("groupname")+ "']/../span[@class='k-checkbox']")).click();
	driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
	
	driver.findElement(By.xpath("//button[@id='btnContinueBottomRight']")).click();
	driver.findElement(By.xpath("//button[@id='btnFinishBottomRight']")).click();
	Thread.sleep(2000);
	
	//Send
	driver.findElement(By.xpath("//div[@class='gridrow campaign-sms ']//div/div/div/div/p[text()='AutomationTest']/../../following-sibling::div/div/div/a[text()='Send']")).click();
    //Thread.sleep(1000);
	
	System.out.println("Before");
	WebDriverWait waitforconf =new WebDriverWait(driver, 30);
	waitforconf.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sendSmsModal']")));
	System.out.println("After");
	
	

	//
	
	//div[@id='sendSmsModal']//following::input[@id='smsDateTimePicker'].clear()
	//div[@id='sendSmsModal']//following::input[@id='smsDateTimePicker'].sendkeys(Current
			
	driver.findElement(By.xpath("//input[@id='btn-sms-send-continue']")).click();
	Thread.sleep(1000);
	Thread.sleep(1000);
	WebDriverWait waitforconf1 =new WebDriverWait(driver, 10);
	waitforconf1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sendSmsModal']")));
			
	driver.findElement(By.xpath("//button[@id='btn-sms-send-confirm']")).click();
	
	 //Delete created Message
	 //TestUtils.DeleteGroup();
	
	 //Delete created campaign
	 //TestUtils.DeleteCampaigns();
	 
	 //Delete created Message
	 //TestUtils.DeleteMessage();
	 
	 //Query to Execute
	 
	 //set Remaining credit to 1
	  String query3 = "UPDATE Accountstbl SET SmsRemainingCredits = 100 WHERE ID = 3";
	
		
	  System.out.println("Executing Query ");
	 // Execute the SQL Query. Store results in ResultSet
	  stone.executeUpdate(query3);
			
	   System.out.println("Execution Successful");
	 // closing DB Connection
	  connone.close();
	  
//	  public void CreateSMSMessageTest(String email,String mobile,String title, String fname, String lname)
//	  {
//	
//	  }
	 
}







}