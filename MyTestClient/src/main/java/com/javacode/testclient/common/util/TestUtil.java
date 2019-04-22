package com.javacode.testclient.common.util;

/**
 * @author jiangzhen
 * @date 2019/4/22 13:00
 */
public class TestUtil {
    public static void assertTrue(boolean result) {
        if (!result) {
            System.err.println("assertTrue fails!!!");
            System.exit(-1);
        }
    }
}
