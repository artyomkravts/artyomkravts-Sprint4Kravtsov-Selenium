import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import pages.SearchPage;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;

public class MainPageButtonsTest {

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void doesSamokatButtonLeadToMainPage() {
        mainPage.clickSamokatButton();
        Assert.assertEquals("The URL is unexpected", "https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

    @Test
    public void doesYandexButtonLeadToDzenPage() {
        mainPage.clickYandexButton();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        Assert.assertThat(driver.getCurrentUrl(), containsString("dzen.ru"));
    }

    @Test
    public void doesNoSuchOrderPageAppear() {
        mainPage.clickOrderStatusButton();
        mainPage.fillOrderStatusInput("42fafd");
        mainPage.clickGoButton();
        SearchPage searchPage = new SearchPage(driver);
        searchPage.isNoSuchOrderPageVisible();

    }
}