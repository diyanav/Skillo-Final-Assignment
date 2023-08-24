package object.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public void editPublicInfo(String newPublicInfo) {
        WebElement publicInfoField = driver.findElement(By.cssSelector("[formcontrolname='publicInfo']"));
        publicInfoField.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        publicInfoField.clear();
        publicInfoField.sendKeys(newPublicInfo);

        WebElement saveButtonEditProfile = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=submit]")));
        saveButtonEditProfile.click();
    }

    public boolean isPublicInfoUpdated(String newPublicInfo) {
        WebElement actualPublicInfo = driver.findElement(By.xpath("//div[5]/p"));
        boolean isPublicInfoUpdated = actualPublicInfo.getText().contains(newPublicInfo);

        return isPublicInfoUpdated;
    }

}
