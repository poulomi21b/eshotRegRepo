package com.eshot.qa.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.eshot.qa.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public class TestUtils extends TestBase
{
	
	public TestUtils() throws IOException 
	{
		super();
	}
	
	public void CreateGroup() throws InterruptedException
	{
		initialization();
		driver.findElement(By.id("txtUName")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("txtPwd")).sendKeys(prop.getProperty("pwd"));
		driver.findElement(By.id("txtEmail")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("lvSubaccounts_ctrl9_Literal1")).click();
		driver.findElement(By.xpath("//a[text()='Contacts Manager']")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@id = 'addGroup']")).click();
		
		
		WebDriverWait wait1 =new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@name,'ctl00_ContentHolder')]"))); 
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'ctl00_ContentHolder')]")));
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id = 'ctl00_ContentHolder_txtGrpName']")).sendKeys(prop.getProperty("groupname"));
		driver.findElement(By.xpath("//input[@id = 'ctl00_ContentHolder_chkBranded']")).click();
		driver.findElement(By.xpath("//input[@id = 'ctl00_ContentHolder_btnOK']")).click();
		//driver.close();
	}
	
	
	public void AddContact(String email,String mobile,String title, String fname, String lname) throws InterruptedException
	{
		initialization();
		driver.findElement(By.id("txtUName")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("txtPwd")).sendKeys(prop.getProperty("pwd"));
		driver.findElement(By.id("txtEmail")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("lvSubaccounts_ctrl9_Literal1")).click();
		driver.findElement(By.xpath("//a[text()='Contacts Manager']")).click();
		
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
	     //driver.close();
	}
	
	public void Adduser(String newuser) throws InterruptedException
	{
		initialization();
		driver.findElement(By.id("txtUName")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("txtPwd")).sendKeys(prop.getProperty("pwd"));
		driver.findElement(By.id("txtEmail")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.id("ctl00_lnkSettings")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"btnAddUser\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_FirstName\"]")).sendKeys("AutomationTestUserF");
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_LastName\"]")).sendKeys("AutomationTestUserL");
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_Email\"]")).sendKeys(newuser);
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_UserName\"]")).sendKeys("AutomationTestUser");
		driver.findElement(By.xpath("//*[@id=\"AddEditUserDetailViewModel_Password\"]")).sendKeys("AutomationTestUser");
		driver.findElement(By.xpath("//*[@id=\"ManageContacts\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"addEditUserForm\"]/div/div[3]/div/div[2]/div/button")).click();

	}
	
	public void DisablePreferenceCentre()
	{
		
	}
	
	public void DeleteSignUpForms()
	{
		
	}
	
	public void DeleteCampaigns() throws ClassNotFoundException, SQLException
	{
		System.out.println("cleaning Test data from database");
		//Connection URL Syntax: "jdbc:sqlserver://ipAddress:portNumber/dbName"
		System.out.println("Starting to Delete Campaigns");
		//Query to Execute
			String query1 = "DELETE From EshotMailSend where EmailQueueHeadID IN (Select CampaignId From ReportCampaignSendSummary where CampaignId IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%'));";
			String query2 = "DELETE From AutomatedReportSendDetails where CampaignId IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query3 = "DELETE From AutomatedSeries where ID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query4 = "DELETE From Campaign where ClonedFromCampaignID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query5 = "DELETE From CustomFieldFilterUsage where CampaignID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query6 = "DELETE From DateDrivenCampaign where ID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query7 = "DELETE From EshotMailSend where EmailQueueHeadID IN (Select AUTOID From EmailQueueHead where CampaignID  IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%'));";
			String query8 = "DELETE From dbo.ReadStatsFromPagE where ehid IN (Select AUTOID From EmailQueueHead where CampaignID  IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%'));";
			String query9 = "DELETE From Visitor where ehid IN (Select AUTOID From EmailQueueHead where CampaignID  IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%'));";
			String query10 = "DELETE From EmailQueueHead where CampaignID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query11 = "DELETE From EmailQueueHeadA where CampaignID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query12 = "DELETE From RecurrentCampaign where ID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query13 = "DELETE From ReportCampaignSendSummary where CampaignId IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query14 = "DELETE From ResendCampaign where SourceCampaignID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query15 = "DELETE From SingleSendCampaign where ID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query16 = "DELETE From SmsSingleSendCampaign where ID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query17 = "DELETE From SplitTestCampaign where ID IN (Select ID from dbo.Campaign where Name Like '%AutomationTest%');";
			String query18 = "DELETE From dbo.Campaign where Name Like '%AutomationTest%';";
			
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
			st.executeUpdate(query5);
			st.executeUpdate(query6);
			st.executeUpdate(query7);
			st.executeUpdate(query8);
			st.executeUpdate(query9);
			st.executeUpdate(query10);
			st.executeUpdate(query11);
			st.executeUpdate(query12);
			st.executeUpdate(query13);
			st.executeUpdate(query14);
			st.executeUpdate(query15);
			st.executeUpdate(query16);
			st.executeUpdate(query17);
			st.executeUpdate(query18);
			
		System.out.println("Execution Successful");
		// closing DB Connection
			conn.close();
	}

	public void ManageProfileChangeStatus() throws ClassNotFoundException, SQLException
	{
		 //Setting the status to Inactive for all the Forms
		    String dbUrl = prop.getProperty("databasename");
		    
		//Database Username
			String userName = "eshotAdmin";
			
		//Database Password
			String password = "devAdmin";
		
		//System.out.println("Starting to Delete");
		//Query to Execute
			String query1 = "UPDATE ManageProfile set IsActive = 0 where SubAccountId = 3";
						
	        System.out.println("Loading Driver");	
	    //Load mysql jdbc driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		
		//Create Connection to DB
			System.out.println("Connecting Database");
			Connection conn = DriverManager.getConnection(dbUrl, userName, password);
			System.out.println("Connection Successful");
			
		//Create Statement Object
			Statement st = conn.createStatement();
		
		    System.out.println("Executing Query ");
		// Execute the SQL Query. Store results in ResultSet
			st.executeUpdate(query1);
			
		    System.out.println("Execution Successful");
		// closing DB Connection
			conn.close();
	}
	
	public void DeleteMessage() throws ClassNotFoundException, SQLException
	{
		System.out.println("cleaning Test data from database");
		System.out.println("Starting to Delete Message");
		//Query to Execute
			String query1 = "DELETE From dbo.Messagetbl where MessageName Like '%AutomationTest%';";
			
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

			
		System.out.println("Execution Successful");
		// closing DB Connection
			conn.close();
		
	}
	
	public void DeleteGroup() throws ClassNotFoundException, SQLException
	{
		System.out.println("cleaning Test data from database");
		System.out.println("Starting to Delete Group");
		//Query to Execute
			String query1 = "DELETE From AccountGrouptbl where NGID IN (Select AutoID from newsgroups where Name = 'AutomationTest');";
			String query2 = "DELETE From AutomatedSeries where ID IN (Select AutoID from newsgroups where Name = 'AutomationTest');";
			String query3 = "DELETE From AutomatedSeriesAction where ID IN (Select AutoID from newsgroups where Name = 'AutomationTest');";
			String query4 = "DELETE From ManageProfileGroup where ID IN (Select AutoID from newsgroups where Name = 'AutomationTest');";
			String query5 = "DELETE From NGLinkCon where AutoID IN (Select AutoID from newsgroups where Name = 'AutomationTest');";
			String query6 = "DELETE From SignUpFormGroup where ID IN (Select AutoID from newsgroups where Name = 'AutomationTest');";
			String query7 = "DELETE From newsgroups where Name = 'AutomationTest';";
			
			
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
			st.executeUpdate(query5);
			st.executeUpdate(query6);
			st.executeUpdate(query7);

			
		System.out.println("Execution Successful");
		// closing DB Connection
			conn.close();
		
	}
	
}
