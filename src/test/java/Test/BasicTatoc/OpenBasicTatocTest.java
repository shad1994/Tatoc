package Test.BasicTatoc;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class OpenBasicTatocTest {
	static WebDriver driver;
	static WebDriverWait wait;
	String box1_color, box2_color;

	@BeforeTest
	public static void launchBrowser() throws IOException {
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("resources/data.properties");
		p.load(file);

		System.out.println("momo");

		System.out.println(p.getProperty("browser"));
		if (p.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
			driver = new FirefoxDriver();
			driver.get("http://10.0.1.86/tatoc/basic/grid/gate");

		} else if (p.getProperty("browser").equals("chrome")) {
			System.out.println("hey");
			System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
			driver = new ChromeDriver();
			driver.get("http://10.0.1.86/tatoc/basic/grid/gate");

		}
		wait = new WebDriverWait(driver, 20);
	}

	@Test(priority = 1)
	public void greenGridTest() {

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

	@Test(priority = 3)
	public void repaintSecondBoxtest() throws InterruptedException {

		// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='answer']")));
		// driver.findElement(By.xpath(".//*[@id='answer']"));
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

	@Test(priority = 4)
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

	@Test(priority = 5)
	public void proceedPassTest() throws InterruptedException {
		driver.switchTo().frame("main");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		while (!box2_color.equals(box1_color)) {

			// Thread.sleep(1000);

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//center//a[contains(text(),'Repaint Box 2')]")));
			driver.findElement(By.xpath("//center//a[contains(text(),'Repaint Box 2')]")).click();
			System.out.println(box1_color + " " + box2_color);
			// driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

			box1_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 1')]"))
					.getAttribute("class");
			driver.switchTo().frame("child");
			box2_color = driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 2')]"))
					.getAttribute("class");
			driver.switchTo().parentFrame();

		}
		System.out.println(box1_color + " " + box2_color);
		// driver.switchTo().parentFrame();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//center/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/basic/drag");
		driver.switchTo().defaultContent();
	}

	@Test(priority = 6)
	public void dragAndDropFailTest() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/error");
		driver.navigate().back();
	}

	@Test(priority = 7)

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

	@Test(priority = 8)
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
		driver.switchTo().window(tabs2.get(tabs2.size() - 1));
		// System.out.println(driver.findElement(By.id("submit")).getAttribute("onclick"));
		 System.out.println("switch");

		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
			
		 
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
			driver.findElement(By.id("name")).sendKeys("Shadab Sayeed");
			
		 driver.findElement(By.id("submit")).click();
		 
			 System.out.println("submit");

	

		// driver.switchTo().window(tabs2.get(0));
		// System.out.println("switch back");
		// driver.switchTo().window(tabs2.get(0));
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		// System.out.println("proceed");
		// driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		// assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/error");
		// driver.navigate().back();
	}

	/*@Test(priority = 10)
	public void submitNameTest() {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Launch Popup Window')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Launch Popup Window')]")).click();
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
		driver.findElement(By.id("name")).sendKeys("Shadab Sayeed");
		driver.findElement(By.id("submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		assertThat(driver.getCurrentUrl()).isEqualTo("10.0.1.86/tatoc/basic/cookie");
	
	}
	*/
}
