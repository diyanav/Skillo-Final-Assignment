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

import java.io.File;
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

    @DataProvider
    public Object[][] getUsers() {
        String randomPublicInfo = getRandomPublicInfo();

        File postPicture = new File("src/main/resources/upload/pug-icon.jpeg");
        String postCaption = "Test posts count update";

        return new Object[][]{
                {"test.user-1234", "test.user-1234", "test.user-1234", randomPublicInfo, postCaption, postPicture}
        };
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

    @org.testng.annotations.Test(dataProvider = "getUsers")
    public void testProfile(String user, String password, String name, String randomPublicInfo, String caption, File file) {
        String newPublicInfo = randomPublicInfo;

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

        profilePage.clickEditProfile();

        EditProfile editProfile = new EditProfile(driver);

        String actualEditProfileBoxTitle = editProfile.getEditProfileBoxTitle();
        Assert.assertEquals(actualEditProfileBoxTitle, "Modify Your Profile", "The Edit Profile box is not loaded!");

        editProfile.editPublicInfo(newPublicInfo);

        try {
            Assert.assertTrue(editProfile.isPublicInfoUpdated(newPublicInfo), "The Public Info is incorrect!");
        } catch (AssertionError exception) {
            exception.printStackTrace();
        }

        int expectedPostsCountBefore = profilePage.getExpectedPostsCount();
        int actualPublicPostsCountBefore = profilePage.getActualPublicPostsCount();
        int actualPostsCount = profilePage.getActualPostsCount();

        try {
            Assert.assertEquals(actualPostsCount, expectedPostsCountBefore, "The posts count is incorrect!");
        } catch (AssertionError exception) {
            exception.printStackTrace();
        }

        header.clickNewPost();

        PostPage postPage = new PostPage(driver);
        Assert.assertTrue(postPage.isUrlLoaded(), "The Post URL is not correct!");

        postPage.uploadPicture(file);
        Assert.assertTrue(postPage.isImageVisible(), "The image is not visible!");
        String imageName = postPage.getImageName();
        Assert.assertEquals(file.getName(), imageName, "The image name is incorrect!");

        postPage.populatePostCaption(caption);

        postPage.createPost();

        Assert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        int expectedPostsCountAfter = profilePage.getExpectedPostsCount();
        int actualPublicPostsCountAfter = profilePage.getActualPublicPostsCount();

        try {
            Assert.assertEquals(expectedPostsCountBefore + 1, expectedPostsCountAfter, "The number of all posts count displayed on the profile is not correct!");
        } catch (AssertionError exception) {
            exception.printStackTrace();
        }

        try {
            Assert.assertEquals(actualPublicPostsCountBefore + 1, actualPublicPostsCountAfter, "The number of all public posts is not correct!");
        } catch (AssertionError exception) {
            exception.printStackTrace();
        }

        profilePage.openFirstPublicPost();
        profilePage.deletePost();

        actualPublicPostsCountAfter = profilePage.getActualPublicPostsCount();

        try {
            Assert.assertEquals(actualPublicPostsCountBefore, actualPublicPostsCountAfter, "The number of all public posts is not correct!");
        } catch (AssertionError exception) {
            exception.printStackTrace();
        }
    }
}
