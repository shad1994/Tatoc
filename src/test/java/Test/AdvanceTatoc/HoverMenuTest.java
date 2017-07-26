package Test.AdvanceTatoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class HoverMenuTest {

	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeTest
	public static void launchBrowser() throws IOException {
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
			System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
			driver = new ChromeDriver();
			// driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");

		}
		wait = new WebDriverWait(driver, 10);
	}

	@Test(priority = 1)
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
	}

	@Test(priority = 2)
	public void queryGateTest() throws ClassNotFoundException, SQLException {
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
	}

	@Test(priority = 3)
	public void OoyalaTest() {

	}

	@Test(priority = 4)
	public void restfulTest() throws Exception {
		driver.get("http://10.0.1.86/tatoc/advanced/rest");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("session_id")));
		String session_id = driver.findElement(By.id("session_id")).getText().toString();
		String[] parts = session_id.split(" ");
		System.out.println("session id: " + parts[2]);
		requests req = new requests();
		String response = req.sendGet("http://10.0.1.86/tatoc/advanced/rest/service/token/" + parts[2]);
		JSONObject jsonObject = new JSONObject(response);
		String token = jsonObject.getString("token");
		System.out.println("token: " + token);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", parts[2]));
		params.add(new BasicNameValuePair("signature", token));
		params.add(new BasicNameValuePair("allow_access", "1"));
		String res = req.sendPost("http://10.0.1.86/tatoc/advanced/rest/service/register", params);
		System.out.println(res);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();

	}

	@Test(priority = 5)
	public void fileHandleTest() throws IOException {

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Download File')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Download File')]")).click();
		File file = HoverMenuTest.getLatestFilefromDir("/home/qainfotech/Downloads");
		
		String signature = " ";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				System.out.println(line);
				if (line.contains("Signature")) {
					String[] parts = line.split(" ");
					signature = parts[1];
				} else {
					System.out.println("no signature found");
				}
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signature")));
		driver.findElement(By.id("signature")).sendKeys(signature);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("submit")));
		driver.findElement(By.className("submit")).click();

		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/end");
		file.delete();

	}

	// Get the latest file from a specific directory
	public static File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

}
