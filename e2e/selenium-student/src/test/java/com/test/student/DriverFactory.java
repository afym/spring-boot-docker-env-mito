package com.test.student;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    public static RemoteWebDriver getFirefoxInstance(String hubHost) throws MalformedURLException {
        Capabilities capabilities = DesiredCapabilities.firefox();
        return new RemoteWebDriver(new URL(hubHost), capabilities);
    }

    public static RemoteWebDriver getChromeInstance(String hubHost) throws MalformedURLException {
        Capabilities capabilities = DesiredCapabilities.chrome();
        return new RemoteWebDriver(new URL(hubHost), capabilities);
    }

    public static WebDriverWait getWaitInstance(RemoteWebDriver driver, long seconds) {
        return new WebDriverWait(driver, seconds);
    }
}
