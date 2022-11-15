package dfPack.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BasePage{
    WebDriver driver;

    @FindBy(xpath = "//span[text()='Accept All Cookies']")
    public WebElement cookieBtn;

    @FindBy(xpath = "//span[@class='langClose']")
    public WebElement languageBtn;

    @FindBy(xpath = "//a[contains(text(),'Sign in')]")
    public WebElement signInInput;

    @FindBy(xpath = "//input[@id='login_id']")
    public WebElement emailInput;

    @FindBy(css = "button[id='nextbtn'] span")
    public WebElement nextInput;

    @FindBy(css = "#password")
    public WebElement passpordInput;

    @FindBy(xpath = "//div[@class='announcement_header']")
    public WebElement accountConfirm;

    @FindBy(xpath = "//form[@id='login']//button[@id='nextbtn']")
    public WebElement signIn;

    @FindBy(css = "#all-apps")
    public WebElement allAppInput;

    @FindBy(xpath = "(//p[text()='CRM'])[1]")
    public WebElement CRMVerification;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    /*   @FindBy(css = ".menu-list")
       public List<WebElement> menuList;

       @FindAll({
               @FindBy(id = "loginpage-input-email"),
               @FindBy(name="email")
       })*/


/*    public void login(String username,String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        understandBtn.click();
        loginBtn.click();
    }

    public void loginAsTeacher(){
        String username= ConfigurationReader.initialize("usernameTeacher");
        String password= ConfigurationReader.initialize("passwordTeacher");
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        understandBtn.click();
        loginBtn.click();
    }*/

}