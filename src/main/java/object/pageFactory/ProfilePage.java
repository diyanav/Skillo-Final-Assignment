package object.pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(tagName = "h2")
    private WebElement username;

    @FindBy(xpath = "//*[@class='fas fa-user-edit ng-star-inserted']")
    private WebElement editProfileIcon;

    @FindBy(className = "profile-stat-count")
    private WebElement postsCountElement;

    @FindBy(css = "label.btn-all")
    private WebElement allPostsButton;

    @FindBy(tagName = "app-post")
    private List<WebElement> postsPreviewCount;

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

    public int getExpectedPostsCount() {
        return Integer.parseInt(postsCountElement.getText());
    }

    public int getActualPostsCount() {
        wait.until(ExpectedConditions.elementToBeClickable(allPostsButton));
        allPostsButton.click();

        List<WebElement> posts = postsPreviewCount;
        return posts.size();
    }

    public int getActualPublicPostsCount() {
        List<WebElement> posts = postsPreviewCount;
        return posts.size();
    }

}
