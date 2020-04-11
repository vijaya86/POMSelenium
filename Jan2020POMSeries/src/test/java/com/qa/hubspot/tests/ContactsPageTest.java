package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.ContactsPage;
import com.qa.hubspot.pages.HomePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.ExcelUtil;

public class ContactsPageTest {

	Properties prop;
	WebDriver driver;
	BasePage basePage;
	LoginPage loginPage;
	HomePage homePage;
	ContactsPage contactsPage;

	@BeforeTest
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_prop();
		driver = basePage.init_driver(prop);
		loginPage = new LoginPage(driver);
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		contactsPage = homePage.goToContactsPage();
	}

	@Test(priority = 1)
	public void verifyContactsPageTitleTest() {
		String title = contactsPage.getContactsPageTitle();
		System.out.println("contacts page title is : " + title);
		Assert.assertEquals(title, AppConstants.CONTACTS_PAGE_TITLE);
	}

	@DataProvider
	public Object[][] getContactsData() {
		Object data[][] = ExcelUtil.getTestData(AppConstants.CONTACTS_SHEET_NAME);
		return data;
	}

	@Test(priority = 2, dataProvider = "getContactsData")
	public void createNewContactsTest(String email, String firstName, String lastName, String jobTitle)
			throws InterruptedException {
		contactsPage.createContact(email, firstName, lastName, jobTitle);

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
