package com.dtc.sample.java.concurrent;

import java.util.concurrent.CompletableFuture;
/**
 * CompletableFuture的使用用例
 * 
 * CompletableFuture 使用了Promise的编程模式（see<a href="http://java-design-patterns.com/patterns/promise/">Promise</a>）
 * 
 * CompletableFuture可以使用顺序编程的习惯来编写代码，这样的代码更得阅读和维护。
 * 与顺序编程习惯相对应的就是Callback的编程方式，Callback的编程方式是非常难以阅读和维护的。
 * 
 * @author tim
 *
 */
public class CompletableFutureTest {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("main thread:"+Thread.currentThread());
		
		CompletableFuture.supplyAsync(() -> {
			System.out.println("computed thread:"+Thread.currentThread());
			return 1;
		}).whenComplete((a, b) -> {
			System.out.println("sync thread:"+Thread.currentThread());
			System.out.println("sync result:" + a);
		}).whenCompleteAsync((a, b) -> {
			System.out.println("async thread:"+Thread.currentThread());
			System.out.println("async result:" + a);
		});

		Thread.currentThread().join();

	}

}
