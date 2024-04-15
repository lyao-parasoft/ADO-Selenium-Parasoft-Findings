package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


public class MicrosoftLoginPage extends PageObject{
    @FindBy(name = "loginfmt")
    private WebElement loginAccountField;

    @FindBy(css = "input[value='Next']")
    private WebElement loginNextButton;

    @FindBy(name = "passwd")
    private WebElement loginPasswdField;

    @FindBy(xpath = "//*[@id='idSIButton9']")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id='idBtn_Back' or @id='declineButton']")
    private WebElement noButtonAfterLogin;

    public MicrosoftLoginPage(WebDriver driver) {
        super(driver);
    }

    public void setLoginAccountField(String text) {
        WebElementUtils.waitUntilVisible(getDriver(), loginAccountField);
        loginAccountField.sendKeys(text);
    }

    public void clickNextButton() {
        WebElementUtils.waitUntilVisible(getDriver(), loginNextButton);
        WebElementUtils.clickAndUseJs(getDriver(), loginNextButton);
    }

    public void setLoginPasswdField(String text) {
        WebElementUtils.waitUntilVisible(getDriver(), loginPasswdField);
        loginPasswdField.sendKeys(text);
    }

    public void clickSignInButton() {
        WebElementUtils.waitUntilVisible(getDriver(), loginButton);
        WebElementUtils.clickAndUseJs(getDriver(), loginButton);
    }

    public void clickNoButtonAfterLogin() {
        WebElementUtils.waitUntilVisible(getDriver(), noButtonAfterLogin);
        WebElementUtils.clickAndUseJs(getDriver(), noButtonAfterLogin);
    }
}
