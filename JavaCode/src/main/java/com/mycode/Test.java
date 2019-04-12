package com.mycode;

/**
 * @author jiangzhen
 * @date 2019/3/29 13:58
 */
public class Test {
    public static final int _1KB = 1024;
    public static final int _1M = 1024 * _1KB;
    public static final int _1G = 1024 * _1M;

    private static final byte[] memory = new byte[_1G];

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(10000);
        }
    }
}
