package Test.AdvanceTatoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

import static org.assertj.core.api.Assertions.*;

public class HoverMenuTest {

	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeClass
	public static void launchBrowser() throws IOException {
		BrowserLaunch.launchBrowser();
		driver=BrowserLaunch.driver;
		wait=BrowserLaunch.wait;
	}

	@Test
	public void hoverMenuTest() {
		driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");
		
		// locate the menu to hover over using its xpath
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[contains(text(),'Menu 2')]")));
		WebElement menu = driver.findElement(By.xpath("//div/span[contains(text(),'Menu 2')]"));

		// Initiate mouse action using Actions class
		Actions builder = new Actions(driver);

		// move the mouse to the earlier identified menu option
		builder.moveToElement(menu).build().perform();

		// wait for max of 5 seconds before proceeding. This allows sub menu
		// appears properly before trying to click on it
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/span[contains(text(),'Go Next')]")));
		
		// identify menu option from the resulting menu display and click
		WebElement menuOption = driver.findElement(By.xpath("//div/span[contains(text(),'Go Next')]"));
		menuOption.click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/advanced/query/gate");
		System.out.println("hover menu");
	}	

}
