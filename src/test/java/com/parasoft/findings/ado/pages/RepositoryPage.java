package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RepositoryPage extends PageObject{
    @FindBy(className = "bolt-header-title")
    private WebElement fileNameInRepository;

    public RepositoryPage(WebDriver driver) {
        super(driver);
    }
    public String getFileNameInRepository() {
        WebElementUtils.waitUntilVisible(getDriver(), fileNameInRepository);
        return fileNameInRepository.getText();
    }
}
