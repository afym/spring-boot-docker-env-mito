package com.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        Capabilities firefoxCapabilities = DesiredCapabilities.firefox();
        RemoteWebDriver firefox = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxCapabilities);
        WebDriverWait wait = new WebDriverWait(firefox, 2);

        try {
            simpleTest(firefox, wait);
            simpleTest2(firefox, wait);
        } catch (Exception e) {
            System.out.println("Some error" + e.getMessage());
        } finally {
            firefox.quit();
        }
    }

    private static void simpleTest(RemoteWebDriver firefox, WebDriverWait wait){
        // run against firefox
        firefox.get("https://www.google.com");

        System.out.println(firefox.getTitle());
        firefox.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);

        WebElement firstResult = wait.until(presenceOfElementLocated(By.id("resultStats")));
        System.out.println(firstResult.getText());
    }

    private static void simpleTest2(RemoteWebDriver firefox, WebDriverWait wait) {
        firefox.get("https://tryphp.w3schools.com/demo/demo_form_validation_complete.php");
        WebElement name = firefox.findElementByName("name");
        name.click();
        name.sendKeys("angel");

        WebElement email = firefox.findElementByName("email");
        email.click();
        email.sendKeys("angel@angel.com");

        WebElement website = firefox.findElementByName("website");
        website.click();
        website.sendKeys("http://angel.com");

        WebElement comment = firefox.findElementByName("comment");
        comment.click();
        comment.sendKeys("Some comment");

        WebElement gender = firefox.findElementByCssSelector("input:nth-child(17)");
        gender.click();

        WebElement submit = firefox.findElementByName("submit");
        submit.click();

        String result = firefox.findElementByCssSelector("body").getText();
        System.out.println(result.contains("Some comment"));
        System.out.println(result.contains("angel@angel.com"));
        System.out.println(result.contains("pepe-lucho-come-mucho"));
    }
}
