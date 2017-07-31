package Test.BasicTatoc;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

import static org.assertj.core.api.Assertions.*;

public class GridTest {
	
	

	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeClass
	public static void launchBrowser() throws IOException {
		BrowserLaunch.launchBrowser();
		driver=BrowserLaunch.driver;
		wait=BrowserLaunch.wait;
	}

	@Test(priority = 1)
	public void greenGridTest() {
		driver.get("http://10.0.1.86/tatoc/basic/grid/gate");
		driver.findElement(By.className("greenbox")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/frame/dungeon");
		driver.navigate().back();
	}

	@Test(priority = 2)
	public void redGridTest() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("redbox")));
		driver.findElement(By.className("redbox")).click();
		assertThat(driver
				.findElement(By.xpath("//div//span[contains(text(),'The page you are looking for does not exist')]")))
						.isNotEqualTo(null);
		driver.navigate().back();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("greenbox")));
		driver.findElement(By.className("greenbox")).click();

		System.out.println("hey");

	}

	



	

	
}
