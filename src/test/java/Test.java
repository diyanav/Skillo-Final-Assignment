import io.github.bonigarcia.wdm.WebDriverManager;
import object.pageObject.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

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
    public Object[] getRandomPublicInfo() {
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

        return new String[]{randomPublicInfo};
    }

    @DataProvider
    public Object[][] getPostResources() {
        File postPicture = new File("src/test/resources/upload/pug-icon.jpeg");
        String postCaption = "Test posts count update";

        return new Object[][]{
                {postPicture, postCaption}
        };
    }


    public void login() {

        LoginPage loginPage = new LoginPage(driver);
        SoftAssert softAssert = new SoftAssert();

        loginPage.navigateTo();

        softAssert.assertTrue(loginPage.isUrlLoaded(), "Login URL is not correct");

        String signInText = loginPage.getSignInElementText();
        softAssert.assertEquals(signInText, "Sign in");

        loginPage.populateUsername("test.user-1234");
        loginPage.populatePassword("test.user-1234");
        loginPage.clickSignIn();

        HomePage homePage = new HomePage(driver);

        softAssert.assertTrue(homePage.isUrlLoaded(), "The Home page is not correct! ");

        Header header = new Header(driver);
        header.clickProfile();

    }

    @org.testng.annotations.Test(dataProvider = "getRandomPublicInfo", priority = 1)
    public void editPublicInfo(String newPublicInfo) {

        SoftAssert softAssert = new SoftAssert();

        login();

        ProfilePage profilePage = new ProfilePage(driver, wait);
        softAssert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        profilePage.clickEditProfile();

        EditProfile editProfile = new EditProfile(driver);

        String actualEditProfileBoxTitle = editProfile.getEditProfileBoxTitle();
        softAssert.assertEquals(actualEditProfileBoxTitle, "Modify Your Profile", "The Edit Profile box is not loaded!");

        editProfile.editPublicInfo(newPublicInfo);

        softAssert.assertTrue(editProfile.isPublicInfoUpdated(newPublicInfo), "The Public Info is incorrect!");

        softAssert.assertAll();
    }

    @org.testng.annotations.Test(priority = 2)
    public void testPostsCounter() {

        SoftAssert softAssert = new SoftAssert();

        login();

        ProfilePage profilePage = new ProfilePage(driver, wait);
        softAssert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        int expectedPostsCount = profilePage.getExpectedPostsCount();
        int actualPostsCount = profilePage.getActualPostsCount();

        softAssert.assertEquals(actualPostsCount, expectedPostsCount, "The posts count is incorrect!");

        softAssert.assertAll();
    }

    @org.testng.annotations.Test(dataProvider = "getPostResources", priority = 3)
    public void testPostUploadCount(File file, String caption) {

        SoftAssert softAssert = new SoftAssert();

        login();

        ProfilePage profilePage = new ProfilePage(driver, wait);
        softAssert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        Header header = new Header(driver);

        int expectedPostsCountBefore = profilePage.getExpectedPostsCount();
        int actualPublicPostsCountBefore = profilePage.getActualPublicPostsCount();

        header.clickNewPost();

        PostPage postPage = new PostPage(driver);
        softAssert.assertTrue(postPage.isUrlLoaded(), "The Post URL is not correct!");

        postPage.uploadPicture(file);
        softAssert.assertTrue(postPage.isImageVisible(), "The image is not visible!");

        String imageName = postPage.getImageName();
        softAssert.assertEquals(file.getName(), imageName, "The image name is incorrect!");

        postPage.populatePostCaption(caption);

        postPage.createPost();

        softAssert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        int expectedPostsCountAfter = profilePage.getExpectedPostsCount();
        int actualPublicPostsCountAfter = profilePage.getActualPublicPostsCount();

        softAssert.assertEquals(expectedPostsCountBefore + 1, expectedPostsCountAfter, "The number of all posts count displayed on the profile is not correct after post uploading!");

        softAssert.assertEquals(actualPublicPostsCountBefore + 1, actualPublicPostsCountAfter, "The number of all public posts is not correct after post uploading!");

        softAssert.assertAll();
    }

    @org.testng.annotations.Test(priority = 4)
    public void testPostDeleteCount() {

        SoftAssert softAssert = new SoftAssert();

        login();

        ProfilePage profilePage = new ProfilePage(driver, wait);
        softAssert.assertTrue(profilePage.isUrlLoaded(), "The Profile URL is not correct!");

        int actualPublicPostsCountBefore = profilePage.getActualPublicPostsCount();

        profilePage.openFirstPublicPost();
        profilePage.deletePost();

        int actualPublicPostsCountAfter = profilePage.getActualPublicPostsCount();

        softAssert.assertEquals(actualPublicPostsCountBefore-1, actualPublicPostsCountAfter, "The number of all public posts is not correct after post deletion!");

        softAssert.assertAll();
    }
}
