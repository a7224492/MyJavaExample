package com.mycode.research.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: jiangzhen
 * Date: 2019/5/25 15:14
 */
public class LogbackTest1 {
    private static final Logger logger = LoggerFactory.getLogger(LogbackTest1.class);

    public static void main(String[] args) {
        logger.debug("Hello Logback!");
    }
}
