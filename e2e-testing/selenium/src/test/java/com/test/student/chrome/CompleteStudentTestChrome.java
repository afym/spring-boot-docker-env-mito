package com.test.student.chrome;

import com.test.DriverFactory;
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
public class CompleteStudentTestChrome {
    private String hostUrl;
    private RemoteWebDriver remoteWebDriver;

    @Before
    public void setDriver() throws MalformedURLException {
        this.hostUrl = "http://frontend-inside/";
        //this.hostUrl = "http://frontend/";
        this.remoteWebDriver = DriverFactory.getChromeInstance("http://localhost:4444/wd/hub");
        this.remoteWebDriver.get(this.hostUrl);
        System.out.println(this.remoteWebDriver.getTitle());
    }

    @Test
    public void AInsertMaleStudent() {
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 2);

        wait.until(presenceOfElementLocated(By.id("addNewButton"))).click();
        wait.until(presenceOfElementLocated(By.id("addForm")));

        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input").sendKeys("Ronald");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[2]").sendKeys("Perez");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[3]").sendKeys("rperez@company.com");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[4]").sendKeys("MALE");
        this.remoteWebDriver.findElementById("addButton").click();

        this.remoteWebDriver.navigate().refresh();
        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();

        assertTrue(body.contains("Ronald"));
        assertTrue(body.contains("Perez"));
        assertTrue(body.contains("rperez@company.com"));
        assertTrue(body.contains("MALE"));
    }

    @Test
    public void BInsertFemaleStudent() {
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 1);

        wait.until(presenceOfElementLocated(By.id("addNewButton"))).click();
        wait.until(presenceOfElementLocated(By.id("addForm")));

        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input").sendKeys("Ana");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[2]").sendKeys("Casas");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[3]").sendKeys("acasas@company.com");
        this.remoteWebDriver.findElementByXPath("//form[@id='addForm']/input[4]").sendKeys("FEMALE");
        this.remoteWebDriver.findElementById("addButton").click();

        this.remoteWebDriver.navigate().refresh();
        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();

        assertTrue(body.contains("Ana"));
        assertTrue(body.contains("Perez"));
        assertTrue(body.contains("acasas@company.com"));
        assertTrue(body.contains("FEMALE"));
    }

    @Test
    public void CUpdateStudent() {
        WebDriverWait wait = DriverFactory.getWaitInstance(this.remoteWebDriver, 1);

        this.remoteWebDriver.findElement(By.cssSelector(".ant-table-row:nth-child(1) .ant-btn-primary")).click();
        this.remoteWebDriver.findElement(By.name("firstName")).click();
        this.remoteWebDriver.findElement(By.name("firstName")).clear();
        this.remoteWebDriver.findElement(By.name("firstName")).sendKeys("Renzo");
        this.remoteWebDriver.findElement(By.id("editButton")).click();

        this.remoteWebDriver.navigate().refresh();
        String body = wait.until(presenceOfElementLocated(By.tagName("body"))).getText();

        assertTrue(body.contains("Renzo"));
        assertFalse(body.contains("Ronald"));
    }


    @Test
    public void DDeleteStudents(){
        this.remoteWebDriver.findElement(By.cssSelector(".ant-table-row:nth-child(1) .ant-btn-danger")).click();
        this.remoteWebDriver.findElement(By.cssSelector(".ant-btn-sm:nth-child(2)")).click();

        this.remoteWebDriver.navigate().refresh();

        String body = this.remoteWebDriver.findElement(By.tagName("body")).getText();

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
