package dfPack.pages;

import dfPack.utilities.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    WebDriver driver;


    public BasePage() {

        PageFactory.initElements(driver, this);

    }



    public void navigateToModule(String tab, String module) {
        String tabLocator = "//span[normalize-space()='"+tab+"']";
        String moduleLocator = "//span[.='"+module+"']";

        try {
            BrowserUtils.waitForClickablility(By.xpath(tabLocator), 5);
            WebElement tabElement = driver.findElement(By.xpath(tabLocator));
            new Actions(driver).moveToElement(tabElement).pause(200).doubleClick(tabElement).build().perform();
        } catch (Exception e) {
            BrowserUtils.clickWithWait(By.xpath(tabLocator), 5);
        }
        try {
            BrowserUtils.waitForPresenceOfElement(By.xpath(moduleLocator), 5);
            BrowserUtils.waitForVisibility(By.xpath(moduleLocator), 5);
            BrowserUtils.scrollToElement(driver.findElement(By.xpath(moduleLocator)));
            driver.findElement(By.xpath(moduleLocator)).click();
        } catch (Exception e) {
            BrowserUtils.clickWithTimeOut(driver.findElement(By.xpath(moduleLocator)),  5);

        }
    }



}