package com.dtc.sample.java.concurrent;

/**
 * 
 * InheritableThreadLocal用例
 * 
 * <p>
 * InheritableThreadLocal与ThreadLocal的区别是通过InheritableThreadLocal 可以获取到父线程的本地变量。
 * <p>
 * 
 * <p>
 * ThreadLocal的实现原理：
 * Thread对象中存在一个属性threadLocalMap，该属性持有Entry[]的数据结构，Entry由ThreadLocal和value组成。
 * </p>
 * 
 * <p>
 * InheritableThreadLocal的实现原理：
 * InheritableThreadLocal与ThreadLocal的实现原理相同，但InheritableThreadLocal使用的是Thread对象另一个属性inheritableThreadLocalMap，该属性持有Entry[]的数据结构，Entry由ThreadLocal和value组成。
 * 在创建线程时，会从父线程中复制inheritableThreadLocalMap。
 * <p>
 * 
 * @author tim
 *
 */
public class InheritableThreadLocalTest {

	public static void main(String[] args) throws InterruptedException {
		ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>();

		threadLocal.set(20);

		new Thread() {
			public void run() {
				Integer pv = threadLocal.get();
				System.out.println(pv == 20);
			};
		}.start();

		Thread.currentThread().join();
	}

}
