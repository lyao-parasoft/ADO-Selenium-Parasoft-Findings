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
public class CppProProjectTests extends AbstractProjectsTests{
    private WebDriver driver;
    private static final  String CPPTEST_PRO_PROJECT_NAME =System.getProperty("cpptest.pro.job.name");

    protected CppProProjectTests() {
        super(new String[]{"reports/static/report.xml", "reports/unit/report.xml"});
    }

    @BeforeEach
    public void beforeTest() {
        driver = WebDriverFactory.getChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void afterTest() {
        deletePipeline(driver, CPPTEST_PRO_PROJECT_NAME);
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void cppProTestParasoftFindingsPlugin() {
        login(driver);
        createAndRunPipeline(driver, CPPTEST_PRO_PROJECT_NAME, SeleniumTestConstants.SETTINGS_FILE_PATH);

        PipelineRunDetailsPage pipelineRunDetailsPage = new PipelineRunDetailsPage(driver);
        // get the pipeline run page url
        String pipelineRunPageUrl = waitUntilJobIsFinished(pipelineRunDetailsPage);
        // get the scans page url
        String scansPageUrl = switchToScansTab(driver,pipelineRunDetailsPage);

        // assert the number of violations in the parsed static analysis report;
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("C++test"));
        assertTrue(PipelineRunDetailsPage.getReportTitle().contains("2802"));

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
            assertEquals(summaryPage.getTotalTestsNumberText(), "162");
            assertEquals(summaryPage.getPassedTestsNumberText(), "138");
            assertEquals(summaryPage.getFailedTestsNumberText(), "24");
            assertEquals(summaryPage.getOthersTestsNumberText(), "0");
        } catch (Exception e) {
            assertEquals(summaryPage.getTotalTestsText(), "162 (138 Passed, 24 Failed, 0 Others)");
        } finally {
            assertEquals(summaryPage.getPassPercentageTextOfUnitTests(), "85.18%");
        }
    }
}
