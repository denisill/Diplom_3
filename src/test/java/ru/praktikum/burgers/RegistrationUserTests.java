package ru.praktikum.burgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.burgers.client.UserClient;
import ru.praktikum.burgers.model.User;
import ru.praktikum.burgers.model.UserCredentials;
import ru.praktikum.burgers.pom.LoginPage;
import ru.praktikum.burgers.pom.MainPage;
import ru.praktikum.burgers.pom.RegistrationPage;
import ru.praktikum.burgers.util.UserGenerator;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegistrationUserTests {

    private WebDriver driver;
    private String accessToken;
    private UserClient userClient;

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
    @DisplayName("Успешная регистрации")
    public void checkRegistration() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        userClient = new UserClient();
        User user = UserGenerator.getUser();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.registerButtonClick();

        RegistrationPage registerPage = new RegistrationPage(driver);
        registerPage.setFieldsForRegistration(user.getName(), user.getEmail(), user.getPassword());

        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userClient.loginUser(userCredentials);
        loginResponse.assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Ошибка некорректного пароля")
    public void checkRegistrationWithIncorrectPassword() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        userClient = new UserClient();
        User user = UserGenerator.getIncorrectPasswordUser();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.registerButtonClick();

        RegistrationPage registerPage = new RegistrationPage(driver);
        registerPage.setFieldsForRegistration(user.getName(), user.getEmail(), user.getPassword());
        registerPage.getTextError();

        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userClient.loginUser(userCredentials);
        loginResponse.assertThat()
                .statusCode(401)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}