package dfPack.test;

import java.util.HashMap;

import com.relevantcodes.extentreports.LogStatus;
import dfPack.base.BaseTest;
import dfPack.utilities.*;
import org.testng.SkipException;
import org.testng.annotations.*;

public class LoginTest extends BaseTest {
    public MyXLSReader xls;


    @DataProvider
    public Object[][] getData() throws Exception {

        String filePath = prop.getProperty("xlsxFilePath");

        xls = new MyXLSReader(filePath);

        Object[][] testData = DataUtil.getTestData(xls, "LoginTest", "Data");

        return testData;
    }

    @Test(dataProvider = "getData")
    public void doLoginTest(HashMap<String, String> map) {
        extendlogger=report.createTest("TC001 Login Test");
        if (!DataUtil.isRunnable(xls, "LoginTest", "Testcases") || map.get("Runmode").equals("N")) {
            extendlogger=report.createTest("Skipping the test as the run mode is set to N");
            throw new SkipException("Skipping the test as the run mode is set to N");
        }
        openBrowser(map.get("Browser"));
        navigate("appURL");
        doLogin(map.get("Username"), map.get("Password"));

        boolean actualResut = isElementPresent();
        String expectedRes=map.get("ExpectedResult");
        boolean expectedResult = false;
        if (expectedRes.equalsIgnoreCase("Failure")) {
            expectedResult = false;
        } else if(expectedRes.equalsIgnoreCase("Success")){
            expectedResult = true;
        } if(actualResut==expectedResult){
            reportPass("LoginTest passed");

        } else{
            reportFail("LoginTest failed ");

        }
    }
}
