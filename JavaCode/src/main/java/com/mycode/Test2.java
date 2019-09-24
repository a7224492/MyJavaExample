package com.mycode;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangzhen
 * @date 2019/3/29 13:58
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        String s1 = "abcd";
        String s2 = "ab" + "cd";
        String s3 = "ab";
        String s4 = "cd";
        String s5 = s3+s4;

        System.out.println(s5 == s1);
        System.out.println(s1 == s2);
    }
}
