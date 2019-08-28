package com.mycode.research.jvm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jiangzhen
 * @date 2019/8/26 13:57
 */
public class SimpleAddTest2 {
    public static void main(String[] args) throws IOException {
        int a = 10;
        int b = 20;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();

        SimpleAddTest2 s2 = new SimpleAddTest2();
        int result = s2.add(a, b);

        System.out.println(result);
    }

    private int add(int x, int y) {
        return x + y;
    }
}
