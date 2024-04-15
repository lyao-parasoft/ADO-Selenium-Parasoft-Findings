package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class PipelineRunDetailsPage extends PageObject{
    @FindBy(css = "#__bolt-tab-sariftools-scans-build-tab .bolt-tab-text")
    private WebElement scansTab;

    @FindBy(css = "#__bolt-tab-ms-vss-test-web-build-test-results-tab .bolt-tab-text")
    private WebElement testsTab;

    @FindBy(css = "#__bolt-tab-codecoverage-tab .bolt-tab-text")
    private WebElement codeCoverageTab;

    @FindBy(xpath = "//div[@class='bolt-table-container flex-grow h-scroll-hidden']//a/td[3]//span")
    private WebElement jobStatusText;

    @FindBy(xpath = "//div[@class='bolt-accordion-section-content']")
    private WebElement testContent;

    @FindBy(xpath = "//div[@class='test-summary-card-row-small']/div[1]//div[2]")
    private WebElement totalTests;

    @FindBy(className = "external-content--iframe")
    private WebElement scansIframe;

    @FindBy(className = "code-coverage-summary-frame")
    private WebElement codeCoverageIframe;

    // static analysis
    @FindBy(xpath = "//span[@class='swcRunTitle']")
    private static WebElement reportTitle;

    @FindBy(xpath = "//div[@class='swcRowRule']/a")
    private WebElement theFirstRuleIdLink;

    @FindBy(xpath = "//tbody[@class='relative']/tr[4]//a")
    private WebElement theFirstFileLink;

    // unit test
    @FindBy(xpath = "(//div[@class='legend-cell data-count'])[1]")
    private WebElement passedTestsNumber;

    @FindBy(xpath = "(//div[@class='legend-cell data-count'])[2]")
    private WebElement failedTestsNumber;

    @FindBy(xpath = "(//div[@class='legend-cell data-count'])[3]")
    private WebElement othersTestsNumber;

    @FindBy(xpath = "//div[@class='failed-chart-total-count']")
    private WebElement newFailedTestsNumber;

    @FindBy(xpath = "(//div[@class='legend-cell data-count'])[5]")
    private WebElement existingFailedTestsNumber;

    @FindBy(className = "number-chart-large-font")
    private WebElement passPercentageOfUnitTests;

    // code coverage
    @FindBy(xpath = "/descendant::th[normalize-space(.)='Assemblies:']/../td")
    private WebElement assembliesNumberInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Classes:']/../td")
    private WebElement classesNumberInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Files:']/../td")
    private WebElement filesNumberInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Covered lines:']/../td")
    private WebElement coveredLinesInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Uncovered lines:']/../td")
    private WebElement uncoveredLinesInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Coverable lines:']/../td")
    private WebElement coverableLinesInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Total lines:']/../td")
    private WebElement totalLinesInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Line coverage:']/../td")
    private WebElement lineCoverageInCoverageTab;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Class:']/../td")
    private WebElement firstClassName;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='Assembly:']/../td")
    private WebElement assemblyInfoOfFirstClass;

    @FindBy(xpath = "/descendant::th[normalize-space(.)='File(s):']/../td")
    private WebElement fileInfoOfFirstClass;

    @FindBy(xpath = "//table[@class='overview table-fixed stripped']/tbody/tr[1]/th[1]")
    private WebElement packageNameOfFirstClass;

    @FindBy(xpath = "//table[@class='overview table-fixed stripped']/tbody/tr[2]/td[1]/a")
    private WebElement theFirstClassName;

    public PipelineRunDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getPipelineRunPageUrl() {
        return getDriver().getCurrentUrl();
    }

    public void clickScansTab() {
        WebElementUtils.waitUntilVisible(getDriver(),scansTab);
        WebElementUtils.clickAndUseJs(getDriver(), scansTab);
    }

    public void waitUntilJobFinished() {
        WebElementUtils.waitUntilTextsAppear(getDriver(), jobStatusText, new String[]{"Success", "Failed"});
    }

    public void clickTestsTab() {
        WebElementUtils.waitUntilVisible(getDriver(),testsTab);
        WebElementUtils.clickAndUseJs(getDriver(), testsTab);
    }

    public void clickCodeCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), codeCoverageTab);
        WebElementUtils.clickAndUseJs(getDriver(), codeCoverageTab);
    }

    public void switchToScansFrame() {
        WebElementUtils.waitUntilVisible(getDriver(), scansIframe);
        getDriver().switchTo().frame(scansIframe);
    }

    public void switchToCodeCoverageFrame() {
        WebElementUtils.waitUntilVisible(getDriver(), codeCoverageIframe);
        getDriver().switchTo().frame(codeCoverageIframe);
    }

    public static String getReportTitle() {
        WebElementUtils.waitUntilVisible(getDriver(), reportTitle);
        return reportTitle.getText();
    }

    public String getTheFirstRuleIdLinkText() {
        try{
            WebElementUtils.waitUntilVisible(getDriver(), theFirstRuleIdLink);
        }catch (Exception e){
            throw new RuntimeException("The first rule id link is not found.Please check the configuration of getting the first rule id from DTP in localsettings.properties file.");
        }
        return theFirstRuleIdLink.getText();
    }

    public String getTheFirstRuleDocUrl() {
        WebElementUtils.waitUntilVisible(getDriver(), theFirstRuleIdLink);
        return theFirstRuleIdLink.getAttribute("href");
    }

    public String getTheFirstFileUrl() {
        WebElementUtils.waitUntilVisible(getDriver(), theFirstFileLink);
        return theFirstFileLink.getAttribute("href");
    }

    public String getTheFirstFileLinkText() {
        WebElementUtils.waitUntilVisible(getDriver(), theFirstFileLink);
        return theFirstFileLink.getText();
    }

    public String getTotalTestsText() {
        WebElementUtils.waitUntilVisible(getDriver(), testContent);
        WebElementUtils.waitUntilVisible(getDriver(),  totalTests);
        return totalTests.getText();
    }

    public String getTotalTestsNumberText() {
        WebElementUtils.waitUntilVisible(getDriver(), testContent);
        WebElement totalTestsNumber = getDriver().findElement(By.xpath("(//div[@class='chart-value'])[1]"));
        return totalTestsNumber.getText();
    }

    public String getPassedTestsNumberText() {
        WebElementUtils.waitUntilVisible(getDriver(), passedTestsNumber);
        return passedTestsNumber.getText();
    }

    public String getFailedTestsNumberText() {
        WebElementUtils.waitUntilVisible(getDriver(),failedTestsNumber);
        return failedTestsNumber.getText();
    }

    public String getOthersTestsNumberText() {
        WebElementUtils.waitUntilVisible(getDriver(), othersTestsNumber);
        return othersTestsNumber.getText();
    }

    public String getNewFailedTestsNumberText() {
        WebElementUtils.waitUntilVisible(getDriver(), newFailedTestsNumber);
        return newFailedTestsNumber.getText();
    }

    public String getExistingFailedTestsNumberText() {
        WebElementUtils.waitUntilVisible(getDriver(), existingFailedTestsNumber);
        return existingFailedTestsNumber.getText();
    }

    public String getPassPercentageTextOfUnitTests() {
        WebElementUtils.waitUntilVisible(getDriver(), passPercentageOfUnitTests);
        return passPercentageOfUnitTests.getText();
    }

    public String getAssembliesNumberInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), assembliesNumberInCoverageTab);
        return assembliesNumberInCoverageTab.getText();
    }

    public String getClassesNumberInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), classesNumberInCoverageTab);
        return classesNumberInCoverageTab.getText();
    }

    public String getFilesNumberInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), filesNumberInCoverageTab);
        return filesNumberInCoverageTab.getText();
    }

    public String getCoveredLinesTextInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), coveredLinesInCoverageTab);
        return coveredLinesInCoverageTab.getText();
    }

    public String getUncoveredLinesTextInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), uncoveredLinesInCoverageTab);
        return uncoveredLinesInCoverageTab.getText();
    }

    public String getCoverableLinesTextInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), coverableLinesInCoverageTab);
        return coverableLinesInCoverageTab.getText();
    }

    public String getTotalLinesTextInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), totalLinesInCoverageTab);
        return totalLinesInCoverageTab.getText();
    }

    public String getLineCoverageTextInCoverageTab() {
        WebElementUtils.waitUntilVisible(getDriver(), lineCoverageInCoverageTab);
        return lineCoverageInCoverageTab.getText();
    }

    public String getFirstClassNameText() {
        WebElementUtils.waitUntilVisible(getDriver(), firstClassName);
        return firstClassName.getText();
    }

    public String getFirstAssemblyInfoText() {
        WebElementUtils.waitUntilVisible(getDriver(), assemblyInfoOfFirstClass);
        return assemblyInfoOfFirstClass.getText();
    }

    public String getFirstFileNameText() {
        WebElementUtils.waitUntilVisible(getDriver(), fileInfoOfFirstClass);
        return fileInfoOfFirstClass.getText();
    }

    public String getFirstPackageNameText() {
        WebElementUtils.waitUntilVisible(getDriver(), packageNameOfFirstClass);
        return packageNameOfFirstClass.getText();
    }

    public String getTheFirstClassNameText() {
        WebElementUtils.waitUntilVisible(getDriver(), theFirstClassName);
        return theFirstClassName.getText();
    }

    public void clickTheFirstClassNameLink() {
        WebElementUtils.waitUntilVisible(getDriver(), theFirstClassName);
        WebElementUtils.clickAndUseJs(getDriver(), theFirstClassName);
    }
}