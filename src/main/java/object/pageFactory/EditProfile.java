package object.pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    public void editPublicInfo(String newPublicInfo) {
        publicInfoField.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        publicInfoField.clear();
        publicInfoField.sendKeys(newPublicInfo);

        wait.until(ExpectedConditions.elementToBeClickable(saveButtonEditProfile));
        saveButtonEditProfile.click();
    }

    public boolean isPublicInfoUpdated(String newPublicInfo) {
        boolean isPublicInfoUpdated = actualPublicInfo.getText().contains(newPublicInfo);

        return isPublicInfoUpdated;
    }
}
