package Test.BasicTatoc;



import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DungeonTest {
	
	 WebDriver driver;
	 WebDriverWait wait;
	
	@BeforeTest
	public  void getDriverinstance()
	{
		System.out.println("new class");
		driver=OpenBasicTatocTest.driver;
		wait =OpenBasicTatocTest.wait;
		
	}
	
	@Test
	public void repaintSecondBoxtest() throws InterruptedException
	{
	
		Thread.sleep(3000);
		driver.switchTo().frame("main");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='answer']")));
		driver.findElement(By.xpath(".//*[@id='answer']"));
		String box1_color=driver.findElement(By.xpath("//div[@id='answer'][contains(text(),'Box 1')]")).getAttribute("class");
		System.out.println(box1_color);
		WebElement element=driver.findElement(By.xpath("//center//a[contains(text(),'Repaint Box 2')]"));
	}

}
