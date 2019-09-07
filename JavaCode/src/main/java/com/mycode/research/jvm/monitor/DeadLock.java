package com.mycode.research.jvm.monitor;

/**
 * @author jiangzhen
 */
public class DeadLock {
	public static void main(String[] args)
		throws InterruptedException
	{
		Thread t = new Thread(() -> {
			int x = 10;
			while (true) {
				++x;
				if (x == Integer.MAX_VALUE) {
					x = 0;
				}
			}
		});

		t.start();
		t.join();
	}
}
