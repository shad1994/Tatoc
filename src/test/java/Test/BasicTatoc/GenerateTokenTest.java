package Test.BasicTatoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

public class GenerateTokenTest {
	static WebDriver driver;
	static WebDriverWait wait;
	
	@BeforeClass
	public static void launchBrowser() throws IOException {
		
		driver=PopupWindowTest.driver;
		wait=BrowserLaunch.wait;
	}
	@Test
	public void generateToken() throws InterruptedException {

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Generate Token')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Generate Token')]")).click();

		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/cookie#");

		String token = driver.findElement(By.xpath("//div/span[@id='token']")).getText();
		String[] parts = token.split(" ");
		System.out.println(parts[1]);
		Cookie name = new Cookie("Token", parts[1]);
		driver.manage().addCookie(name);
		System.out.println("set cockie");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();

		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/end");
		assertThat(driver.findElement(By.xpath("//div/span[@class='finish']")).getText())
				.isEqualTo("Obstacle Course is Complete!");
		System.out.println("COMPLETE");
	}

}
