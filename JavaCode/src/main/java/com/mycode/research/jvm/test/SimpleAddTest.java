package com.mycode.research.jvm.test;

/**
 * @author jiangzhen
 */
public class SimpleAddTest {
	public static void main(String[] args) {
		int a = 10;
		int b = 20;
		int result = add(a, b);

		System.out.println(result);
	}

	private static int add(int x, int y) {
		return x + y;
	}
}
