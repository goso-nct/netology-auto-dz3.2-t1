package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

class LoginTest
{
    LoginPage loginPage;

    @BeforeEach
    void up() {
        DataHelper.truncateAuthCode();
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void tearDown() { closeWebDriver(); }

    @AfterAll
    static void deleteDbData() { DataHelper.deleteDbData(); }

    @Test
    void shouldWork() throws InterruptedException {
        var user = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(user);
        Thread.sleep(5_000);
        var dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(user));
        dashboardPage.isOpen();
    }

    @Test
    void shouldBeNotificationBadCode() throws InterruptedException {
        var user = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(user);
        Thread.sleep(5_000);
        verificationPage.invalidVerify(DataHelper.getInvalidVerificationCodeFor(user));
    }

    @Test
    void shouldBeErrorIfInvalidAuth() {
        loginPage.invalidLogin(DataHelper.getInvalidAuthInfo());
    }

    @Test
    void shouldBeBlockedIfTripleInvalidAuth() {
        loginPage.invalidLogin(DataHelper.getAuthInfoWithInvalidPass());
        loginPage.invalidLogin(DataHelper.getAuthInfoWithInvalidPass());
        loginPage.invalidLogin(DataHelper.getAuthInfoWithInvalidPass());
        loginPage.shouldBeBlocked();
    }

}
