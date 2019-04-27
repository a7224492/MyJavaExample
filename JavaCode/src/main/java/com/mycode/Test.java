package com.mycode;

import static com.mycode.common.constants.Constants._1G;

/**
 * @author jiangzhen
 * @date 2019/3/29 13:58
 */
public class Test {
    private static final byte[] memory = new byte[_1G];

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(10000);
        }
    }
}
