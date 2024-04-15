package com.parasoft.findings.ado.pages;

import com.parasoft.findings.ado.common.WebElementUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;


public class CreatePipelinesPage extends PageObject{
    @FindBy(xpath = "//*[@id='skip-to-main-content']/div/div[1]/div/div[1]")
    private WebElement useForCreatePipelinesPageLoading;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Create Pipeline']")
    private WebElement createPipelineButtonInPipelines;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='New pipeline']")
    private WebElement createNewPipelineButtonInPipelines;

    @FindBy(linkText = "Use the classic editor")
    private WebElement useTheClassicEditorLink;

    @FindBy(xpath = "//div[@class='repo-selector-section']//span[@class='selected-item-text']")
    private WebElement selectorAzureRepoGitAsRepo;

    @FindBy(className = "ci-getting-started-continue-button")
    private WebElement continueButtonInSelectRepo;

    @FindBy(className = "empty-process-button")
    private WebElement emptyJobAsTemplateButton;

    @FindBy(css = ".agent-queue-drop-down > .ms-ComboBox > .ms-ComboBox-CaretDown-button")
    private WebElement agentPoolCombobox;

    @FindBy(xpath = "/descendant::div[normalize-space(.)='Default']")
    private WebElement defaultAgentPool;

    @FindBy(xpath = "/descendant::i[normalize-space(.)='']")
    private WebElement addTasksButton;

    @FindBy(className = "ms-SearchBox-field")
    private WebElement searchTaskBox;

    @FindBy(xpath = "/descendant::div[normalize-space(.)='Refresh']")
    private WebElement refreshButton;

    @FindBy(className = "dtc-task-details")
    private WebElement specificTasks;

    @FindBy(xpath = "//div[@class='dtc-task-details']/div/div/div[1]")
    private WebElement specificTasksName;

    @FindBy(xpath = "//div[@class='dtc-task-details']//button")
    private WebElement addSpecificTasksButton;

    @FindBy(xpath = "/descendant::label[normalize-space(.)='Display name']/../..//input")
    private WebElement displayNameFieldInPipelineTasks;

    @FindBy(xpath = "//div[@class='task-tab-right-section rightPane']//textarea")
    private WebElement scriptOrResultsFilesFieldInPipelineTasks;

    @FindBy(xpath = "(//div[@class='task-tab-right-section rightPane']//textarea)[4]")
    private WebElement settingsFieldInPublishTestResultsTask;

    @FindBy(name = "Save & queue")
    private WebElement selectSaveAndQueueButton;

    @FindBy(className = "ms-ContextualMenu-itemText")
    private WebElement savePipelineSection;

    @FindBy(xpath = "/descendant::span[normalize-space(.)='Save and run']")
    private WebElement saveAndRunButton;

    public CreatePipelinesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * If this element does not exist on the page, then the createFirstPipelineText.isDisplayed() will have an error during execution,
     * so I used this method to operate it.
     */
    public void clickCreatePipelineButton() {
        WebElementUtils.waitUntilVisible(getDriver(), useForCreatePipelinesPageLoading);
        By pipelineTextLocator = By.xpath("//div[contains(@class, 'vss-ZeroDataItem--primary') and contains(text(), 'Create your first Pipeline')]");
        List<WebElement> elements = getDriver().findElements(pipelineTextLocator);
        if (!elements.isEmpty() && elements.get(0).isDisplayed()){
            WebElementUtils.waitUntilClickable(getDriver(), createPipelineButtonInPipelines);
            WebElementUtils.clickAndUseJs(getDriver(), createPipelineButtonInPipelines);
        } else {
            WebElementUtils.waitUntilClickable(getDriver(), createNewPipelineButtonInPipelines);
            WebElementUtils.clickAndUseJs(getDriver(), createNewPipelineButtonInPipelines);
        }
    }

    public void clickUseTheClassicEditorLink() {
        WebElementUtils.waitUntilVisible(getDriver(), useTheClassicEditorLink);
        WebElementUtils.clickAndUseJs(getDriver(), useTheClassicEditorLink);
    }

    public void clickSelectorAzureRepoGitAsRepoButton() {
        WebElementUtils.waitUntilVisible(getDriver(), selectorAzureRepoGitAsRepo);
        WebElementUtils.clickAndUseJs(getDriver(), continueButtonInSelectRepo);
    }

    public void clickEmptyJobAsTemplateButton() {
        WebElementUtils.waitUntilClickable(getDriver(), emptyJobAsTemplateButton);
        WebElementUtils.clickAndUseJs(getDriver(), emptyJobAsTemplateButton);
    }

    public void clickAgentPoolComboBox() {
        WebElementUtils.waitUntilClickable(getDriver(), agentPoolCombobox);
        WebElementUtils.clickAndUseJs(getDriver(), agentPoolCombobox);
    }

    public void setDefaultAgentPool() {
        WebElementUtils.waitUntilClickable(getDriver(), defaultAgentPool);
        WebElementUtils.clickAndUseJs(getDriver(), defaultAgentPool);
    }

    public void clickAddTaskButton() {
        WebElementUtils.waitUntilClickable(getDriver(), addTasksButton);
        WebElementUtils.clickAndUseJs(getDriver(), addTasksButton);
    }

    public void setSearchTaskBox(String text) {
        WebElementUtils.waitUntilClickable(getDriver(), searchTaskBox);
        searchTaskBox.sendKeys(text);
        // need to wait for the page to load the task which is searched. This is not a good way, but i cannot find a better way to do it.
        WebElementUtils.waitAndLoad(500);
    }
    public void clickRefreshButton() {
        WebElementUtils.waitUntilClickable(getDriver(), refreshButton).click();
    }
    public void clickAddSpecificTasksDetails(String taskName) {
        String text = WebElementUtils.waitUntilVisible(getDriver(),specificTasksName).getText();
        if (Objects.equals(text, taskName)){
            WebElementUtils.waitUntilClickable(getDriver(),specificTasks).click();
            WebElementUtils.waitUntilVisible(getDriver(),addSpecificTasksButton);
            WebElementUtils.clickAndUseJs(getDriver(), addSpecificTasksButton);
        }
    }

    public void clickTaskToEdit(String taskName) {
        WebElement taskInPipeline = getDriver().findElement(By.xpath("(//*[text()='" + taskName + "'])[1]"));
        WebElementUtils.waitUntilClickable(getDriver(), taskInPipeline).click();
    }

    public void setDisplayNameField(String text) {
        WebElementUtils.waitUntilClickable(getDriver(), displayNameFieldInPipelineTasks);
        displayNameFieldInPipelineTasks.sendKeys(Keys.CONTROL, "a");
        displayNameFieldInPipelineTasks.sendKeys(text);
    }

    public void setScriptOrResultsFilesFieldInPipelineTasks(String text) {
        WebElementUtils.waitUntilClickable(getDriver(), scriptOrResultsFilesFieldInPipelineTasks);
        scriptOrResultsFilesFieldInPipelineTasks.sendKeys(Keys.CONTROL, "a");
        scriptOrResultsFilesFieldInPipelineTasks.sendKeys(text);
    }

    /**
     * After entering the settings file name, I immediately performed a save&queue operation,
     * which resulted in the value not being stored in the actual form, so I added a time;
     */
    public void setSettingsField(String text) {
        WebElementUtils.waitUntilClickable(getDriver(), settingsFieldInPublishTestResultsTask);
        settingsFieldInPublishTestResultsTask.sendKeys(text);
        WebElementUtils.waitAndLoad(500);
    }

    public void clickSaveAndQueue() {
        WebElementUtils.waitUntilClickable(getDriver(), selectSaveAndQueueButton).click();
        WebElementUtils.waitUntilClickable(getDriver(), savePipelineSection).click();
        WebElementUtils.waitUntilClickable(getDriver(), saveAndRunButton);
        WebElementUtils.clickAndUseJs(getDriver(), saveAndRunButton);
    }
}
