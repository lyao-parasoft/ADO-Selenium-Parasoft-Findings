package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import com.parasoft.findings.ado.common.SeleniumTestConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class InstallOrUninstallPluginPage extends PageObject {
    @FindBy(xpath = "//button[@aria-label='More Actions...']")
    private WebElement moreActionButtonInPublishers;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Remove']")
    private WebElement removeButtonInPublishers;

    @FindBy(linkText = "Get it free")
    private WebElement getItFreeButtonInMarketplace;

    @FindBy(className = "ms-Dropdown-caretDownWrapper")
    private WebElement selectOrganizationSpinnerInMarketplace;

    @FindBy(xpath = "/descendant::div[normalize-space(.)='Install']//button")
    private WebElement installButtonInMarketplace;

    @FindBy(xpath = "/descendant::label[normalize-space(.)='You are all set!']")
    private WebElement successfullyInstalledText;

    public InstallOrUninstallPluginPage(WebDriver driver) {
        super(driver);
    }

    public void judgePluginExistAndUninstall() {
        try {
            WebElementUtils.waitUntilVisible(getDriver(), getDriver().findElement(By.xpath("//a[normalize-space(.)='Parasoft Findings']")));
            clickMoreActionButton();
            clickRemoveButton();
            waitUntilPluginUninstalled();
        } catch (Exception e) {
            throw new RuntimeException("Plugin not found in marketplace");
        }
    }

    public void clickMoreActionButton() {
        WebElementUtils.waitUntilClickable(getDriver(), moreActionButtonInPublishers);
        WebElementUtils.clickAndUseJs(getDriver(), moreActionButtonInPublishers);
    }

    public void clickRemoveButton() {
        WebElementUtils.waitUntilVisible(getDriver(), removeButtonInPublishers);
        WebElementUtils.clickAndUseJs(getDriver(), removeButtonInPublishers);
        getDriver().switchTo().alert().accept();
    }

    public void waitUntilPluginUninstalled() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 30); // timeout of 30 seconds
            wait.pollingEvery(Duration.ofSeconds(1)); // polling every 1 second
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[normalize-space(.)='Parasoft Findings']")));
        } catch (Exception e) {
            System.out.println("Plugin failed to uninstalled: " + e.getMessage());
        }
    }

    public void clickGetItFreeButton() {
        WebElementUtils.waitUntilVisible(getDriver(), getItFreeButtonInMarketplace);
        WebElementUtils.clickAndUseJs(getDriver(), getItFreeButtonInMarketplace);
    }

    public void clickSelectOrganizationSpinner() {
        WebElementUtils.waitUntilVisible(getDriver(), selectOrganizationSpinnerInMarketplace);
        WebElementUtils.clickAndUseJs(getDriver(), selectOrganizationSpinnerInMarketplace);
    }

    public void clickSelectOrganization() {
        WebElementUtils.waitUntilVisible(getDriver(), getDriver().findElement(By.xpath("//button[@aria-label='" + SeleniumTestConstants.ORGANIZATION + "']")));
        WebElementUtils.clickAndUseJs(getDriver(), getDriver().findElement(By.xpath("//button[@aria-label='" + SeleniumTestConstants.ORGANIZATION + "']")));
    }

    public void clickInstallButton() {
        WebElementUtils.waitUntilClickable(getDriver(), installButtonInMarketplace);
        WebElementUtils.clickAndUseJs(getDriver(), installButtonInMarketplace);
        WebElementUtils.waitUntilVisible(getDriver(), successfullyInstalledText);
    }
}

