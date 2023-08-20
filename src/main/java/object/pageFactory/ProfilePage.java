package object.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(tagName = "h2")
    private WebElement username;

    @FindBy(xpath = "//*[@class='fas fa-user-edit ng-star-inserted']")
    private WebElement editProfileIcon;

    public ProfilePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public boolean isUrlLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.urlContains(PAGE_URL));
    }

    public String getUsername() {
        return username.getText();
    }

    public void clickEditProfile() {
        editProfileIcon.click();
    }

}
