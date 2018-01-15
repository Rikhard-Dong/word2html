package io.ride.util;

import java.util.UUID;

public class UUIDUtil {

    public static String getUUID() {
        String result = UUID.randomUUID().toString();
        // 去掉"-"
        return result.substring(0, 8) + result.substring(9, 13) + result.substring(14, 18) +
                result.substring(19, 23) + result.substring(24);
    }
}
