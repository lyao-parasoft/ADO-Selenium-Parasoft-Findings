package com.parasoft.findings.ado.testcases;

import com.parasoft.findings.ado.common.*;
import com.parasoft.findings.ado.pages.PipelineRunDetailsPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({BeforeAndAfterAllExtension.class})
public class JtestProjectTests extends AbstractProjectsTests{
    private WebDriver driver;
    private static final String JTEST_JOB_NAME = "JtestProject";

    protected JtestProjectTests() {
        super(new String[]{"reports/static/report.xml", "reports/unit/report.xml", "reports/unit/coverage.xml"});
    }

    @BeforeEach
    public void beforeTest() {
        driver = WebDriverFactory.getChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterTest() {
        deletePipeline(driver, JTEST_JOB_NAME);
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void jtestParasoftFindingsPlugin() {
        login(driver);
        createAndRunPipeline(driver, JTEST_JOB_NAME, SeleniumTestConstants.SETTINGS_FILE_PATH);

        PipelineRunDetailsPage pipelineRunDetailsPage = new PipelineRunDetailsPage(driver);
        // get the pipeline run page url
        String pipelineRunPageUrl = waitUntilJobIsFinished(pipelineRunDetailsPage);
        // get the scans page url
        String scansPageUrl = switchToScansTab(driver,pipelineRunDetailsPage);
        // assert the number of violations in the parsed static analysis report;
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("Jtest"));
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("376"));

        // assert the rule link text
        assertRuleDoc(driver, pipelineRunDetailsPage,false);
        driver.get(scansPageUrl);
        // assert the file link to the code repository
        assertCodeRep(driver);

        // assert the number of tests in the parsed unit test report;
        driver.get(pipelineRunPageUrl);
        PipelineRunDetailsPage summaryPage = new PipelineRunDetailsPage(driver);
        summaryPage.clickTestsTab();
        try {
            assertEquals(summaryPage.getTotalTestsNumberText(), "8");
            assertEquals(summaryPage.getPassedTestsNumberText(), "8");
            assertEquals(summaryPage.getFailedTestsNumberText(), "0");
            assertEquals(summaryPage.getOthersTestsNumberText(), "0");
        } catch (Exception e) {
            assertEquals(summaryPage.getTotalTestsText(), "8 (8 Passed, 0 Failed, 0 Others)");
        }finally {
            assertEquals(summaryPage.getPassPercentageTextOfUnitTests(), "100%");
        }

        // assert the coverage data in the parsed coverage report
        summaryPage.clickCodeCoverageTab();
        summaryPage.switchToCodeCoverageFrame();
        int coverableLines = Integer.parseInt(summaryPage.getCoveredLinesTextInCoverageTab()) + Integer.parseInt(summaryPage.getUncoveredLinesTextInCoverageTab());
        assertEquals(summaryPage.getAssembliesNumberInCoverageTab(), "6");
        assertEquals(summaryPage.getClassesNumberInCoverageTab(), "10");
        assertEquals(summaryPage.getFilesNumberInCoverageTab(), "6");
        assertEquals(summaryPage.getCoverableLinesTextInCoverageTab(), String.valueOf(coverableLines));
        assertEquals(summaryPage.getTotalLinesTextInCoverageTab(), "107");
        assertTrue(summaryPage.getLineCoverageTextInCoverageTab().contains("63.6%"));

        // assert the summary page
        String firstPackageName = summaryPage.getFirstPackageNameText();
        String firstClassName = summaryPage.getTheFirstClassNameText();
        summaryPage.clickTheFirstClassNameLink();
        assertEquals(summaryPage.getFirstClassNameText(), firstClassName);
        assertEquals(summaryPage.getFirstAssemblyInfoText(), firstPackageName);
        assertTrue(summaryPage.getFirstFileNameText().contains(firstClassName));
        assertEquals(summaryPage.getCoveredLinesTextInCoverageTab(), "5");
        assertEquals(summaryPage.getUncoveredLinesTextInCoverageTab(), "0");
        assertEquals(summaryPage.getCoverableLinesTextInCoverageTab(), "5");
        assertEquals(summaryPage.getTotalLinesTextInCoverageTab(), "27");
        assertTrue(summaryPage.getLineCoverageTextInCoverageTab().contains("100%"));
    }
}
