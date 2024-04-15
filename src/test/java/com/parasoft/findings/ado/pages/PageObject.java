package com.parasoft.findings.ado.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObject {
    private static WebDriver driver;

    public PageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    public static WebDriver getDriver() {
        return driver;
    }
}
