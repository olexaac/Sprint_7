package api.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import api.base.Client;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private static final String PATH_ORDER = "api/v1/orders/";

    private static final String PATH_LIST = "api/v1/orders/";

    @Step("Запрос создания заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_ORDER)
                .then();
    }

    @Step("Запрос получения списка заказов")
    public ValidatableResponse listOrder() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_LIST)
                .then();
    }
}
