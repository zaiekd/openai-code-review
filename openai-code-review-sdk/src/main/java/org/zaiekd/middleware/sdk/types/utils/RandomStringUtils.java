package org.zaiekd.middleware.sdk.types.utils;

import java.util.Random;

/**
 * @author lhz
 * @version 1.0
 * @create 2/16/25 12:12 AM
 */
public class RandomStringUtils {
    public static String randomNumeric(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

}
