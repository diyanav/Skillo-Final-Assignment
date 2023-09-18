package object.pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/login";

    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "h4")
    private WebElement signInFormTitle;

    @FindBy(id = "defaultLoginFormUsername")
    private WebElement userNameField;

    @FindBy(id = "defaultLoginFormPassword")
    private WebElement passwordField;

    @FindBy(id = "sign-in-button")
    private WebElement signInButton;

    public void navigateTo() {
        this.driver.get(PAGE_URL);
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isUrlLoaded() {
        return wait.until(ExpectedConditions.urlToBe(PAGE_URL));
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(signInFormTitle));

        userNameField.sendKeys(username);

        passwordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }
}
