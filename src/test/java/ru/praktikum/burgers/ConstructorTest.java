package ru.praktikum.burgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.praktikum.burgers.pom.MainPage;

import static org.junit.Assert.*;

public class ConstructorTest {

    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка перехода к разделу соусы")
    public void checkGoToSauces() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickSauceTab();
        assertEquals("Соусы", mainPage.getTextFromSelectedMenu());
    }

    @Test
    @DisplayName("Проверка перехода к разделу начинки")
    public void checkGoToFillings() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickFillingsTab();
        assertEquals("Начинки", mainPage.getTextFromSelectedMenu());
    }

    @Test
    @DisplayName("Проверка перехода к разделу булки")
    public void checkGoToBuns() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        mainPage.clickFillingsTab();
        mainPage.clickBunsTab();
        assertEquals("Булки", mainPage.getTextFromSelectedMenu());
    }
}
