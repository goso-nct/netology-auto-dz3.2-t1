package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest
{
    LoginPage loginPage;

    @BeforeEach
    void truncate() {
        DataHelper.truncateAuthCode();
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void tearDown() { closeWebDriver(); }

    @RepeatedTest(5)
    void shouldWork(RepetitionInfo repetitionInfo) throws InterruptedException {
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
        assertTrue(loginPage.invalidLogin(
                DataHelper.getInvalidAuthInfo().getLogin(),
                DataHelper.getInvalidAuthInfo().getPassword()
        ));
    }

}
