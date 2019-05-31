package com.mycode.research.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jiangzhen
 * @date 2019/5/27 20:43
 */
public class JvmTest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean isFinish = false;
        while (!isFinish) {
            String line = br.readLine();

            System.out.println("Input : " + line);
            if (TestMethod.test(line)) {
                break;
            }
        }
    }
}
