package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class DeletePipelinePage extends PageObject {
    @FindBy(xpath = "//button[@aria-label='More actions']")
    private WebElement pipelineOptions;

    @FindBy(xpath = "/descendant::div[normalize-space(.)='Delete']")
    private WebElement deleteOption;

    @FindBy(xpath = "//*[@class='delete-definition-name bolt-textfield-input flex-grow']")
    private WebElement textInputField;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Delete']")
    private WebElement checkDeleteButton;

    public DeletePipelinePage(WebDriver driver) {
        super(driver);
    }

    public void clickOptions() {
        WebElementUtils.waitUntilClickable(getDriver(), pipelineOptions);
        pipelineOptions.click();
    }

    public void clickDeleteOption() {
        WebElementUtils.waitUntilClickable(getDriver(),deleteOption);
        WebElementUtils.clickAndUseJs(getDriver(), deleteOption);
    }

    public void setBoltTextFieldInputField(String text) {
        WebElementUtils.waitUntilVisible(getDriver(),textInputField);
        textInputField.sendKeys(text);
    }

    public void clickDeleteButton() {
        WebElementUtils.waitUntilClickable(getDriver(),checkDeleteButton);
        WebElementUtils.clickAndUseJs(getDriver(), checkDeleteButton);
    }
}