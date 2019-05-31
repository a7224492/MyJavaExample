package com.mycode.research.jvm;

/**
 * @author jiangzhen
 * @date 2019/5/27 20:46
 */
public class TestMethod {
    static {
        System.out.println("TestMethod");
    }

    public static int addNumber(int x) {
        return x + 10;
    }

    public static boolean test(String line)
    {
        switch (line)
        {
            case "finish":
                return true;
            default:
                try {
                    int value = Integer.valueOf(line);
                    System.out.println("result is " + TestMethod.addNumber(value));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return false;
        }
    }

    public int newTest(int x)
    {
        return x * 10;
    }
}
