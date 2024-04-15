package com.parasoft.findings.ado.testcases;

import com.parasoft.findings.ado.common.SeleniumTestConstants;
import com.parasoft.findings.ado.common.WebDriverFactory;
import com.parasoft.findings.ado.pages.PipelineRunDetailsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({BeforeAndAfterAllExtension.class})
public class CppStadProjectTests extends AbstractProjectsTests{
    private WebDriver driver;
    private static final String CPPTEST_STD_JOB_NAME = System.getProperty("cpptest.std.job.name");

    protected CppStadProjectTests() {
        super(new String[]{"reports/static/report.xml", "reports/unit/report.xml", "reports/unit/coverage.xml"});
    }

    @BeforeEach
    public void beforeTest() {
        driver = WebDriverFactory.getChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterTest() {
        deletePipeline(driver, CPPTEST_STD_JOB_NAME);
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void cppStdTestParasoftFindingsPlugin() {
        login(driver);
        createAndRunPipeline(driver, CPPTEST_STD_JOB_NAME, SeleniumTestConstants.SETTINGS_FILE_PATH);

        PipelineRunDetailsPage pipelineRunDetailsPage = new PipelineRunDetailsPage(driver);

        // get the pipeline run page url
        String pipelineRunPageUrl = waitUntilJobIsFinished(pipelineRunDetailsPage);

        // get the scans page url
        String scansPageUrl = switchToScansTab(driver,pipelineRunDetailsPage);
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("C/C++test"));
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("1175"));

        // assert the rule link text
        assertRuleDoc(driver, pipelineRunDetailsPage,false);

        driver.get(scansPageUrl);
        // assert the file link to the code repository
        assertCodeRep(driver);

        // assert the number of tests in the parsed unit test report
        driver.get(pipelineRunPageUrl);
        PipelineRunDetailsPage summaryPage = new PipelineRunDetailsPage(driver);
        summaryPage.clickTestsTab();
        try {
            assertEquals(summaryPage.getTotalTestsNumberText(), "5");
            assertEquals(summaryPage.getPassedTestsNumberText(), "4");
            assertEquals(summaryPage.getFailedTestsNumberText(), "1");
            assertEquals(summaryPage.getOthersTestsNumberText(), "0");
        } catch (Exception e) {
            assertEquals(summaryPage.getTotalTestsText(), "5 (4 Passed, 1 Failed, 0 Others)");
        } finally {
            assertEquals(summaryPage.getPassPercentageTextOfUnitTests(), "80%");
        }

        // assert the coverage data in the parsed coverage report
        summaryPage.clickCodeCoverageTab();
        summaryPage.switchToCodeCoverageFrame();
        int coverableLines = Integer.parseInt(summaryPage.getCoveredLinesTextInCoverageTab()) + Integer.parseInt(summaryPage.getUncoveredLinesTextInCoverageTab());
        assertEquals(summaryPage.getAssembliesNumberInCoverageTab(), "10");
        assertEquals(summaryPage.getClassesNumberInCoverageTab(), "38");
        assertEquals(summaryPage.getFilesNumberInCoverageTab(), "38");
        assertEquals(summaryPage.getCoverableLinesTextInCoverageTab(), String.valueOf(coverableLines));
        assertEquals(summaryPage.getTotalLinesTextInCoverageTab(), "36918");
        assertTrue(summaryPage.getLineCoverageTextInCoverageTab().contains("23.2%"));

        // assert the summary page of the fist class
        String firstPackageName = summaryPage.getFirstPackageNameText();
        String firstClassName = summaryPage.getTheFirstClassNameText();
        summaryPage.clickTheFirstClassNameLink();
        assertEquals(summaryPage.getFirstClassNameText(), firstClassName);
        assertEquals(summaryPage.getFirstAssemblyInfoText(), firstPackageName);
        assertEquals(summaryPage.getFirstFileNameText(), firstPackageName + "/" + firstClassName);
        assertEquals(summaryPage.getCoveredLinesTextInCoverageTab(), "0");
        assertEquals(summaryPage.getUncoveredLinesTextInCoverageTab(), "13");
        assertEquals(summaryPage.getCoverableLinesTextInCoverageTab(), "13");
        assertEquals(summaryPage.getTotalLinesTextInCoverageTab(), "33");
        assertTrue(summaryPage.getLineCoverageTextInCoverageTab().contains("0%"));
    }
}
