package com.mycode.research.jvm.thread;

/**
 * @author jiangzhen
 */
public class ThreadTest {
	public static void main(String[] args)
		throws InterruptedException
	{
		Thread t = new Thread(() -> {
			System.out.println(Thread.currentThread().getName());
		});

		t.start();
		System.out.println(Thread.currentThread().getName());
		t.join();
	}
}
