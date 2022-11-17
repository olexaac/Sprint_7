import api.courier.Courier;
import api.courier.CourierClient;
import api.courier.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourierCreateNegativeTest {

    private CourierClient courierClient;

    public CourierCreateNegativeTest() {
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверка статуса ответа и текста об ошибке при создания двух одинаковых курьеров")
    public void courierCanNotBeCreatedIfHeWasCreated() {
        Courier courier = CourierGenerator.getDefault();
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCode = responseCreate.extract().statusCode();
        String messageError = responseCreate.extract().path("message");

        assertEquals(409, actualStatusCode);
        assertEquals(messageError, "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Нельзя создать курьера если одного из полей нет")
    @Description("Проверка статуса ответа и текста об ошибке при создании курьера с незаполненным ипутом")
    public void courierCanNotBeCreatedWithEmptyInput() {
        Courier courier = CourierGenerator.getNotInput();
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCode = responseCreate.extract().statusCode();
        String messageError = responseCreate.extract().path("message");

        assertEquals(400, actualStatusCode);
        assertEquals(messageError, "Недостаточно данных для создания учетной записи");
    }
}
