package com.javacode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangzhen
 * @date 2019/4/24 15:16
 */
public class Test {
    public static void main(String[] args) {
        Map<String, Student> m = new HashMap<>();
        m.put("jiangzhen", new Student("jiangzhen", 25));
        m.put("chuangwang", new Student("chuangwang", 25));
        m.put("shafuzi", new Student("shafuzi", 25));
        m.put("zhangdoudou", new Student("zhangdoudou", 25));

        System.out.println(m);
    }
}

class Student
{
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
