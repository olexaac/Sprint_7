import api.courier.CourierClient;
import api.courier.CourierGenerator;
import api.courier.Credentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка кода ответа и id при успешной авторизации курьера")
    public void courierCanBeLogin() {
        Credentials credentials = Credentials.from(CourierGenerator.getDefault());
        ValidatableResponse responseCreate = courierClient.login(credentials);
        int actualStatusCode = responseCreate.extract().statusCode();
        int courierId = responseCreate.extract().path("id");

        assertEquals(200, actualStatusCode);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Нельзя авторизоваться если поле заполнено неправильно")
    @Description("Проверка статуса ответа и текста об ошибке при авторизации с неправильным паролем")
    public void courierCanNotBeLoginWithWrongInput() {
        Credentials credentials = Credentials.from(CourierGenerator.getWrongPassword());
        ValidatableResponse responseCreate = courierClient.login(credentials);
        int actualStatusCode = responseCreate.extract().statusCode();
        String messageError = responseCreate.extract().path("message");

        assertEquals(404, actualStatusCode);
        assertEquals(messageError, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Нельзя авторизоваться если поле не заполнено")
    @Description("Проверка статуса ответа и текста об ошибке при авторизации с пустым полем")
    public void courierCanNotBeLoginWithEmptyInput() {
        Credentials credentials = Credentials.from(CourierGenerator.getNotInput());
        ValidatableResponse responseCreate = courierClient.login(credentials);
        int actualStatusCode = responseCreate.extract().statusCode();
        String messageError = responseCreate.extract().path("message");

        assertEquals(400, actualStatusCode);
        assertEquals(messageError, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Нельзя авторизоваться если учетная запись не найдена")
    @Description("Проверка статуса ответа и текста об ошибке при авторизации с несуществующими данными")
    public void courierCanNotBeLoginIfHeWasNotCreated() {
        Credentials credentials = Credentials.from(CourierGenerator.getNotUser());
        ValidatableResponse responseCreate = courierClient.login(credentials);
        int actualStatusCode = responseCreate.extract().statusCode();
        String messageError = responseCreate.extract().path("message");

        assertEquals(404, actualStatusCode);
        assertEquals(messageError, "Учетная запись не найдена");
    }
}
