package com.parasoft.findings.ado.testcases;

import com.parasoft.findings.ado.common.SeleniumTestConstants;
import com.parasoft.findings.ado.common.WebDriverFactory;
import com.parasoft.findings.ado.pages.InstallOrUninstallPluginPage;
import com.parasoft.findings.ado.pages.SettingsPage;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class BeforeAndAfterAllExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    private static final String AZURE_URL = "https://dev.azure.com/";
    private static final String SETTINGS_EXTENSIONS = AZURE_URL + SeleniumTestConstants.ORGANIZATION + "/_settings/extensions";
    private static final String SETTINGS_PIPELINESETTINGS = AZURE_URL + SeleniumTestConstants.ORGANIZATION + "/_settings/pipelinessettings";
    public static final String MANAGE_PUBLISHERS_EXTENSIONS_URL = "https://marketplace.visualstudio.com/manage/publishers/" + SeleniumTestConstants.AZURE_PUBLISHER;
    public static final String PARASOFT_FINDINGS = "Parasoft Findings";
    private WebDriver driver;
    private static boolean started = false;

    // install plugin before all tests.
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if (!started) {
            extensionContext.getRoot().getStore(GLOBAL).put("BeforeAndAfterAllExtension", this);
            started = true;
            driver = WebDriverFactory.getChromeDriver();
            driver.manage().window().maximize();

            // open classic format project
            driver.get(SETTINGS_PIPELINESETTINGS);
            SettingsPage settingsPipelineSettingsPage = new SettingsPage(driver);
            AbstractProjectsTests.login(driver);
          	driver.get(SETTINGS_PIPELINESETTINGS);
            if ("On".equals(SettingsPage.getPipelineClassicValue())) {
                settingsPipelineSettingsPage.setOpenClassicSwitch();
            }

            driver.get(SETTINGS_EXTENSIONS);
            SettingsPage settingsExtensionsPage = new SettingsPage(driver);
            settingsExtensionsPage.uninstallExistingPlugins();
            settingsExtensionsPage.clickSharedTab();
            settingsExtensionsPage.clickParasoftFindingsPlugin();

            driver.get(settingsExtensionsPage.getMarketplaceLink());
            InstallOrUninstallPluginPage getPluginPage = new InstallOrUninstallPluginPage(driver);
            getPluginPage.clickGetItFreeButton();
            getPluginPage.clickSelectOrganizationSpinner();
            getPluginPage.clickSelectOrganization();
            getPluginPage.clickInstallButton();

            driver.get(SETTINGS_EXTENSIONS);
            SettingsPage returnToSettingsExtensionsPage = new SettingsPage(driver);
            assertEquals(returnToSettingsExtensionsPage.getParasoftFindingsPluginText(), PARASOFT_FINDINGS);
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // uninstall plugin after all tests.
    @Override
    public void close() {
        driver = WebDriverFactory.getChromeDriver();
        driver.manage().window().maximize();

        driver.get(MANAGE_PUBLISHERS_EXTENSIONS_URL);
        InstallOrUninstallPluginPage publishersPage = new InstallOrUninstallPluginPage(driver);
        AbstractProjectsTests.login(driver);
        driver.get(MANAGE_PUBLISHERS_EXTENSIONS_URL);
        String currentPublishersUrl = driver.getCurrentUrl();
        if (!currentPublishersUrl.equals(MANAGE_PUBLISHERS_EXTENSIONS_URL)) {
            driver.get(MANAGE_PUBLISHERS_EXTENSIONS_URL);
        }
        publishersPage.judgePluginExistAndUninstall();

     	driver.get(SETTINGS_EXTENSIONS);
        SettingsPage settingsExtensionsPage = new SettingsPage(driver);
        String currentSettingsUrl = driver.getCurrentUrl();
        if (!currentSettingsUrl.equals(SETTINGS_EXTENSIONS)) {
            driver.get(SETTINGS_EXTENSIONS);
        }
        settingsExtensionsPage.waitingForPageLoading();
        Exception exception = null;
        try {
            driver.findElement(By.xpath("/descendant::span[normalize-space(.)='" + PARASOFT_FINDINGS + "']"));
        } catch (NoSuchElementException e) {
            exception = e;
        } finally {
            assertNotNull(exception);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
