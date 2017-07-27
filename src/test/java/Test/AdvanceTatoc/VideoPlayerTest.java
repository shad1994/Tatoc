package Test.AdvanceTatoc;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

public class VideoPlayerTest {

	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeClass
	public static void launchBrowser() throws IOException {
	
		driver=QuerygateTest.driver;
		wait=BrowserLaunch.wait;
	}
	
	@Test
	public void OoyalaTest() {
		System.out.println(driver.getCurrentUrl());
		System.out.println("video player");
	}
}
