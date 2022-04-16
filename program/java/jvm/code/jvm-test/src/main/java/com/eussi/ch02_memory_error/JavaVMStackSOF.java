package com.eussi.ch02_memory_error;
/**
 * VM Args: -Xss128k
 * @author wangxueming
 *
 */
public class JavaVMStackSOF {
	private int stackLength = 1;
	
	public void stackLeak() {
		stackLength++;
		stackLeak();
	}
	
	public static void main(String[] args) {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			/**
			 * catch捕获的是Throwable，而不是Exception。
			 * 因为StackOverflowError和OutOfMemoryError
			 * 都不属于Exception的子类。
			 */
			System.out.println("Stack length:" + oom.stackLength);
			throw e;
		}
	}
}
