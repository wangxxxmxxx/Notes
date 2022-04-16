package com.eussi.ch02_memory_error;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author wangxueming
 *
 * cglib-3.2.5.jar
 * asm-5.2.jar
 */
public class JavaMethodAreaOOM {
	public static void main(String[] args) {
		try {
			while(true) {
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(OOMObject.class);
				enhancer.setUseCache(false);
				enhancer.setCallback(new MethodInterceptor() {
					
					@Override
					public Object intercept(Object obj, Method method
							, Object[] args, MethodProxy proxy) throws Throwable {
						return proxy.invoke(obj, args);
					}
				});
				enhancer.create();
			}
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}
	static class OOMObject {
	}
}
