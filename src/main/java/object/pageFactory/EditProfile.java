package object.pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class EditProfile {
    private final WebDriver driver;
    private WebDriverWait wait;

    @FindBy(tagName = "h4")
    private WebElement editProfileBoxTitle;

    @FindBy(css = "[formcontrolname='publicInfo']")
    private WebElement publicInfoField;

    @FindBy(css = "[type=submit]")
    private WebElement saveButtonEditProfile;

    @FindBy(xpath = "//div[5]/p")
    private WebElement actualPublicInfo;

    public EditProfile(WebDriver driver) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public String getEditProfileBoxTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return editProfileBoxTitle.getText();
    }

    public boolean editPublicInfo() {
        publicInfoField.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String newPublicInfo = getRandomPublicInfo();

        publicInfoField.clear();
        publicInfoField.sendKeys(newPublicInfo);

        wait.until(ExpectedConditions.elementToBeClickable(saveButtonEditProfile));
        saveButtonEditProfile.click();

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
