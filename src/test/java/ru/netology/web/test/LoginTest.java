package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest
{
//    LoginPage loginPage = open("http://localhost:9999", LoginPage.class);

//    @AfterEach
//    void tearDown() { closeWebDriver(); }

    @Test
    void testGetCode() {
        System.out.println(DataHelper.getVerificationCodeFor(DataHelper.getAuthInfo())
                );
    }

//    @Test
//    void shouldBeErrorIfInvalidAuth() {
//        assertTrue(loginPage.invalidLogin(
//                DataHelper.getInvalidAuthInfo().getLogin(),
//                DataHelper.getInvalidAuthInfo().getPassword()
//        ));
//    }

}
