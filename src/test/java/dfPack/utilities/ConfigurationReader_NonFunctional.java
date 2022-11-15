package dfPack.utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader_NonFunctional {
    public static Properties prop = null;
    public static WebDriver driver = null;
    public static ExtentReports eReport = ExtentManager.getInstance();
    public static ExtentTest eTest;

       static{
        if(prop ==null) {

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


    public static String initialize(String keyName) {

        return prop.getProperty(keyName);
    }


}
