package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SettingsPage extends PageObject{

    @FindBy(id = "__bolt-uninstall")
    private WebElement uninstallButtonInSettings;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Continue with uninstall']")
    private WebElement continueWithUninstallButton;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Shared']")
    private WebElement sharedTabInExtensions;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Parasoft Findings']")
    private WebElement parasoftFindingsPlugin;

    @FindBy(xpath = "//div[contains(@aria-label,'Disable creation of classic build pipelines')]/following-sibling::div")
    private static WebElement classicPipelineValueInSettings;

    @FindBy(xpath ="//div[contains(@aria-label,'Disable creation of classic build pipelines')]")
    private WebElement openClassicSwitchInSettings;

    @FindBy(linkText = "Marketplace")
    private WebElement marketplaceLink;

    private static final String PARASOFT_FINDINGS = "Parasoft Findings";

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public void waitingForPageLoading() {
        WebElementUtils.waitUntilVisible(getDriver(), sharedTabInExtensions);
    }

    public void uninstallExistingPlugins() {
        WebElement plugin;
        try {
            plugin = getDriver().findElement(By.xpath("/descendant::span[normalize-space(.)='"+ PARASOFT_FINDINGS +"']"));
        } catch (NoSuchElementException e) {
            plugin = null;
        }
        if (plugin == null) {
            return;
        }
        WebElementUtils.clickAndUseJs(getDriver(), plugin);
        WebElementUtils.waitUntilClickable(getDriver(), uninstallButtonInSettings);
        WebElementUtils.clickAndUseJs(getDriver(), uninstallButtonInSettings);
        WebElementUtils.waitUntilClickable(getDriver(), continueWithUninstallButton);
        WebElementUtils.clickAndUseJs(getDriver(), continueWithUninstallButton);
        waitUntilPluginUninstalled();
    }

    private void waitUntilPluginUninstalled() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 20); // Set timeout to 20 seconds
        By locator = By.xpath("//span[normalize-space(.)='" + PARASOFT_FINDINGS + "']");// Wait for the element to be visible (plugin is still present)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void clickSharedTab() {
        WebElementUtils.waitUntilVisible(getDriver(), sharedTabInExtensions).click();
    }

    public void clickParasoftFindingsPlugin() {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 5000; // 6 minutes in milliseconds
        boolean elementFound = false;
        while (System.currentTimeMillis() < endTime) {
            try {
                getDriver().findElement(By.xpath("/descendant::span[normalize-space(.)='Parasoft Findings']"));
                elementFound = true; // Element found
                break;
            } catch (Exception e) {
                WebElementUtils.waitAndLoad(5000);
                getDriver().navigate().refresh();
            }
        }
        if (!elementFound) {
            throw new RuntimeException("Plugin not found in shared tab after 6 minutes.");
        }else{
            WebElementUtils.waitUntilVisible(getDriver(), parasoftFindingsPlugin);
            WebElementUtils.clickAndUseJs(getDriver(), parasoftFindingsPlugin);
        }
    }

    public String getParasoftFindingsPluginText() {
        WebElementUtils.waitUntilVisible(getDriver(), sharedTabInExtensions);
        WebElementUtils.waitUntilTextAppear(getDriver(), parasoftFindingsPlugin, parasoftFindingsPlugin.getText());
        return parasoftFindingsPlugin.getText();
    }

    public String getMarketplaceLink() {
        WebElementUtils.waitUntilVisible(getDriver(), marketplaceLink);
        return marketplaceLink.getAttribute("href");
    }
    public static String getPipelineClassicValue(){
        WebElementUtils.waitUntilVisible(getDriver(),classicPipelineValueInSettings);
        WebElementUtils.waitUntilTextAppear(getDriver(),classicPipelineValueInSettings,classicPipelineValueInSettings.getText());
        return classicPipelineValueInSettings.getText();
    }
    public void setOpenClassicSwitch() {
        WebElementUtils.waitUntilVisible(getDriver(),openClassicSwitchInSettings);
        openClassicSwitchInSettings.click();
        WebElementUtils.waitUntilTextAppear(getDriver(),classicPipelineValueInSettings,"Off");
    }
}
