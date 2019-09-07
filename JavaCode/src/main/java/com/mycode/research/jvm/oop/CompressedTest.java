package com.mycode.research.jvm.oop;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试hotspot的-XX:UseCompressedOops选项的实际作用效果
 *
 * @author jiangzhen
 */
public class CompressedTest {
	public static void main(String[] args) throws InterruptedException {
		List<Student> testList = new ArrayList<>();
		for (int i = 0; i < 10000; ++i) {
			testList.add(new Student("jiangzhen", i+1));
		}

		Thread.sleep(1000*1000);
	}
}

class Student {
	private String name;
	private int age;

	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
}
