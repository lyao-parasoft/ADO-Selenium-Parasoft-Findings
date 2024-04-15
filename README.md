## Description
The selenium tests are used for testing the Parasoft Findings Azure plugin. All test cases are running on **Chrome** and **Windows** currently.
#### Prerequisites for selenium tests
* Install the `SARIF SAST Scans Tab` plugin on Azure.
* Create publisher,  organization and Microsoft account.
* If the project is run manually, it is necessary to manually upload the parasoft-findings plugin, and the publisher name during the plugin construction process should be consistent with the created publisher.
* If a single test is manually run each time, the plugin will be uninstalled after completing the test. After that, if you want to run the next test, you will need to manually upload the plugin again.
* Create four projects in the organization according to [this](#prerequisites-for-tested-projects). The project name needs to be fixed.(The project names are the following four)
* [Azure agent](https://learn.microsoft.com/en-us/azure/devops/pipelines/agents/windows-agent?view=azure-devops) needs to be installed and started locally.
* The organization information about logging into ADO in the SeleniumTestConstants class and the project names for the four tests should correspond to those on ADO.


## Prerequisites for tested projects
* These projects are used for Selenic-tests, if you want to access these projects on this ADO organization, you may need to ask the IT in chengdu to get the account and password.
* For [CppStdProject](https://parasoft-cd-devel@dev.azure.com/parasoft-cd-devel/CppStdProject/_git/CppStdProject) project
* For [JtestProject](https://parasoft-cd-devel@dev.azure.com/parasoft-cd-devel/JtestProject/_git/JtestProject) project
* For [dotTESTProject](https://parasoft-cd-devel@dev.azure.com/parasoft-cd-devel/CppProProject/_git/CppProProject) project
* For [CppProProject](https://parasoft-cd-devel@dev.azure.com/parasoft-cd-devel/CppProProject/_git/CppProProject) project



## Running tests
Run the following command in root directory:

```
mvn test -Dtest=%TARGET_TEST_CASE% %selenicAttribute% -Dwebdriver.chrome.driver="%CHROME_DRIVER_PATH%" 
-Dazure.publisher="%AZURE_PUBLISHER%" -Dazure.organization="%AZURE_ORGANIZATION%" 
-Dlogin.account="%LOGIN_ACCOUNT%" -Dlogin.password="%LOGIN_PASSWORD%"
```
#### Parameters in command line:
* **-Dtest**:  Which test cases you want to run. 
  * Options: `CppProProjectTests`, `CppStadProjectTests`, `DottestProjectTests`, `JtestProjectTests`, `* (run all tests)`
* **-Dwebdriver.chrome.driver**: The full path of the Chrome browser driver.
* **-Dlogin.account** (Required): Log in to the account of Azure.
* **-Dlogin.password** (Required): Log in to the password of Azure.
* **-Dazure.publisher** (Required): Azure publisher.
* **-Dazure.organization** (Required): Azure organization.
* For [Selenic Agent Options](https://docs.parasoft.com/display/SEL20222/Command+Line#CommandLine-SelenicAgentOptions)