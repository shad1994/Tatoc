package Test.BasicTatoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

public class PopupWindowTest {
	static WebDriver driver;
	static WebDriverWait wait;
	
	@BeforeClass
	public static void launchBrowser() throws IOException {
		
		driver=DragAndDropTest.driver;
		wait=BrowserLaunch.wait;
	}
	@Test
	public void launchPopupWindowTest() {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Launch Popup Window')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Launch Popup Window')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/windows#");

	}

	@Test(priority = 9)
	public void submitNameFailtest() {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		System.out.println(tabs2.size());

		System.out.println("switch");

		driver.switchTo().window(tabs2.get(1));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
		driver.findElement(By.id("submit")).click();

		System.out.println("submit");

		driver.switchTo().window(tabs2.get(0));
		System.out.println("switch back");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));

		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		System.out.println("proceed");
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/error");
		driver.navigate().back();
	}

	@Test(priority = 10)
	public void submitNameTest() {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Launch Popup Window')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Launch Popup Window')]")).click();

		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		System.out.println(tabs2.size());
		driver.switchTo().window(tabs2.get(1));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
		driver.findElement(By.id("name")).sendKeys("Shadab Sayeed");
		driver.findElement(By.id("submit")).click();
		driver.switchTo().window(tabs2.get(0));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/cookie");

	}
}
