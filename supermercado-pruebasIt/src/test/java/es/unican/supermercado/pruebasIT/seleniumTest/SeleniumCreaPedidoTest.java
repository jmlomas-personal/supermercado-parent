package es.unican.supermercado.pruebasIT.seleniumTest;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Clase que contiene  pruebas de aceptacion automatizadas con el 
 * framework Selenium. 
 * @author MacbookAir
 *
 */
public class SeleniumCreaPedidoTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://pupp3t.asuscomm.com:6080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSeleniumCreaPedido() throws Exception {
		driver.get(baseUrl + "/supermercado-web/");
		driver.findElement(By.id("j_idt9:nombre")).clear();
		driver.findElement(By.id("j_idt9:nombre")).sendKeys("45454545H");
		driver.findElement(By.id("j_idt9:nombre")).clear();
		driver.findElement(By.id("j_idt9:nombre")).sendKeys("45454545H");
		driver.findElement(By.name("j_idt9:j_idt15")).click();
		driver.findElement(By.name("j_idt9:j_idt15")).click();
		driver.findElement(By.name("j_idt20:j_idt22:2:j_idt33")).click();
		driver.findElement(By.name("j_idt20:j_idt22:2:j_idt33")).click();
		driver.findElement(By.id("j_idt20:unidades")).clear();
		driver.findElement(By.id("j_idt20:unidades")).sendKeys("3");
		driver.findElement(By.id("j_idt20:unidades")).clear();
		driver.findElement(By.id("j_idt20:unidades")).sendKeys("3");
		driver.findElement(By.name("j_idt20:j_idt31")).click();
		driver.findElement(By.name("j_idt20:j_idt31")).click();
		driver.findElement(By.cssSelector("a > img.icon")).click();
		driver.findElement(By.cssSelector("a > img.icon")).click();
		driver.findElement(By.id("j_idt34:fechaRecogida")).clear();
		driver.findElement(By.id("j_idt34:fechaRecogida")).sendKeys("23/02/2018");
		driver.findElement(By.id("j_idt34:fechaRecogida")).clear();
		driver.findElement(By.id("j_idt34:fechaRecogida")).sendKeys("23/02/2018");
		driver.findElement(By.name("j_idt34:j_idt40")).click();
		driver.findElement(By.name("j_idt34:j_idt40")).click();
		driver.findElement(By.id("j_idt34:fechaRecogida")).clear();
		driver.findElement(By.id("j_idt34:fechaRecogida")).sendKeys("23-02-2018");
		driver.findElement(By.id("j_idt34:fechaRecogida")).clear();
		driver.findElement(By.id("j_idt34:fechaRecogida")).sendKeys("23-02-2018");
		driver.findElement(By.name("j_idt34:j_idt40")).click();
		driver.findElement(By.name("j_idt34:j_idt40")).click();
		driver.findElement(By.id("j_idt34:fechaRecogida")).clear();
		driver.findElement(By.id("j_idt34:fechaRecogida")).sendKeys("2018-01-26");
		driver.findElement(By.id("j_idt34:fechaRecogida")).clear();
		driver.findElement(By.id("j_idt34:fechaRecogida")).sendKeys("2018-01-26");
		driver.findElement(By.name("j_idt34:j_idt40")).click();
		driver.findElement(By.name("j_idt34:j_idt40")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@SuppressWarnings("unused")
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
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
