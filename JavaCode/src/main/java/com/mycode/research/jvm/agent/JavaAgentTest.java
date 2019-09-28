package com.mycode.research.jvm.agent;

/**
 * @author jiangzhen
 * @date 2019/9/24 20:02
 */
public class JavaAgentTest {
    public static void main(String[] args) {
        System.out.println("Hello World");
        test(1,22,333);
    }

    public static void test(int x, int y, int z) {
        System.out.println(String.valueOf(x));
    }

    public static void writeStep(int x) {
        System.out.println("x = " + x);
    }
}
