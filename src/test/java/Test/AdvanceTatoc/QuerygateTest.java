package Test.AdvanceTatoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;
import UtilityClasses.DBAccess;

public class QuerygateTest {
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeClass
	public static void launchBrowser() throws IOException {
		
		driver=HoverMenuTest.driver;
		wait=BrowserLaunch.wait;
	}
	
	@Test
	public void queryGateTest() throws ClassNotFoundException, SQLException {
		//driver.get("http://10.0.1.86/tatoc/advanced/query/gate");
		String symbol = driver.findElement(By.id("symboldisplay")).getText().toLowerCase();
		DBAccess db = new DBAccess();
		String id = db.findValue("id", "identity", "symbol", symbol);

		System.out.println(symbol);

		String name = db.findValue("name", "credentials", "id", id);
		String passkey = db.findValue("passkey", "credentials", "id", id);

		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("passkey")).sendKeys(passkey);
		driver.findElement(By.id("submit")).click();

		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/advanced/video/player");
		System.out.println("query gate");
	}

}
