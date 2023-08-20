package object.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditProfile {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(tagName = "h4")
    private WebElement editProfileBoxTitle;

    public EditProfile(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public String getEditProfileBoxTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return editProfileBoxTitle.getText();
    }
}
