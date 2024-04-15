package com.parasoft.findings.ado.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class WebElementUtils {
    private WebElementUtils() {
    }
    public static  WebElement waitUntilClickable(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.ignoring(StaleElementReferenceException.class);
        return wait.until(elementToBeClickable(element));
    }

    public static WebElement waitUntilVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.ignoring(StaleElementReferenceException.class);
        return wait.until(visibilityOf(element));
    }

    public static void clickAndUseJs(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();",element);
    }

    public static void waitUntilTextAppear(WebDriver driver, WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        wait.until(visibilityOf(element));
    }

    public static void waitUntilTextsAppear(WebDriver driver, WebElement element, String[] texts) {
        WebDriverWait wait = new WebDriverWait(driver,300);
        ExpectedCondition<Boolean>[] conditions = new ExpectedCondition[texts.length];
        for (int i = 0; i < texts.length; i++) {
            conditions[i] = ExpectedConditions.textToBePresentInElement(element, texts[i]);
        }
        wait.until(ExpectedConditions.or(conditions));
    }

    /**
     * This is not a best practice, try not to use it as much as possible
     */
    public static void waitAndLoad(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}