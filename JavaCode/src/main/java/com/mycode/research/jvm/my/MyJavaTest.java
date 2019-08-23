package com.mycode.research.jvm.my;

import com.mycode.research.jvm.my.interpreter.Interpreter;
import com.mycode.research.jvm.my.oop.JKlass;
import com.mycode.research.jvm.my.oop.SystemDictionary;
import com.mycode.research.jvm.my.oop.parser.ClassParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangzhen
 * @date 2019/8/12 21:00
 */
public class MyJavaTest {
    public static void main(String[] args) throws IOException {
        args = new String[]{
                "C:\\Users\\jiangzhen\\Desktop\\KodgamesTool\\JavaCode\\target\\classes\\com\\mycode\\research\\jvm\\test\\SimpleAddTest.class"
        };

        Interpreter interpreter = new Interpreter();

        FileInputStream fileIn = new FileInputStream(args[0]);
        JKlass klass = ClassParser.loadClassFile(fileIn);
        SystemDictionary.addKlass(klass.getName(), klass);

        List<Object> paramList = new ArrayList<>();
        paramList.add(args);
        interpreter.callMethod(klass.findMethod("main"), paramList);
    }
}
