package com.mycode.research.jvm.my.oop;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangzhen
 * @date 2019/8/14 18:50
 */
public class SystemDictionary {
    private static Map<String, JKlass> klassDictionary = new HashMap<>();

    public static void addKlass(String name, JKlass klass) {
        klassDictionary.put(name, klass);
    }

    public static JKlass getKlass(String name) {
        JKlass klass = klassDictionary.get(name);
        if (klass == null) {
            // 查找的类不存在，尝试加载这个类
        }

        return klass;
    }
}
