package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Courier getDefault() {
        return new Courier("jsdhghfgghddss", "dhdj", "ghjghjhj");
    }

    public static Courier getRandomCourier() {
        final String courierLogin = RandomStringUtils.randomAlphabetic(7);
        final String courierPassword = RandomStringUtils.randomAlphabetic(7);
        final String courierFirstName = RandomStringUtils.randomAlphabetic(7);

        return new Courier(courierLogin, courierPassword, courierFirstName);
    }

    public static Courier getNotInput() {
        return new Courier("jsdhghfgghddss", "", "");
    }

    public static Courier getWrongPassword() {
        return new Courier("jsdhghfgghddss", "error", "ghjghjhj");
    }

    public static Courier getNotUser() {
        return new Courier("takogonet", "error", "takogonet");
    }
}
