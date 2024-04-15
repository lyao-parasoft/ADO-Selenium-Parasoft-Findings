package com.parasoft.findings.ado.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WebDriverFactory {
    static {
        Logger logger = Logger.getLogger("org.openqa.selenium");
        logger.setLevel(Level.ALL);
        logger.addHandler(new SLF4JBridgeHandler());
    }
    private WebDriverFactory() { }

    public static WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "en-US,en");
        chromeOptions.setExperimentalOption("prefs", prefs);
        return new ChromeDriver(chromeOptions);
    }
}