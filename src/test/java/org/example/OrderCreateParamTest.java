package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreateParamTest {
    private final String firstNameValue;
    private final String lastNameValue;
    private final String addressValue;
    private final int metroStationValue;
    private final String phoneValue;
    private final int rentTimeValue;
    private final String deliveryDateValue;
    private final String commentValue;
    private final List<String> colorValue;

    public OrderCreateParamTest(String firstNameValue, String lastNameValue, String addressValue, int metroStationValue,
                           String phoneValue, int rentTimeValue, String deliveryDateValue, String commentValue, List<String> colorValue) {
        this.firstNameValue = firstNameValue;
        this.lastNameValue = lastNameValue;
        this.addressValue = addressValue;
        this.metroStationValue = metroStationValue;
        this.phoneValue = phoneValue;
        this.rentTimeValue = rentTimeValue;
        this.deliveryDateValue = deliveryDateValue;
        this.commentValue = commentValue;
        this.colorValue = colorValue;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDataCreateOrder() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", null},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK", "GREY")},
        };
    }

    @Test
    @DisplayName("Параметризованный тест создания заказа")
    @Description("Проверка создания заказа без указания цвета, с черным, серым и обоими цветами")
    public void testTrackFieldInOrder(){
        OrderClient ordersClient = new OrderClient();
        ValidatableResponse responseCreate = ordersClient.create(new Order(firstNameValue, lastNameValue, addressValue,
                metroStationValue, phoneValue, rentTimeValue, deliveryDateValue, commentValue, colorValue));
        int actualStatusCode = responseCreate.extract().statusCode();
        int track = responseCreate.extract().path("track");

        assertEquals(201, actualStatusCode);
        assertThat("Track ID is incorrect", track, is(not(0)));
    }
}