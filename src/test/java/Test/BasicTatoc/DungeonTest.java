package Test.BasicTatoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

public class DungeonTest {
	static WebDriver driver;
	static WebDriverWait wait;
	String box1_color, box2_color;
	@BeforeClass
	public static void launchBrowser() throws IOException {
		
		driver=GridTest.driver;
		wait=BrowserLaunch.wait;
	}
	
	@Test(priority = 1)
	public void repaintSecondBoxtest() throws InterruptedException {
		driver.switchTo().frame("main");
		driver.switchTo().frame("child");
		box2_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 2')]"))
				.getAttribute("class");
		System.out.println("box2 color " + box2_color);

		driver.switchTo().parentFrame();
		driver.findElement(By.xpath("//center//a[contains(text(),'Repaint Box 2')]")).click();

		driver.switchTo().frame("child");
		String box2_newcolor = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 2')]"))
				.getAttribute("class");
		System.out.println("new box2 color " + box2_color);
		while (box2_color.equals(box2_newcolor)) {
			driver.switchTo().parentFrame();
			driver.findElement(By.xpath("//center//a[contains(text(),'Repaint Box 2')]")).click();

			driver.switchTo().frame("child");
			box2_newcolor = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 2')]"))
					.getAttribute("class");
			System.out.println("new box2 color " + box2_color);
		}

		assertThat(box2_color).isNotEqualTo(box2_newcolor);

	}

	@Test(priority = 2)
	public void proceedFailTest() throws InterruptedException {
		driver.switchTo().parentFrame();
		box1_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 1')]"))
				.getAttribute("class");
		System.out.println("box1 color " + box1_color);
		while (box2_color.equals(box1_color)) {
			System.out.println("box1 " + box1_color + " " + "box2 " + box2_color);
			Thread.sleep(1000);
			// driver.switchTo().parentFrame();
			driver.findElement(By.xpath("//center//a[contains(text(),'Repaint Box 2')]")).click();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

			driver.switchTo().frame("child");
			box2_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 2')]"))
					.getAttribute("class");
			driver.switchTo().parentFrame();
		}

		// driver.switchTo().parentFrame();
		driver.findElement(By.xpath("//center//a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/error");
		driver.switchTo().defaultContent();

		driver.navigate().back();
	}

	@Test(priority = 3)
	public void proceedPassTest() throws InterruptedException {
		driver.switchTo().frame("main");
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		while (!box2_color.equals(box1_color)) {

			driver.findElement(By.xpath("//center//a[contains(text(),'Repaint Box 2')]")).click();
			System.out.println(box1_color + " " + box2_color);

			box1_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 1')]"))
					.getAttribute("class");
			driver.switchTo().frame("child");
			box2_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 2')]"))
					.getAttribute("class");
			driver.switchTo().parentFrame();

		}
		System.out.println(box1_color + " " + box2_color);

		driver.findElement(By.xpath("//center/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/drag");
		driver.switchTo().defaultContent();
	}
}
