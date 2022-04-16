package com.eussi.basic;

import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;


//@Mocked注解用途
public class MockedClassTest {
	// 加上了JMockit的API @Mocked, JMockit会帮我们实例化这个对象，不用担心它为null
	@Mocked
	private Person person;

	// 当@Mocked作用于class
	@Test
	public void testMockedClass() {
		// 静态方法不起作用了,返回了null
		Assert.assertNull(Person.tellType());
		// 非静态方法（返回类型为String）也不起作用了，返回了null
		Assert.assertNull(person.getName());
		Assert.assertNull(person.getAddress().getCity());
		// 自已new一个，也同样如此，方法都被mock了
		Person newPerson = new Person();
		newPerson.setName("Jenny");
		Assert.assertNull(newPerson.getName());
	}
}
