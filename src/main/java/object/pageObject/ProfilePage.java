package object.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProfilePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isUrlLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.urlContains("http://training.skillo-bg.com:4300/users/"));
    }

    public String getUsername() {
        WebElement username = driver.findElement(By.tagName("h2"));
        return username.getText();
    }

    public void clickEditProfile() {
        WebElement editProfileIcon = driver.findElement(By.cssSelector("[class='fas fa-user-edit ng-star-inserted']"));
        editProfileIcon.click();
    }

    public int getExpectedPostsCount() {
        WebElement postsCountElement = driver.findElement(By.className("profile-stat-count"));
        return Integer.parseInt(postsCountElement.getText());
    }

    public int getActualPostsCount() {
        WebElement allPostsButton = driver.findElement(By.cssSelector("label.btn-all"));
        allPostsButton.click();

        List<WebElement> posts = driver.findElements(By.tagName("app-post"));
        return posts.size();
    }

    public int getActualPublicPostsCount() {
        List<WebElement> posts = driver.findElements(By.tagName("app-post"));
        return posts.size();
    }
}
