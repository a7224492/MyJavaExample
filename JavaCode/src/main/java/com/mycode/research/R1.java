package com.mycode.research;

import static com.mycode.common.constants.Constants._1M;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class R1 {
    public static void main(String[] args) {
        byte[] x = new byte[25*_1M];
    }
}
