package com.parasoft.findings.ado.testcases;

import com.parasoft.findings.ado.common.*;
import com.parasoft.findings.ado.pages.PipelineRunDetailsPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith({BeforeAndAfterAllExtension.class})
public class DottestProjectTests extends AbstractProjectsTests{

    private WebDriver driver;
    private static final String DOTTEST_JOB_NAME = System.getProperty("dottest.job.name");

    protected DottestProjectTests() {
        super(new String[]{"reports/static/report.xml", "reports/unit/report.xml", "reports/unit/coverage.xml"});
    }

    @BeforeEach
    public void beforeTest() {
        driver = WebDriverFactory.getChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterTest() {
        deletePipeline(driver, DOTTEST_JOB_NAME);
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void dotTestParasoftFindingsPlugin() {
        login(driver);
        createAndRunPipeline(driver, DOTTEST_JOB_NAME, SeleniumTestConstants.SETTINGS_FILE_PATH);

        PipelineRunDetailsPage pipelineRunDetailsPage = new PipelineRunDetailsPage(driver);
        // get the pipeline run page url
        String pipelineRunPageUrl = waitUntilJobIsFinished(pipelineRunDetailsPage);
        // get the scans page url
        String scansPageUrl = switchToScansTab(driver,pipelineRunDetailsPage);

        // assert the number of violations in the parsed static analysis report
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("dotTEST"));
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("1546"));

        // assert the rule link text
        assertRuleDoc(driver, pipelineRunDetailsPage,true);

        driver.get(scansPageUrl);
        // assert the file link to the code repository
        assertCodeRep(driver);

        // assert the number of tests in the parsed unit test report
        driver.get(pipelineRunPageUrl);
        PipelineRunDetailsPage summaryPage = new PipelineRunDetailsPage(driver);
        summaryPage.clickTestsTab();
        try {
            assertEquals(summaryPage.getTotalTestsNumberText(), "30");
            assertEquals(summaryPage.getPassedTestsNumberText(), "19");
            assertEquals(summaryPage.getFailedTestsNumberText(), "11");
            assertEquals(summaryPage.getOthersTestsNumberText(), "0");
            assertEquals(summaryPage.getNewFailedTestsNumberText(), "11");
            assertEquals(summaryPage.getExistingFailedTestsNumberText(), "0");
        } catch (Exception e) {
            assertEquals(summaryPage.getTotalTestsText(), "30 (19 Passed, 11 Failed, 0 Others)");
            assertEquals(summaryPage.getFailedTestsNumberText(), "11 (11 New, 0 Existing)");
        } finally {
            assertEquals(summaryPage.getPassPercentageTextOfUnitTests(), "63.33%");
        }

        // assert the coverage data in the parsed coverage report
        summaryPage.clickCodeCoverageTab();
        summaryPage.switchToCodeCoverageFrame();
        int coverableLines = Integer.parseInt(summaryPage.getCoveredLinesTextInCoverageTab()) + Integer.parseInt(summaryPage.getUncoveredLinesTextInCoverageTab());
        assertEquals(summaryPage.getAssembliesNumberInCoverageTab(), "6");
        assertEquals(summaryPage.getClassesNumberInCoverageTab(), "27");
        assertEquals(summaryPage.getFilesNumberInCoverageTab(), "27");
        assertEquals(summaryPage.getCoverableLinesTextInCoverageTab(), String.valueOf(coverableLines));
        assertEquals(summaryPage.getTotalLinesTextInCoverageTab(), "1393");
        assertTrue(summaryPage.getLineCoverageTextInCoverageTab().contains("35.8%"));

        // assert the summary page
        String firstPackageName = summaryPage.getFirstPackageNameText();
        String firstClassName = summaryPage.getTheFirstClassNameText();
        summaryPage.clickTheFirstClassNameLink();
        assertEquals(summaryPage.getFirstClassNameText(), firstClassName);
        assertEquals(summaryPage.getFirstAssemblyInfoText(), firstPackageName);
        assertTrue(summaryPage.getFirstFileNameText().contains(firstClassName));
        assertEquals(summaryPage.getCoveredLinesTextInCoverageTab(), "42");
        assertEquals(summaryPage.getUncoveredLinesTextInCoverageTab(), "18");
        assertEquals(summaryPage.getCoverableLinesTextInCoverageTab(), "60");
        assertEquals(summaryPage.getTotalLinesTextInCoverageTab(), "90");
        assertTrue(summaryPage.getLineCoverageTextInCoverageTab().contains("70%"));
    }
}
