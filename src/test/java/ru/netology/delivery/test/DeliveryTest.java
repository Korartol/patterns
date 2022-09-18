package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        $x("//*[@data-test-id=\"city\"]//input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id=\"date\"]//input").setValue(firstMeetingDate);
        $x("//*[@data-test-id=\"name\"]//input").setValue(validUser.getName());
        $x("//*[@data-test-id=\"phone\"]//input").setValue(validUser.getPhone());
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//span[@class=\"button__content\"]").click();
        $x("//*[contains(text(), \"Успешно!\")]").should(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id=\"success-notification\"]").should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id=\"date\"]//input").setValue(secondMeetingDate);
        $x("//span[@class=\"button__content\"]").click();
        $x("//*[@data-test-id=\"replan-notification\"]//span[text()=\"Перепланировать\"]").click();
        $x("//*[@data-test-id=\"success-notification\"]//div[contains(text(),\"Встреча успешно запланирована на \")]")
                .shouldBe(Condition.visible).shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}