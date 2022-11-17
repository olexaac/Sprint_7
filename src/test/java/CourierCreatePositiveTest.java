import api.courier.Courier;
import api.courier.CourierClient;
import api.courier.CourierGenerator;
import api.courier.Credentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierCreatePositiveTest {

    private CourierClient courierClient;

    private int courierId;

    public CourierCreatePositiveTest() {

    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка кода ответа и сообщения при успешном создании курьера")
    public void courierCanBeCreated() {
        Courier courier = CourierGenerator.getRandomCourier(); // отделяем генерацию тестовых данных от теста
        ValidatableResponse responseCreate = courierClient.create(courier); // вызываем метод, дергающий соответствующий endpoint
        int actualStatusCode = responseCreate.extract().statusCode();
        boolean isCourierCreated = responseCreate.extract().path("ok");
        courierId = courierClient.login(Credentials.from(courier)).extract().path("id");

        assertEquals(201, actualStatusCode);
        assertTrue(isCourierCreated);
    }

    @After
    public void tearDown() {
    courierClient.delete(courierId);
    }
}
