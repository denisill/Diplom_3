package ru.praktikum.burgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.praktikum.burgers.client.UserClient;
import ru.praktikum.burgers.model.User;
import ru.praktikum.burgers.pom.AccountPage;
import ru.praktikum.burgers.pom.LoginPage;
import ru.praktikum.burgers.pom.MainPage;
import ru.praktikum.burgers.util.UserGenerator;

public class PersonalAccountTests {

    private WebDriver driver;
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
        driver.quit();
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет»")
    public void loginUserPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickPersonalAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.isSingInButtonDisplayed();
    }

    @Test
    @DisplayName("Переход по клику на «Конструктор» из личного кабинета")
    public void checkGoToConstructor() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        userClient = new UserClient();
        user = UserGenerator.getUser();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.clickPersonalAccountButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickCreateBurgerButton();
        mainPage.isOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Переход по клику на логотип Stellar Burgers из личного кабинета")
    public void checkOutFromPersonalAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        userClient = new UserClient();
        user = UserGenerator.getUser();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();
        mainPage.clickPersonalAccountButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickStellarBurgerLogoButton();
        mainPage.isOrderButtonDisplayed();
    }
}
