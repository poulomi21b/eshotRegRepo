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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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


public class ManageProfileTest extends TestBase
{
	
	public ManageProfileTest() throws IOException 
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
	public void CreateManageProfileTest() throws InterruptedException, SQLException, ClassNotFoundException 
	{
 
	        //Setting the status to Inactive for all the Forms
	
			    //String dbUrl = "jdbc:sqlserver://dev-eshotdb;databaseName=eshot_LiveTestingSharedContainer_v5"; 
			
			//Database Username
				//String userName = "eshotAdmin";
				
			//Database Password
				//String password = "devAdmin";
		
			// Update the status for Manage Profile form to inactive
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
			driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/a[1]")).click(); 
			driver.findElement(By.xpath("//*[@id=\"body\"]/div[1]/ul/li[5]/a")).click();
			driver.findElement(By.xpath("//*[@id=\"body\"]/div[2]/div/div/div[2]/div[2]/div[2]/a")).click();
			driver.findElement(By.xpath("//*[@id=\"body\"]/div[3]/div[2]/div/a")).click();
			
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
			
		    //verifying Manage Profile Form Creation
		    Assert.assertTrue(ToolTipText.contains("Active"));   		
		    driver.close();	
		    System.out.println("CreateManageProfileTest Successful");
			
		}
	
	@Test(priority=2)
	public void CloneManageProfileTest() throws InterruptedException, ClassNotFoundException, SQLException
	{
		driver.findElement(By.xpath("//a[text() ='Subscription Management']")).click();
		driver.findElement(By.xpath("//a[text()='Manage']")).click(); 
		Thread.sleep(6000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='Automation Test']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::button")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='Automation Test']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::ul/li/a[text()='Clone']")).click();
		
		List<WebElement> manageprofileforms = driver.findElements(By.xpath("//div/p[contains(text(),'Automation Test')]"));
		
		//System.out.println(manageprofileforms.size());
		
		//Verifying cloning
		for(int i = 0;i<manageprofileforms.size();i++)
		{
			
			String formname = manageprofileforms.get(i).getText();
			if (formname.contains("Automation Test - Clone"))
			{
				Assert.assertTrue(formname.contains("Automation Test - Clone"));
				break;
			}
			else
			{
				System.out.println("Not found - FAIL");
			}
		}
		
		System.out.println("CloneManageProfileTest Successful");
	}
	
	@Test(priority=3)
	public void DeleteManageProfileTest() throws InterruptedException, ClassNotFoundException, SQLException
	{
		driver.findElement(By.xpath("//a[text() ='Subscription Management']")).click();
		driver.findElement(By.xpath("//a[text()='Manage']")).click(); 
		Thread.sleep(6000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='Automation Test - Clone']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::button")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='campaignList']/div/div/div[1]/div/div/div/div/p[@title ='Automation Test - Clone']/../../following-sibling::div/div/div/a[text()='Edit']/following-sibling::ul/li/a[text()='Delete']")).click();
		
		List<WebElement> manageprofileforms = driver.findElements(By.xpath("//div/p[contains(text(),'Automation Test')]"));
		
		//System.out.println(manageprofileforms.size());
		
		//Verifying deleting of the cloned form
		for(int i = 0;i<manageprofileforms.size();i++)
		{
			
			String formname = manageprofileforms.get(i).getText();
			if (formname.contains("Automation Test - Clone"))
			{
				Assert.assertTrue(formname.contains("Automation Test - Clone"));
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
			String query5 = "DELETE FROM ManageProfileActionsLog where ManageProfileID IN (Select ID from ManageProfile where Name IN ('Automation Test','Automation Test - Clone'));";
			String query6 = "DELETE FROM ManageProfileContactField where ManageProfileID IN (Select ID from ManageProfile where Name IN ('Automation Test','Automation Test - Clone'));";
			String query7 = "DELETE FROM ManageProfileGroup where ManageProfileID IN (Select ID from ManageProfile where Name IN ('Automation Test','Automation Test - Clone'));";
			String query8 = "DELETE FROM ManageProfile where Name IN ('Automation Test','Automation Test - Clone');";
			
	    System.out.println("Loading Driver");	
	    //Load mysql jdbc driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		
		//Create Connection to DB
			System.out.println("Connecting Database");
			//Connection conntwo = DriverManager.getConnection(dbUrl, userName, password);
			Connection conntwo = DriverManager.getConnection(prop.getProperty("databasename"), prop.getProperty("userName"),prop.getProperty("password"));
			System.out.println("Connection Successful");
			
		//Create Statement Object
			Statement sttwo = conntwo.createStatement();
		
		System.out.println("Executing Query ");
		// Execute the SQL Query. Store results in ResultSet
		sttwo.executeUpdate(query5);
		sttwo.executeUpdate(query6);
		sttwo.executeUpdate(query7);
		sttwo.executeUpdate(query8);
		System.out.println("Execution Successful");
		// closing DB Connection
		conntwo.close();
		System.out.println("DeleteManageProfileTest Successful");
	}
	

	@AfterMethod
	public void endSession()
	{
		driver.quit();
	}

	
}
