package com.eussi.ch01_memory_error;

/**
 * VM Args: -Xss2M(这时候不妨设置大些）
 * 
 * @author wangxueming
 *
 */
public class JavaVMStackOOM {

	private void dontStop() {
		while(true) {
			
		}
	}
	
	public void stackLeakByThread() {
		while(true) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					dontStop();
				}
				
			});
			thread.start();
		}
	}

	public static void main(String[] args) {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.dontStop();
	}
}
