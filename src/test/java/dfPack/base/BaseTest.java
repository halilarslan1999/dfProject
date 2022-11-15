package dfPack.base;


import java.io.File;

import org.testng.ITestResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import dfPack.pages.LoginPage;
import dfPack.utilities.BrowserUtils;
import dfPack.utilities.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.*;

public class BaseTest {
    protected static WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;
    protected Alert alert;
    // to start and build the reports
    protected com.aventstack.extentreports.ExtentReports report;
    //this class is used to create HTML report file
    protected ExtentHtmlReporter htmlReporter;
    //this will define a test enables adding logs, authors, test steps
    protected com.aventstack.extentreports.ExtentTest extendlogger;

    public Properties prop = null;
    public ExtentReports eReport = ExtentManager.getInstance();
    public ExtentTest eTest;
    LoginPage loginPage;

    @BeforeTest
    public void seUpTest() {
        System.out.println("This is Before Test");
        //initialize the class
        report = new com.aventstack.extentreports.ExtentReports();

        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "//test-output//report.html";

        //initialize the html reporter with the report path
        htmlReporter = new ExtentHtmlReporter(path);

        //attach the html report to report object
        report.attachReporter(htmlReporter);

        //title in report
        htmlReporter.config().setReportName("Smoke Test");

        //set environment information
        report.setSystemInfo("Environment", "QA");
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Test Engineer", "Erhan");
    }
    //Initialization
    @BeforeClass
    public void initialise(){
        System.out.println("This is Before Class");

        if(prop==null){
            prop = new Properties();
            File projectConfigFile = new File("src//test//resources//projectconfig.properties");
           FileInputStream fis = null;
            try {
                fis = new FileInputStream(projectConfigFile);
                prop.load(fis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public WebDriver openBrowser(String browserType) {
        extendlogger.info("Opening Browser : "+browserType);
        if (driver == null) {
            String browser = browserType;
            switch (browser) {
                case "Chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "Chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
                    break;
                case "Firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "Firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
                    break;
                case "IE":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "Edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "Safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driver = new SafariDriver();
                    break;
            }
        }
        extendlogger.info("Browser opened Successfully "+browserType);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        return driver;

    }

    //Open application
    public void navigate(String urlKey) {
        driver.get(prop.getProperty(urlKey));
        extendlogger.info("Navigated to "+prop.getProperty(urlKey));
    }
    public void doLogin(String username, String password){
        loginPage=new LoginPage(driver);

        //PageFactory is used to find elements with @FindBy specified
        //PageFactory.initElements(driver, loginPage);

        extendlogger.info("Cookies Accepted");
        loginPage.cookieBtn.click();

        extendlogger.info("Language button accepted");
        loginPage.languageBtn.click();

        extendlogger.info( "Signin page with email opened");
        loginPage.signInInput.click();

        BrowserUtils.waitFor(2);
        extendlogger.info("Username entered");
        loginPage.emailInput.sendKeys(username);

        BrowserUtils.waitFor(2);
        extendlogger.info("User name entered and next button clicked");
        loginPage.nextInput.click();

        BrowserUtils.waitFor(2);
        extendlogger.info("Password entered");
        loginPage.passpordInput.sendKeys(password);

        BrowserUtils.waitFor(2);
        extendlogger.info("Clicked to enter the weppage ");
        loginPage.signIn.click();

        Assert.assertTrue(loginPage.allAppInput.isDisplayed());
        loginPage.allAppInput.click();

    }

    //Finding whether the required element is available on the page
    public boolean isElementPresent() {

        if(loginPage.CRMVerification.isDisplayed()){
            return true;
        }else{
            return false;
        }
     }

    //This method will be called when the test is passed
    public void reportPass(String messsage) {

        extendlogger.pass(messsage);

    }

    //This method will be called when the test is failed
    public void reportFail(String message) {


        extendlogger.fail(message);
        Assert.fail(message);

    }
    public static String getScreenshot(String name) throws IOException {
        // name the screenshot with the current date time to avoid duplicate name
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot ---> interface from selenium which takes screenshots
        TakesScreenshot ts = (TakesScreenshot) driver;

        File source = ts.getScreenshotAs(OutputType.FILE);
        //   ((TakesScreenshot)Driver.get()).getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws InterruptedException, IOException {
        // If test fails
        if(result.getStatus()==ITestResult.FAILURE){
            //record the name of failed case
            extendlogger.fail(result.getName());
            //take the screenshot
            String screenShotPath= getScreenshot(result.getName());
            //add your screenshot to your report
            extendlogger.addScreenCaptureFromPath(screenShotPath);
            //capture the exception and put inside the report
            extendlogger.fail(result.getThrowable());
        }
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    @AfterTest
    public void tearDownTest(){
        System.out.println("This is after test");
        //this is when the report is actually created
        report.flush();

    }


}