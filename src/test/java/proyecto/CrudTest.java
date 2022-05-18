package proyecto;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import java.io.File;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
	  WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testAdd() throws Exception {
    driver.get("https://mern-crud.herokuapp.com");
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).clear();
    driver.findElement(By.name("name")).sendKeys("hola hola 2");
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("exemplo@gmail.com");
    driver.findElement(By.name("age")).click();
    driver.findElement(By.name("age")).clear();
    driver.findElement(By.name("age")).sendKeys("5");
    driver.findElement(By.name("age")).click();
    driver.findElement(By.name("age")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
    pause (1500);
    //Asert - buscar en mensaje
    assertThat(driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/p")).getText(),is("Successfully added!"));
    driver.findElement(By.xpath("//i")).click();
  }
  
  @Test
  public void testEdit() throws Exception {
    driver.get("https://mern-crud.herokuapp.com");
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")).click();
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).clear();
    driver.findElement(By.name("name")).sendKeys("Modificacion");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
    pause (1500);
    //Asert - editar info 
    assertThat(driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/p")).getText(),is("Successfully updated!"));
    driver.findElement(By.xpath("//i")).click();
  }
  
  @Test
  public void testFdelete() throws Exception {
    driver.get("https://mern-crud.herokuapp.com");
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]")).click();
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button[2]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Modificacion'])[2]/following::button[1]")).click();
    pause (1500);
    //Assert - eliminar info
    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Modificacion[\\s\\S]*$"));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private void pause (long mils) {
	try {
		Thread.sleep(mils);
	}catch(Exception e) {
		e.printStackTrace();
	}
  }
  
  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

