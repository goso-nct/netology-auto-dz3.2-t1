package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage
{
    @FindBy(css = "[data-test-id=login] input")
    private SelenideElement loginField;
    @FindBy(css = "[data-test-id=password] input")
    private SelenideElement passwordField;
    @FindBy(css = "[data-test-id=action-login]")
    private SelenideElement loginButton;
    @FindBy(css = "[data-test-id=error-notification]")
    private SelenideElement errorNotification;

    public VerificationPage validLogin(DataHelper.AuthInfo user) {
        loginField.setValue(user.getLogin());
        passwordField.setValue(user.getPassword());
        loginButton.click();
        return page(VerificationPage.class);
    }

    public void invalidLogin(DataHelper.AuthInfo ai) {
        loginField.setValue(ai.getLogin());
        passwordField.setValue(ai.getPassword());
        loginButton.click();
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void shouldBeBlockedLogin() {
        loginButton.shouldBe(disabled);
    }

}
