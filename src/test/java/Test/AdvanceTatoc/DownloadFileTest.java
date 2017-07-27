package Test.AdvanceTatoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;

public class DownloadFileTest {
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeClass
	public static void launchBrowser() throws IOException {

		driver = VideoPlayerTest.driver;
		wait = BrowserLaunch.wait;
	}

	@Test
	public void fileHandleTest() throws IOException, InterruptedException {

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Download File')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Download File')]")).click();

		Thread.sleep(2000);
		File file = new File("resources/file_handle_test.dat");

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
		file.delete();
		assertThat(driver.getCurrentUrl()).isEqualTo("http://10.0.1.86/tatoc/end");

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
