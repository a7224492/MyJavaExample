package com.mycode.research.jvm.my;

import com.mycode.research.jvm.my.interpreter.Interpreter;
import com.mycode.research.jvm.my.oop.JKlass;
import com.mycode.research.jvm.my.oop.SystemDictionary;
import com.mycode.research.jvm.my.oop.parser.ClassParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangzhen
 * @date 2019/8/23 15:55
 */
public class MyJava {
    public void init(InputStream main, String[] args) throws IOException {
        JKlass klass = ClassParser.loadClassFile(main);
        SystemDictionary.addKlass(klass.getName(), klass);

        List<Object> paramList = new ArrayList<>();
        paramList.add(args);

        Interpreter interpreter = new Interpreter();
        interpreter.callMethod(klass.findMethod("main"), paramList);
    }
}
