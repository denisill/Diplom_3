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
import ru.praktikum.burgers.pom.LoginPage;
import ru.praktikum.burgers.pom.MainPage;
import ru.praktikum.burgers.pom.RecoverPasswordPage;
import ru.praktikum.burgers.pom.RegistrationPage;
import ru.praktikum.burgers.util.UserGenerator;

public class LoginUserTests {

    private WebDriver driver;
    private String accessToken;
    private UserClient userClient;
    private User user;

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
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    public void loginUserLoginButton() {
        userClient = new UserClient();
        user = UserGenerator.getUser();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();
        mainPage.isOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Вход по кнопке «Личный кабинет»")
    public void loginUserPersonalAccountButton() {
        userClient = new UserClient();
        user = UserGenerator.getUser();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.isOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Вход по кнопке в форме регистрации")
    public void loginUserRegistrationForm() {
        userClient = new UserClient();
        user = UserGenerator.getUser();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.registerButtonClick();
        RegistrationPage registerPage = new RegistrationPage(driver);
        registerPage.clickSingInButton();

        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.isOrderButtonDisplayed();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginUserRecoverPasswordButton() {
        userClient = new UserClient();
        user = UserGenerator.getUser();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordButton();
        RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage(driver);
        recoverPasswordPage.clickLoginButton();

        loginPage.setLoginForm(user.getEmail(), user.getPassword());
        loginPage.singInButtonClick();

        mainPage.isOrderButtonDisplayed();
    }
}