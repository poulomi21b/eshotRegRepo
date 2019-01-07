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


public class AddDeleteUserTest extends TestBase
{
	
	public AddDeleteUserTest() throws IOException 
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
	@Parameters({"newuser"})
	public void AddNewUserTest(String newuser) throws InterruptedException
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"btnAddUser\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_FirstName\"]")).sendKeys("AutomationTestUserF");
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_LastName\"]")).sendKeys("AutomationTestUserL");
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_Email\"]")).sendKeys(newuser);
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_UserName\"]")).sendKeys("AutomationTestUser");
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_Password\"]")).sendKeys("AutomationTestUser");
		driver.findElement(By.xpath("//*[@id=\"ManageContacts\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"addEditUserForm\"]/div/div[3]/div/div[2]/div/button")).click();
		
		//Verifying user creation
		WebElement user = driver.findElement(By.xpath("//div/table/tbody/tr/td[text()='userAT70600.poulomitesting@forfront.net']"));
		String actualuser = user.getText();
		
		Assert.assertEquals(actualuser, newuser ,"User created successfully");
		driver.close();
		System.out.println("Add User Successful");
	}
	
	@Test(priority=2)
	@Parameters({"newuser"})
	public void CreateExistingUserTest(String newuser) throws InterruptedException
	{	
		//change Sub account
		driver.findElement(By.xpath("//a[@id='btnSubAccountTopNavigation']")).click();
		//select Sub account
		driver.findElement(By.xpath("//a[@id='btnSubAccountTopNavigation']/following::ul/li/a[text()='SmartDiesel']")).click();
		
		driver.findElement(By.xpath("//a[@id='ctl00_lnkSettings']")).click();
		driver.findElement(By.xpath("//a[@id='btnAddUser']")).click();
		driver.findElement(By.xpath("//input[@value='Existing']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//select[@id='AddEditUserDetailViewModel_SelectedExistingSubaccountUserId']")).click();
		driver.findElement(By.xpath("//select[@id='AddEditUserDetailViewModel_SelectedExistingSubaccountUserId']/option[starts-with(text(),'AutomationTestUser')]")).click();
		driver.findElement(By.xpath("//button[text()='Save']")).click();
		
		Thread.sleep(2000);
		//Verifying user creation
		WebElement user = driver.findElement(By.xpath("//div/table/tbody/tr/td[text()='userAT70600.poulomitesting@forfront.net']"));
		String actualUser = user.getText();
		Assert.assertEquals(actualUser, newuser ,"User created successfully");
		
		driver.close();
		System.out.println("CreateExistingUserTest Successful");
	}
	
	@Test(priority=3)
	@Parameters({"newuser"})
	public void DeleteUserTest(String newuser) throws InterruptedException
	{	
		//Delete User from current sub account
		driver.findElement(By.xpath("//td[contains(text(),'" + newuser + "')]/following-sibling::td/a/img[@title='Delete']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div/button[@type='submit']")).click();
		
		//Verifying user deletion
		Thread.sleep(1000);
		WebElement usertable = driver.findElement(By.xpath("//div/table[@class=\"grid2\"]/tbody"));  
		List<WebElement> row = usertable.findElements(By.tagName("tr"));
		for ( int r=0; r<row.size(); r++)
			{
				List<WebElement> col = row.get(r).findElements(By.tagName("td"));
				for (int c=0; c<col.size(); c++)
				{
					String celltext = col.get(c).getText();
					Assert.assertNotEquals(celltext, newuser);
					
				}
			}
		//change Sub account
		driver.findElement(By.xpath("//a[@id='btnSubAccountTopNavigation']")).click();
		//select Sub account
		driver.findElement(By.xpath("//a[@id='btnSubAccountTopNavigation']/following::ul/li/a[text()='SmartDiesel']")).click();	
		driver.findElement(By.xpath("//a[@id='ctl00_lnkSettings']")).click();
		
		//Delete User from another sub account
		driver.findElement(By.xpath("//td[contains(text(),'" + newuser + "')]/following-sibling::td/a/img[@title='Delete']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div/button[@type='submit']")).click();
		
		driver.close();
		System.out.println("DeleteUserTest Successful");
	}

	@AfterMethod
	public void endSession()
	{
		driver.quit();
	}

	
}
