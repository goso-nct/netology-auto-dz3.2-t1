package ru.netology.web.page;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    public void isOpen() {
        $("h2").shouldHave(text("Личный кабинет"));
    }
}
