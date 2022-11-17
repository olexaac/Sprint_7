import api.order.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderListTest {

    private OrderClient ordersClient;

    @Before
    public void setUp() {
        ordersClient = new OrderClient();
    }

    @Test
    @DisplayName("Получвение списка заказов")
    @Description("Проверка кода ответа и содержимого заказов")
    public void getListOrders() {
        ValidatableResponse responseCreate = ordersClient.listOrder();
        int actualStatusCode = responseCreate.extract().statusCode();
        List<Object> orders = responseCreate.extract().path("orders");

        assertEquals(200, actualStatusCode);
        assertThat(orders, is(not(empty())));
    }
}
