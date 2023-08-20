import io.github.bonigarcia.wdm.WebDriverManager;
import object.pageObject.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Random;

public class Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeSuite
    protected final void setupTestSuite() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.safaridriver().setup();
    }

    @BeforeMethod
    protected final void setUpTest() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().setPosition(new Point(2000, 0));
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    protected final void tearDownTest() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @DataProvider(name = "getUsers")
    public Object[][] getUsers() {
        return new Object[][]{
                {"test.user-1234", "test.user-1234", "test.user-1234"}
        };
    }

    @DataProvider(name = "getRandomPublicInfo")
    public Object[] getRandomPublicInfo() {
        //String randomPublicInfo = String.format("%06d", random);
        byte[] array = new byte[10]; // length is bounded by 7
        new Random().nextBytes(array);
        String randomPublicInfo = new String(array, StandardCharsets.UTF_8);
        return new String[]{randomPublicInfo};
    }

    @org.testng.annotations.Test(dataProvider = "getUsers")
    public void testLogin(String user, String password, String name) {
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        Header header = new Header(driver);
        header.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUrlLoaded(), "Login URL is not correct");

        String signInText = loginPage.getSignInElementText();
        Assert.assertEquals(signInText, "Sign in");
        loginPage.populateUsername(user);
        loginPage.populatePassword(password);
        loginPage.clickSignIn();

        Assert.assertTrue(homePage.isUrlLoaded(), "The Home page is not correct! ");

        header.clickProfile();

        ProfilePage profilePage = new ProfilePage(driver, wait);
        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        String actualUsername = profilePage.getUsername();
        Assert.assertEquals(actualUsername, name, "The username is incorrect!");

        }

    @org.testng.annotations.Test(dataProvider = "getRandomPublicInfo")
    public void testEditProfile(String randomPublicInfo) {

        ProfilePage profilePage = new ProfilePage(driver, wait);
        profilePage.clickEditProfile();

        EditProfile editProfile = new EditProfile(driver);

        String actualEditProfileBoxTitle = editProfile.getEditProfileBoxTitle();
        Assert.assertEquals(actualEditProfileBoxTitle, "Modify Your Profile", "The Edit Profile box is not loaded!");

        editProfile.editPublicInfo(randomPublicInfo);
    }

}
