package com.parasoft.findings.ado.testcases;

import com.parasoft.findings.ado.common.SeleniumTestConstants;
import com.parasoft.findings.ado.pages.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


public abstract class AbstractProjectsTests {
    private final String[] reportPaths;

    protected AbstractProjectsTests(String[] reportPaths) {
        this.reportPaths = reportPaths;
    }

    public static void login(WebDriver driver) {
        driver.get("https://dev.azure.com/" + SeleniumTestConstants.ORGANIZATION);
        MicrosoftLoginPage microsoftLoginPage = new MicrosoftLoginPage(driver);
        microsoftLoginPage.setLoginAccountField(SeleniumTestConstants.LOGIN_ACCOUNT);
        microsoftLoginPage.clickNextButton();
        microsoftLoginPage.setLoginPasswdField(SeleniumTestConstants.LOGIN_PASSWORD);
        microsoftLoginPage.clickSignInButton();
        microsoftLoginPage.clickNoButtonAfterLogin();
    }

    public void createAndRunPipeline(WebDriver driver, String projectName, String settingsField) {
        driver.get("https://dev.azure.com/" + SeleniumTestConstants.ORGANIZATION + "/" + projectName + "/_build");
        CreatePipelinesPage createPipelinesPage = new CreatePipelinesPage(driver);
        createPipelinesPage.clickCreatePipelineButton();
        createPipelinesPage.clickUseTheClassicEditorLink();
        createPipelinesPage.clickSelectorAzureRepoGitAsRepoButton();
        createPipelinesPage.clickEmptyJobAsTemplateButton();
        createPipelinesPage.clickAgentPoolComboBox();
        createPipelinesPage.setDefaultAgentPool();

        // add Command Line task in pipeline
        createPipelinesPage.clickAddTaskButton();
        createPipelinesPage.setSearchTaskBox("Command line");;
        createPipelinesPage.clickAddSpecificTasksDetails("Command line");
        createPipelinesPage.clickTaskToEdit("Command Line Script");
        createPipelinesPage.setDisplayNameField("Report path updater");
        createPipelinesPage.setScriptOrResultsFilesFieldInPipelineTasks(getReportPathUpdaterCommand());

        // add Publish Parasoft Results task in pipeline
        createPipelinesPage.clickAddTaskButton();
        createPipelinesPage.setSearchTaskBox("Publish Parasoft Results");
        createPipelinesPage.clickRefreshButton();
        createPipelinesPage.clickAddSpecificTasksDetails("Publish Parasoft Results");
        createPipelinesPage.clickTaskToEdit("Publish Parasoft Results **/rep*.xml **/rep*.sarif **/coverage.xml");
        createPipelinesPage.setDisplayNameField("Analyze report");
        createPipelinesPage.setScriptOrResultsFilesFieldInPipelineTasks(getReportPathsAsString());
        createPipelinesPage.setSettingsField(settingsField);
        createPipelinesPage.clickSaveAndQueue();
    }

    public String waitUntilJobIsFinished(PipelineRunDetailsPage pipelineRunDetailsPage){
        pipelineRunDetailsPage.waitUntilJobFinished();
        return pipelineRunDetailsPage.getPipelineRunPageUrl();
    }

    public String switchToScansTab(WebDriver driver, PipelineRunDetailsPage pipelineRunDetailsPage){
        pipelineRunDetailsPage.clickScansTab();
        pipelineRunDetailsPage.switchToScansFrame();
        return driver.getCurrentUrl();
    }

    public void assertRuleDoc(WebDriver driver, PipelineRunDetailsPage pipelineRunDetailsPage, boolean isDottest){
        String ruleId = pipelineRunDetailsPage.getTheFirstRuleIdLinkText();
        driver.get(pipelineRunDetailsPage.getTheFirstRuleDocUrl());
        RuleDocPage ruleDocPage = new RuleDocPage(driver);
        String ruleText;
        if(isDottest){
            ruleText = ruleDocPage.getDottestRuleText();
        }else {
            ruleText = ruleDocPage.getJtestOrCppRuleText();
        }
        assertTrue(ruleText.contains(ruleId));
    }
    public void assertCodeRep(WebDriver driver)  {
        PipelineRunDetailsPage scansPage = new PipelineRunDetailsPage(driver);
        scansPage.switchToScansFrame();
        String fileLinkText = scansPage.getTheFirstFileLinkText();
        driver.get(scansPage.getTheFirstFileUrl());
        RepositoryPage sourceCodeRepositoryPage = new RepositoryPage(driver);
        assertTrue(fileLinkText.contains(sourceCodeRepositoryPage.getFileNameInRepository()));
    }

    public void deletePipeline(WebDriver driver, String PipelineName) {
        driver.get("https://dev.azure.com/" + SeleniumTestConstants.ORGANIZATION + "/" + PipelineName + "/_build");
        PipelinesPage pipelinespage = new PipelinesPage(driver);
        String PipelinesName = pipelinespage.getFirstPipelineName();
        pipelinespage.clickFirstPipelineName();
        driver.get(driver.getCurrentUrl());
        DeletePipelinePage deletepipelinepage = new DeletePipelinePage(driver);
        deletepipelinepage.clickOptions();
        deletepipelinepage.clickDeleteOption();
        deletepipelinepage.setBoltTextFieldInputField(PipelinesName);
        deletepipelinepage.clickDeleteButton();
    }

    private String getReportPathUpdaterCommand() {
        StringBuilder command = new StringBuilder();
        command.append("SETLOCAL EnableDelayedExpansion\n")
                .append("SET CURRENT_PATH=%CD%\n");
        for (String reportPath : reportPaths) {
            command.append("powershell -Command \"(Get-Content ")
                .append(reportPath)
                .append(").replace('${ado_working_directory}', '!CURRENT_PATH!').replace('\\', '/') | Set-Content ")
                .append(reportPath)
                .append(" -Encoding UTF8\"\n");
        }
        return command.toString();
    }

    private String getReportPathsAsString() {
        StringBuilder reportPathsAsString = new StringBuilder();
        for (String reportPath : reportPaths) {
            reportPathsAsString.append(reportPath).append("\n");
        }
        return reportPathsAsString.toString();
    }
}
