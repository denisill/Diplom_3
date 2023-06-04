package ru.praktikum.burgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;
    //Кнопка "войти в аккаунт
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    //Кнопка "оформить заказ"
    private By setOrderButton = By.xpath(".//button[@class = 'button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']" +
            "[text() = 'Оформить заказ']");
    //Кнопка "личный кабинет"
    private By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    //Кнопка перехода в раздел "Булки"
    private By bunsTab = By.xpath(".//div[./span[text()='Булки']]");
    //Картинка "Флюоресцентная булка R2-D3" для проверки видимости раздела
    private By fluorescentBunTab = By.xpath(".//img[@alt='Флюоресцентная булка R2-D3']");
    //Кнопка перехода в раздел "Соусы"
    private By sauceTab = By.xpath(".//div[./span[text()='Соусы']]");
    //Наличие соуса Spicy-X для проверки видимости раздела Соусы
    private By spicySauceTab = By.xpath(".//p[text()='Соус Spicy-X']");
    //Кнопка перехода в раздел "Начинки"
    private By fillingsTab = By.xpath(".//div[./span[text()='Начинки']]");
    //Картинка "Мясо бессмертных моллюсков Protostomia" для проверки видимости раздела
    private By protostomiaFillingsTab = By.xpath(".//img[@alt='Мясо бессмертных моллюсков Protostomia']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие главной страницы сайта")
    public void openPage() {
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Step("Клик по кнопке Войти")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик по кнопке Личный кабинет")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Проверка отображения кнопки Оформить заказ")
    public void isOrderButtonDisplayed() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(setOrderButton));
    }

    @Step("Клик по кнопке Соусы")
    public void clickSauceTab() {
        driver.findElement(sauceTab).click();
    }


    @Step("Проверка отображения соуса Spicy-X")
    public boolean isSpicySauceTabDisplayed() {
        return driver.findElement(spicySauceTab).isDisplayed();
    }

    @Step("Клик по кнопке Начинки")
    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    @Step("Проверка отображения картинки для начинки Мясо бессмертных моллюсков")
    public boolean isProtostomiaFillingsTabDisplayed() {
        return driver.findElement(protostomiaFillingsTab).isDisplayed();
    }

    @Step("Клик по кнопке Булки")
    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    @Step("Проверка отображения флюорисцентной булки")
    public boolean isFluorescentBunTabDisplayed() {
        return driver.findElement(fluorescentBunTab).isDisplayed();
    }
}
