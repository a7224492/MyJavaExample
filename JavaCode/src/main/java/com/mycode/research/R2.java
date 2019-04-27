package com.mycode.research;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class R2 {
    public static void main(String[] args) throws InterruptedException {
        List<String> l = new ArrayList<String>();
        int i = 0;
        while (true) {
            l.add(String.valueOf(i++).intern());
            Thread.sleep(10);
        }
    }
}
