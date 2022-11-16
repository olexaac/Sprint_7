package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {

    private CourierClient courierClient;

    private int courierId;

    public CourierCreateTest() {
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
        //courierId = courierClient.login(Credentials.from(courier)).extract().path("id");

        assertEquals(201, actualStatusCode);
        assertTrue(isCourierCreated);
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

    //@After
    //public void tearDown() {
    //courierClient.delete(courierId);
    //}
}
