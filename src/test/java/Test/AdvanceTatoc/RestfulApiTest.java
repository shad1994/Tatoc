package Test.AdvanceTatoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import UtilityClasses.BrowserLaunch;
import UtilityClasses.ServerRequests;

public class RestfulApiTest {

	static WebDriver driver;
	static WebDriverWait wait;

	/*public static void main(String[] args) throws Exception {
		BrowserLaunch.launchBrowser();
		driver = BrowserLaunch.driver;
		wait=BrowserLaunch.wait;
		RestfulApiTest r = new RestfulApiTest();
		r.restfulTest();
		
	}*/

	@BeforeClass
	public static void launchBrowser() throws IOException {
	
		driver=VideoPlayerTest.driver;
		wait=BrowserLaunch.wait;
		System.out.println(driver.getCurrentUrl());
	}
	
	@Test
	public void restfulTest() throws Exception {
		driver.get("http://10.0.1.86/tatoc/advanced/rest");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("session_id")));
		String session_id = driver.findElement(By.id("session_id")).getText().toString();
		String[] parts = session_id.split(" ");
		System.out.println("session id: " + parts[2]);
		ServerRequests req = new ServerRequests();
		String response = req.getRequest("http://10.0.1.86/tatoc/advanced/rest/service/token/" + parts[2]);
		JSONObject jsonObject = new JSONObject(response);
		String token = jsonObject.getString("token");
		System.out.println("token: " + token);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", parts[2]));
		params.add(new BasicNameValuePair("signature", token));
		params.add(new BasicNameValuePair("allow_access", "1"));
		String res = req.postRequest("http://10.0.1.86/tatoc/advanced/rest/service/register", params);
		System.out.println(res);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[contains(text(),'Proceed')]")));
		driver.findElement(By.xpath("//div/a[contains(text(),'Proceed')]")).click();
		System.out.println("restful test");

	}

}
