package UtilityClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserLaunch {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	public static void launchBrowser() throws IOException
	{
		Properties p = new Properties();
		FileInputStream file = new FileInputStream("resources/data.properties");
		p.load(file);

		System.out.println(p.getProperty("browser"));
		if (p.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
			driver = new FirefoxDriver();
			// driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");

		} else if (p.getProperty("browser").equals("chrome")) {
			System.out.println("hey");
			//Set preference to change the download directory
			System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
			String downloadFilepath = "resources";			
			HashMap chromePrefs = new HashMap();			
			chromePrefs.put("profile.default_content_settings.popups", 0);			
			chromePrefs.put("download.default_directory", downloadFilepath);			
			ChromeOptions options = new ChromeOptions();			
			options.setExperimentalOption("prefs", chromePrefs);			
			DesiredCapabilities cap = DesiredCapabilities.chrome();			
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);			
			cap.setCapability(ChromeOptions.CAPABILITY, options);			
			driver = new ChromeDriver(cap);
			// driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");

		}
		wait = new WebDriverWait(driver, 10);
	}
	
	public static void closeBrowser()
	{
		driver.quit();
	}

}
