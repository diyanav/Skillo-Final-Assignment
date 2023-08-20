package object.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditProfile {
    private final WebDriver driver;
    private WebDriverWait wait;

    public EditProfile(WebDriver driver) {
        this.driver = driver;
        this.wait = wait;
    }

    public String getEditProfileBoxTitle() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement editProfileBoxTitle = driver.findElement(By.tagName("h4"));
        return editProfileBoxTitle.getText();
    }

    public void editPublicInfo(String randomPublicInfo) {
        WebElement publicInfo = driver.findElement(By.className("form-control ng-pristine ng-valid ng-touched"));
        publicInfo.click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        publicInfo.sendKeys(randomPublicInfo);
    }

}
