package object.pageObject;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

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

    public boolean editPublicInfo() {
        WebElement publicInfoField = driver.findElement(By.cssSelector("[formcontrolname='publicInfo']"));
        publicInfoField.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String newPublicInfo = getRandomPublicInfo();

        publicInfoField.clear();
        publicInfoField.sendKeys(newPublicInfo);

        WebElement saveButtonEditProfile = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type=submit]")));
        saveButtonEditProfile.click();

        WebElement actualPublicInfo = driver.findElement(By.xpath("//div[5]/p"));

        boolean isPublicInfoUpdated = String.valueOf(actualPublicInfo).contains(newPublicInfo);

        return isPublicInfoUpdated;
    }


    public String getRandomPublicInfo() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        String randomPublicInfo = buffer.toString();

        return randomPublicInfo;
    }
}
