package com.eshot.qa.testcases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.eshot.qa.utils.TestUtils;
import com.eshot.qa.base.TestBase;

class AddNewContactTest extends TestBase
{
	TestUtils TestUtils ;
	
	
	public AddNewContactTest() throws IOException
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
	driver.findElement(By.xpath("//a[text()='Contacts Manager']")).click();
}

@Test(priority=1)
@Parameters({"email","mobile","title","fname","lname"})
public void AddContact(String email,String mobile,String title, String fname, String lname ) throws InterruptedException, SQLException, ClassNotFoundException
{
	
	TestUtils.CreateGroup();	
	Thread.sleep(2000);
	driver.findElement(By.xpath("//a[text()='Add a new contact']")).click();
	driver.findElement(By.xpath("//input[@id='ContactBasicFields_Email']")).sendKeys(email);
	driver.findElement(By.xpath("//input[@id='ContactBasicFields_MobileNumber']")).sendKeys(mobile);
	driver.findElement(By.xpath("//input[@id='ContactBasicFields_Salutation']")).sendKeys(title);
	driver.findElement(By.xpath("//input[@id='ContactBasicFields_Firstname']")).sendKeys(fname);
	driver.findElement(By.xpath("//input[@id='ContactBasicFields_Lastname']")).sendKeys(lname);
	driver.findElement(By.xpath("//div/label[text()='AutomatedDDMMYYYY']/following-sibling::div/span/select[@class='day ']")).click();
	driver.findElement(By.xpath("//div/label[text()='AutomatedDDMMYYYY']/following-sibling::div/span/select/option[text()='10']")).click();
	
	driver.findElement(By.xpath("//div/label[text()='AutomatedDDMMYYYY']/following-sibling::div/span/select[@class='month ']")).click();
	driver.findElement(By.xpath("//div/label[text()='AutomatedDDMMYYYY']/following-sibling::div/span/select/option[text()='10']")).click();
	
	driver.findElement(By.xpath("//div/label[text()='AutomatedDDMMYYYY']/following-sibling::div/span/select[@class='year ']")).click();
	driver.findElement(By.xpath("//div/label[text()='AutomatedDDMMYYYY']/following-sibling::div/span/select/option[text()='2018']")).click();
	
	driver.findElement(By.xpath("//ul[@class='select2-selection__rendered']")).click();
	WebElement group= driver.findElement(By.xpath("//ul[@class='select2-results__options']"));
	List<WebElement> groupList=group.findElements(By.tagName("li"));
	
				for (WebElement li : groupList)
						
				{
				if (li.getText().equals(prop.getProperty("groupname")))
					
				   {
				     li.click();
				     break;
				   }
				
				}
				
	 driver.findElement(By.xpath("//ul[@class='select2-selection__rendered']")).click();			
     driver.findElement(By.xpath("//button[@id='btnSaveMapping']")).click();
	
}
}
