package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RuleDocPage extends PageObject{
    @FindBy(xpath = "//body/strong[1]")
    private WebElement jtestOrCppRuleText;

    @FindBy(xpath = "//*[@id='TitleRow']/h1")
    private WebElement dottestRuleText;

    public RuleDocPage(WebDriver driver) {
        super(driver);
    }

    public String getJtestOrCppRuleText() {
        WebElementUtils.waitUntilVisible(getDriver(), jtestOrCppRuleText);
        return jtestOrCppRuleText.getText();
    }

    public String getDottestRuleText() {
        WebElementUtils.waitUntilVisible(getDriver(), dottestRuleText);
        return dottestRuleText.getText();
    }
}
