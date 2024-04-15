package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


public class PipelinesPage extends PageObject{
    @FindBy(xpath = "//*[@id='skip-to-main-content']//a[1]/td[2]//span/span")
    private WebElement firstPipelineName;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Edit']")
    private WebElement editButtonInPipelines;
    public PipelinesPage(WebDriver driver) {
        super(driver);
    }
    public String getFirstPipelineName() {
        WebElementUtils.waitUntilVisible(getDriver(), firstPipelineName);
        return firstPipelineName.getText();
    }

    public void clickFirstPipelineName() {
        WebElementUtils.waitUntilClickable(getDriver(), firstPipelineName);
        WebElementUtils.clickAndUseJs(getDriver(), firstPipelineName);
        WebElementUtils.waitUntilVisible(getDriver(), editButtonInPipelines);
    }
}


