package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
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

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15)).shouldBe(visible);

        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldHave(text("Необходимо подтверждение"), Duration.ofSeconds(15)).shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    public void webInterfacePositiveTest() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
    @Test
    void emptyValues() {
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_invalid").should(exactText("Поле обязательно для заполнения"));
    }


    @Test
    void enterCurrentDate() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 0;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidData() {
        Faker faker = new Faker();
        String invalidData = faker.lorem().word();
        var validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(invalidData);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }


    @Test
    void invalidName() {
        Faker faker = new Faker();
        String invalidName = faker.lorem().word();
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(invalidName);
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }



    @Test
    void cityNotSupported() {
        open("http://localhost:9999");
        var validUser = DataGenerator.Registration.generateUser("en");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }
}
