package com.eussi.basic;

import mockit.Injectable;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;

//@Mocked与@Injectable的不同
public class MockedAndInjectable {

	@Test
	public void testMocked(@Mocked Person person) {
		// 静态方法不起作用了,返回了null
		Assert.assertNull(Person.tellType());
		// 非静态方法（返回类型为String）也不起作用了，返回了null
		Assert.assertNull(person.getName());
		// 自已new一个，也同样如此，方法都被mock了
		Person newPerson = new Person();
		newPerson.setName("Jenny");
		Assert.assertNull(newPerson.getName());
	}

	@Test
	public void testInjectable(@Injectable Person person) {
		// 静态方法不mock
		Assert.assertNotNull(Person.tellType());
		// 非静态方法（返回类型为String）也不起作用了，返回了null,但仅仅限于Person这个对象
		Assert.assertNull(person.getName());
		// 自已new一个，并不受影响
		Person newPerson = new Person();
		newPerson.setName("Jenny");
		Assert.assertEquals("Jenny", newPerson.getName());
	}
}
