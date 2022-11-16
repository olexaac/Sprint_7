package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH_CREATE = "api/v1/courier/";

    private static final String PATH_LOGIN = "api/v1/courier/login/";

    @Step("Запрос на создание курьера")
    public ValidatableResponse create(Courier courier) { // в объекте данного типа хранится вся информация о response
        return given()
                .spec(getSpec()) // данный метод настраивает запрос, который будет использован
                .body(courier)
                .when() //когда запрос отправлен, возвращаем соответствующий response
                // выносим ручку в константу
                .post(PATH_CREATE)
                .then();
    }

    @Step("Запрос на авторизацию курьера")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    @Step("Запрс на удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH_CREATE + courierId)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
