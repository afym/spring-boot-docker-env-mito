package com.test.student.chrome;

import com.test.student.DriverFactory;
import com.test.student.config.ConfigEnv;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompleteStudentChromeTest {
    private String hostUrl;
    private RemoteWebDriver remoteWebDriver;

    @Before
    public void setDriver() throws MalformedURLException {
        this.hostUrl = ConfigEnv.getSeleniumAppUrl("http://localhost:9999/");
        this.remoteWebDriver = DriverFactory.getChromeInstance(ConfigEnv.getSeleniumHubUrl("http://localhost:4444/wd/hub"));
        this.remoteWebDriver.get(this.hostUrl);
        System.out.println(this.remoteWebDriver.getTitle());
    }

    @Test
    public void AInsertMaleStudent() {
        System.out.println("TEST : AInsertMaleStudent");
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 6);

        wait.until(presenceOfElementLocated(By.id("addNewButton"))).click();
        wait.until(presenceOfElementLocated(By.id("addForm")));

        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input").sendKeys("Ronald");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[2]").sendKeys("Perez");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[3]").sendKeys("rperez@company.com");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[4]").sendKeys("MALE");
        this.remoteWebDriver.findElementById("addButton").click();

        this.remoteWebDriver.navigate().refresh();
        this.remoteWebDriver.navigate().refresh();

        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();
        System.out.println("BODY : " + body);
        assertTrue(body.contains("Ronald"));
        assertTrue(body.contains("Perez"));
        assertTrue(body.contains("rperez@company.com"));
        assertTrue(body.contains("MALE"));
    }

    @Test
    public void BInsertFemaleStudent() {
        System.out.println("TEST : BInsertFemaleStudent");
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 6);

        wait.until(presenceOfElementLocated(By.id("addNewButton"))).click();
        wait.until(presenceOfElementLocated(By.id("addForm")));

        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input").sendKeys("Ana");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[2]").sendKeys("Casas");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[3]").sendKeys("acasas@company.com");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[4]").sendKeys("FEMALE");
        this.remoteWebDriver.findElementById("addButton").click();

        this.remoteWebDriver.navigate().refresh();
        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();
        System.out.println("BODY : " + body);
        assertTrue(body.contains("Ana"));
        assertTrue(body.contains("Perez"));
        assertTrue(body.contains("acasas@company.com"));
        assertTrue(body.contains("FEMALE"));
    }

    @Test
    public void CUpdateStudent() {
        System.out.println("TEST : CUpdateStudent");
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 6);

        this.remoteWebDriver.findElement(By.cssSelector(".ant-table-row:nth-child(1) .ant-btn-primary")).click();
        this.remoteWebDriver.findElement(By.name("firstName")).click();
        this.remoteWebDriver.findElement(By.name("firstName")).clear();
        this.remoteWebDriver.findElement(By.name("firstName")).sendKeys("Renzo");
        this.remoteWebDriver.findElement(By.id("editButton")).click();

        this.remoteWebDriver.navigate().refresh();
        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();
        System.out.println("BODY : " + body);
        assertTrue(body.contains("Renzo"));
    }


    @Test
    public void DDeleteStudents(){
        System.out.println("TEST : DDeleteStudents");
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 6);
        this.remoteWebDriver.findElement(By.cssSelector(".ant-table-row:nth-child(1) .ant-btn-danger")).click();
        this.remoteWebDriver.findElement(By.cssSelector(".ant-btn-sm:nth-child(2)")).click();

        this.remoteWebDriver.navigate().refresh();

        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();
        System.out.println("BODY : " + body);
        assertFalse(body.contains("Ana"));
        assertFalse(body.contains("Casas"));
        assertFalse(body.contains("acasas@company.com"));
        assertFalse(body.contains("FEMALE"));
    }

    @After
    public void closeDriver() {
        this.remoteWebDriver.quit();
    }
}
