package Test.BasicTatoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

public class DragAndDropTest {
	static WebDriver driver;
	static WebDriverWait wait;
	
	@BeforeClass
	public static void launchBrowser() throws IOException {
		
		driver=DungeonTest.driver;
		wait=BrowserLaunch.wait;
	}
	@Test(priority = 1)
	public void dragAndDropFailTest() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/error");
		driver.navigate().back();
	}

	@Test(priority = 2)

	public void dragAndDropTest() {
		WebElement From = driver.findElement(By.xpath("//div[@class='ui-draggable']"));

		WebElement To = driver.findElement(By.xpath("//div[contains(text(),'DROPBOX')]"));

		Actions builder = new Actions(driver);

		Action dragAndDrop = builder.clickAndHold(From)

				.moveToElement(To)

				.release(To)

				.build();

		dragAndDrop.perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/windows");
	}
}
