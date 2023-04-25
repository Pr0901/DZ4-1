import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class CardDeliveryTest {

    private String date(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void successfulAuthorization() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $x("//input[contains(@placeholder,'Город')]").setValue("Краснодар");
        String currentDate = date(4, "dd.MM.yyyy");
        $x("//input[contains(@placeholder,'Дата встречи')]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[contains(@placeholder,'Дата встречи')]").setValue(currentDate);
        $("[data-test-id=name] input").setValue("Анна Петровна");
        $("[data-test-id=phone] input").setValue("+79529676386");
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(),'Забронировать')]").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(12));
        $x("//div[contains(@class, 'notification__content')]").shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
}
