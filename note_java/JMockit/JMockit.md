[TOC]

# JMockit是什么

JMockit是一款Java类/接口/对象的Mock工具，目前广泛应用于Java应用程序的单元测试中。是用以帮助开发人员编写测试程序的一组工具和API，该项目完全基于 Java5 SE的java.lang.instrument包开发，内部使用ASM库来修改Java的Bytecode。

jmockit**全称Mocking with Jmockit**

Java Mock工具很多，比如easyMock,Mockito等等。但JMockit的API易用，丰富，写出来的Mock程序代码完全面向对象。此外，JMockit还提供了注解，支持泛型的Mock API用于对类/对象的属性，方法(支持static,private,final,native)，构造函数，初始代码块(含静态初始代码块)灵活Mock。

JMockit是一款功能强大，API易用，不可或缺的Java Mock工具。

# 配置

## maven pom.xml配置

```
<dependency>
	<groupId>org.jmockit</groupId>
	<artifactId>jmockit</artifactId>
	<version>1.37</version>
	<scope>test</scope>
</dependency>
```

## JUnit4.x及以下用户特别注意事项

如果通过mvn test来运行你的测试程序 , 请确保JMockit的依赖定义出现在JUnit的依赖之前。 

```
<!-- 先声明jmockit的依赖 -->
<dependency>
	<groupId>org.jmockit</groupId>
	<artifactId>jmockit</artifactId>
	<version>1.37</version>
	<scope>test</scope>
</dependency>

<!-- 再声明junit的依赖 -->
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.11</version>
	<scope>test</scope>
</dependency>
```

## JMockit Coverage配置

如果需要使用JMockit的代码覆盖率功能，需要在Maven pom.xml中如下定义

```
<plugin>
	<artifactId>maven-surefire-plugin</artifactId>
	<configuration>
		<argLine>-javaagent:"${settings.localRepository}/org/jmockit/jmockit/1.37/jmockit-1.37.jar=coverage"</argLine>
		<disableXmlReport>false</disableXmlReport>
		<systemPropertyVariables>
			<coverage-output>html</coverage-output>
			<coverage-outputDir>${project.build.directory}/codecoverage-output</coverage-outputDir>
			<coverage-metrics>all</coverage-metrics>
		</systemPropertyVariables>
	</configuration>
</plugin>
```

# 程序结构

1. 通过一个例子来看JMockit的程序结构

```
//JMockit的程序结构
public class ProgramConstructureTest {

    // 这是一个测试属性
    @Mocked
    HelloJMockit helloJMockit;

    @Test
    public void test1() {
        // 录制(Record)
        new Expectations() {
            {
                helloJMockit.sayHello();
                // 期待上述调用的返回是"hello,david"，而不是返回"hello,JMockit"
                result = "hello,david";
            }
        };
        // 重放(Replay)
        String msg = helloJMockit.sayHello();
        Assert.assertTrue(msg.equals("hello,david"));
        // 验证(Verification)
        new Verifications() {
            {
                helloJMockit.sayHello();

                times = 1;
            }
        };
    }

    @Test
    public void test2(@Mocked HelloJMockit helloJMockit /* 这是一个测试参数 */) {
        // 录制(Record)
        new Expectations() {
            {
                helloJMockit.sayHello();
                // 期待上述调用的返回是"hello,david"，而不是返回"hello,JMockit"
                result = "hello,david";
            }
        };
        // 重放(Replay)
        String msg = helloJMockit.sayHello();
        Assert.assertTrue(msg.equals("hello,david"));
        // 验证(Verification)
        new Verifications() {
            {
                helloJMockit.sayHello();
                // 验证helloJMockit.sayHello()这个方法调用了1次
                times = 1;
            }
        };
    }
}
```

JMockit的程序结构包含了测试属性或测试参数，测试方法，测试方法体中又包含录制代码块，重放测试逻辑，验证代码块。 

2. 测试属性&测试参数

a) 测试属性：即测试类的一个属性。它作用于测试类的所有测试方法。

在JMockit中，我们可以用JMockit的注解API来修饰它。这些API有@Mocked,@Tested,@Injectable,@Capturing。在上述例子中，我们用@Mocked修饰了测试属性HelloJMockit  helloJMockit，表示helloJMockit这个测试属性，它的实例化，属性赋值，方法调用的返回值全部由JMockit来接管，接管后，helloJMockit的行为与HelloJMockit类定义的不一样了，而是由录制脚本来定义了。@Mocked修饰后的helloJMockit，JMockit对它做了什么， 会在后续详细讲述。


b) 测试参数：即测试方法的参数。它仅作用于当前测试方法。

给测试方法加参数，原本在JUnit中是不允许的，但是如果参数加了JMockit的注解API(@Mocked,@Tested,@Injectable,@Capturing)，则是允许的。


测试参数与测试属性的不同，主要是作用域的不同。

3. Record-Replay-Verification

Record-Replay-Verification 是JMockit测试程序的主要结构。

- Record: 即先录制某类/对象的某个方法调用，在当输入什么时，返回什么。
- Replay: 即重放测试逻辑。
- Verification: 重放后的验证。比如验证某个方法有没有被调用，调用多少次。

其实，Record-Replay-Verification与JUnit程序的AAA(Arrange-Action-Assert)结构是一样的。Record对应Arrange，先准备一些测试数据，测试依赖。Replay对应Action，即执行测试逻辑。Verification对应Assert，即做测试验证。

# API

## @Mocked

1. 当@Mocked修饰一个类时

```
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
		// 自已new一个，也同样如此，方法都被mock了
		Person newPerson = new Person();
		newPerson.setName("Jenny");
		Assert.assertNull(newPerson.getName());
	}
}
```

2. 当@Mocked修饰一个接口/抽象类时

```
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
```

3. @Mocked功能总结

@Mocked修饰的类/接口，是告诉JMockit，帮我**生成一个Mocked对象，这个对象方法（包含静态方法)返回默认值**。

即**如果返回类型为原始类型(short,int,float,double,long)就返回0，如果返回类型为String就返回null，如果返回类型是其它引用类型，则返回这个引用类型的Mocked对象**（这一点，是个递归的定义，参考person.getAddress().getCity()）。

4. 什么测试场景，我们要使用@Mocked

当我们的测试程序依赖某个接口时，用@Mocked非常适合了。只需要@Mocked一个注解，JMockit就能帮我们生成这个接口的实例。 

比如在分布式系统中，我们的测试程序依赖某个接口的实例是在远程服务器端时，我们在本地构建是非常困难的，此时就交给@Mocked。又比如，需要一个包含很多方法的接口实现类，类是动态生成的，如果实现该接口，需要实现大量方法，此时用@Mocked也是很方便。

## @Injectable与@Mocked

1. @Injectable与@Mocked的不同

```
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
```

@Injectable 也是告诉 JMockit生成一个Mocked对象，但@Injectable只是针对其修饰的实例，而@Mocked是针对其修饰类的所有实例。

此外，@Injectable对类的静态方法，构造函数没有影响。因为它只影响某一个实例。

2. @Tested & @Injectable通常搭配使用

为便于演示，我们以电商网站下订单的场景为例：在买家下订单时，电商网站后台程序需要校验买家的身份（是否合法，例如是否在黑名单中），若下订单没有问题还要发邮件给买家。

```
// 邮件服务类，用于发邮件
public interface MailService {

	/**
	 * 发送邮件
	 * 
	 * @param userId
	 *            邮件接受人id
	 * @param content
	 *            邮件内容
	 * @return 发送成功了，就返回true,否则返回false
	 */
	public boolean sendMail(long userId, String content);
}

// 用户身份校验  
public interface UserCheckService {

	/**
	 * 校验某个用户是否是合法用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 合法的就返回true,否则返回false 
	 */
	public boolean check(long userId);
}

//订单服务类 ,用于下订单 
public class OrderService {
	// 邮件服务类，用于向某用户发邮件。
	MailService mailService;
	// 用户身份校验类，用于校验某个用户是不是合法用户
	@Resource
	UserCheckService userCheckService;

	// 构造函数
	public OrderService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * 下订单
	 * 
	 * @param buyerId
	 *            买家ID
	 * @param itemId
	 *            商品id
	 * @return 返回 下订单是否成功
	 */
	public boolean submitOrder(long buyerId, long itemId) {
		// 先校验用户身份
		if (!userCheckService.check(buyerId)) {
			// 用户身份不合法
			return false;
		}
		// 下单逻辑代码，
		// 省略...
		// 下单完成，给买家发邮件
		if (!this.mailService.sendMail(buyerId, "下单成功")) {
			// 邮件发送成功
			return false;
		}
		return true;
	}
}
```

假设现在我们需要测试OrderService类的submitOrder方法，可是OrderService依赖MailService,UserCheckService类，在测试过程中，我们并不想真正连结邮件服务器，也不想连结校验用户身份的服务器校验用户身份，此时@Tested与@Injectable就派上用场了。

```
//@Tested与@Injectable搭配使用
public class TestedAndInjectable {
	// @Tested修饰的类，表示是我们要测试对象,在这里表示，我想测试订单服务类。JMockit也会帮我们实例化这个测试对象
	@Tested
	private OrderService orderService;
	//测试用户ID
	private long testUserId = 123456L;
	//测试商品id
	private long testItemId = 456789L;

	// 测试注入方式
	@Test
	public void testSubmitOrder(@Injectable MailService mailService, @Injectable UserCheckService userCheckService) {
		new Expectations() {
			{
				// 当向testUserId发邮件时，假设都发成功了
				mailService.sendMail(testUserId, anyString);
				result = true;
				// 当检验testUserId的身份时，假设该用户都是合法的
				userCheckService.check(testUserId);
				result = true;
			}
		};
		//JMockit帮我们实例化了mailService了，并通过OrderService的构造函数，注入到orderService对象中。
		//JMockit帮我们实例化了userCheckService了，并通过OrderService的属性，注入到orderService对象中。 
		Assert.assertTrue(orderService.submitOrder(testUserId, testItemId));
	}
}
```

3. @Tested & @Injectable功能总结

@Injectable 也表示一个Mocked对象，相比@Mocked，只不过只影响类的一个实例。而@Mocked默认是影响类的所有实例。

@Tested表示被测试对象。**如果该对象没有赋值，JMockit会去实例化它，若@Tested的构造函数有参数，则JMockit通过在测试属性&测试参数中查找@Injectable修饰的Mocked对象注入@Tested对象的构造函数来实例化，不然，则用无参构造函数来实例化。除了构造函数的注入，JMockit还会通过属性查找的方式，把@Injectable对象注入到@Tested对象中**。

**注入的匹配规则：先类型，再名称(构造函数参数名，类的属性名)。若找到多个可以注入的@Injectable，则选择最优先定义的@Injectable对象。**

当然，我们的测试程序要尽量避免这种情况出现。因为给哪个测试属性/测试参数加@Injectable，是人为控制的。

4. 什么测试场景，我们要使用@Tested & @Injectable 

显然，当我们需要**手工管理被测试类的依赖时,就需要用到@Tested & @Injectable**。两者搭配起来用，JMockit就能帮我们轻松搞定被测试类及其依赖注入细节。

## @Capturing

1. @Capturing主要用于子类/实现类的Mock

@Capturing平时较少用到，但某些场景下，非用它不可。举个例子：通常我们的系统中，都有权限校验。我们通常用AOP来做权限校验，可是AOP生成的类是哪个，连类名都不知道，还怎么Mock?  AOP生成的类是动态生成的。可是我们在单元测试时，不希望程序卡在权限校验上（除非是为了测试权限的测试程序），这种情况下用@Capturing。

```
//@Capturing注解用途
public class CapturingTest {
	// 测试用户ID
	long testUserId = 123456l;
	// 权限检验类，可能是人工写的
	IPrivilege privilegeManager1 = new IPrivilege() {
		@Override
		public boolean isAllow(long userId) {
			if (userId == testUserId) {
				return false;
			}
			return true;
		}
	};
	// 权限检验类，可能是JDK动态代理生成。我们通常AOP来做权限校验。
	IPrivilege privilegeManager2 = (IPrivilege) Proxy.newProxyInstance(IPrivilege.class.getClassLoader(),
			new Class[] { IPrivilege.class }, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) {
					if ((long) args[0] == testUserId) {
						return false;
					}
					return true;
				}
			});

	// 有Cautring情形
	@Test
	public void testCaputring(@Capturing IPrivilege privilegeManager) {
		// 加上了JMockit的API @Capturing,
		// JMockit会帮我们实例化这个对象，它除了具有@Mocked的特点，还能影响它的子类/实现类
		new Expectations() {
			{
				// 对IPrivilege的所有实现类录制，假设测试用户有权限
				privilegeManager.isAllow(testUserId);
				result = true;
			}
		};
		// 不管权限校验的实现类是哪个，这个测试用户都有权限
		Assert.assertTrue(privilegeManager1.isAllow(testUserId));
		Assert.assertTrue(privilegeManager2.isAllow(testUserId));
	}
	// 没有Cautring情形
	@Test
	public void testWithoutCaputring() {
		// 不管权限校验的实现类是哪个，这个测试用户没有权限
		Assert.assertTrue(!privilegeManager1.isAllow(testUserId));
		Assert.assertTrue(!privilegeManager2.isAllow(testUserId));
	}
}
```

2. 什么测试场景用@Capturing 

当只知道父类或接口时，但**需要控制它所有子类的行为时**，子类可能有多个实现（可能有人工写的，也可能是AOP代理自动生成时），用@Capturing（注意加粗字体）。

## Expectations

通过上面的例子，我们已经了解了Expectations的作用主要是用于录制。即录制类/对象的调用，返回值是什么。

录制脚本规范

```
new Expectations() {
    // 这是一个Expectations匿名内部类
    {
        //这是这个内部类的初始化代码块，我们在这里写录制脚本，脚本的格式要遵循下面的约定
        //方法调用(可是类的静态方法调用，也可以是对象的非静态方法调用)
        //result赋值要紧跟在方法调用后面
        //...其它准备录制脚本的代码
        //方法调用
        //result赋值
    }
};
 
还可以再写new一个Expectations，只要出现在重放阶段之前均有效。
new Expectations() {
      
    {
         //...录制脚本
    }
};
```

Expectations主要有两种使用方式。

1. 通过引用外部类的Mock对象(@Injectabe,@Mocked,@Capturing)来录制

```
//Expectations对外部类的mock对象进行录制
public class ExpectationsTest {
    @Mocked
    Calendar cal;

    @Test
    public void testRecordOutside() {
        new Expectations() {
            {
                // 对cal.get方法进行录制，并匹配参数 Calendar.YEAR
                cal.get(Calendar.YEAR);
                result = 2016;// 年份不再返回当前小时。而是返回2016年
                // 对cal.get方法进行录制，并匹配参数 Calendar.HOUR_OF_DAY
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时不再返回当前小时。而是返回早上7点钟
            }
        };
        Assert.assertTrue(cal.get(Calendar.YEAR) == 2016);
        Assert.assertTrue(cal.get(Calendar.HOUR_OF_DAY) == 7);
        // 因为没有录制过，所以这里月份返回默认值 0
        Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == 0);
    }
}
```

在这个例子中，在Expectations匿名内部类的初始代码块中，可以对外部类的任意成员变量，方法进行调用。

2. 通过构建函数注入类/对象来录制

在上面的例子中，我们通过引用外部类的Mock对象(@Injectabe,@Mocked,@Capturing)来录制，可是无论是@Injectabe,@Mocked,@Capturing哪种Mock对象，都是对类的方法都mock了，可是有时候，我们只希望JMockit只mock类/对象的某一个方法？

```
//通过Expectations对其构造函数mock对象进行录制
public class ExpectationsConstructorTest2 {

    // 把类传入Expectations的构造函数
    @Test
    public void testRecordConstrutctor1() {
        Calendar cal = Calendar.getInstance();
        // 把待Mock的类传入Expectations的构造函数，可以达到只mock类的部分行为的目的
        new Expectations(Calendar.class) {
            {
                // 只对get方法并且参数为Calendar.HOUR_OF_DAY进行录制
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时永远返回早上7点钟
            }
        };
        Calendar now = Calendar.getInstance();
        // 因为下面的调用mock过了，小时永远返回7点钟了
        Assert.assertTrue(now.get(Calendar.HOUR_OF_DAY) == 7);
        // 因为下面的调用没有mock过，所以方法的行为不受mock影响，
        Assert.assertTrue(now.get(Calendar.DAY_OF_MONTH) == (new Date()).getDate());
    }

    // 把对象传入Expectations的构造函数
    @Test
    public void testRecordConstrutctor2() {
        Calendar cal = Calendar.getInstance();
        // 把待Mock的对象传入Expectations的构造函数，可以达到只mock类的部分行为的目的，但只对这个对象影响
        new Expectations(cal) {
            {
                // 只对get方法并且参数为Calendar.HOUR_OF_DAY进行录制
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时永远返回早上7点钟
            }
        };

        // 因为下面的调用mock过了，小时永远返回7点钟了
        Assert.assertTrue(cal.get(Calendar.HOUR_OF_DAY) == 7);
        // 因为下面的调用没有mock过，所以方法的行为不受mock影响，
        Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == (new Date()).getDate());

        // now是另一个对象，上面录制只对cal对象的影响，所以now的方法行为没有任何变化
        Calendar now = Calendar.getInstance();
        // 不受mock影响
        Assert.assertTrue(now.get(Calendar.HOUR_OF_DAY) == (new Date()).getHours());
        // 不受mock影响
        Assert.assertTrue(now.get(Calendar.DAY_OF_MONTH) == (new Date()).getDate());
    }
}
```

通过Expectations构造函数，传入具体的一个实例或者class类，实现只对该类的一个实例生效或者所有实例生效。

## MockUp & @Mock

MockUp & @Mock提供的Mock方式，Mock方式最直接

```
//Mockup & @Mock的Mock方式
public class MockUpTest {

    @Test
    public void testMockUp() {
        // 对Java自带类Calendar的get方法进行定制
        // 只需要把Calendar类传入MockUp类的构造函数即可
        new MockUp<Calendar>(Calendar.class) {
            // 想Mock哪个方法，就给哪个方法加上@Mock， 没有@Mock的方法，不受影响
            @Mock
            public int get(int unit) {
                if (unit == Calendar.YEAR) {
                    return 2017;
                }
                if (unit == Calendar.MONDAY) {
                    return 12;
                }
                if (unit == Calendar.DAY_OF_MONTH) {
                    return 25;
                }
                if (unit == Calendar.HOUR_OF_DAY) {
                    return 7;
                }
                return 0;
            }
        };
        // 从此Calendar的get方法，就沿用你定制过的逻辑，而不是它原先的逻辑。
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        Assert.assertTrue(cal.get(Calendar.YEAR) == 2017);
        Assert.assertTrue(cal.get(Calendar.MONDAY) == 12);
        Assert.assertTrue(cal.get(Calendar.DAY_OF_MONTH) == 25);
        Assert.assertTrue(cal.get(Calendar.HOUR_OF_DAY) == 7);
        // Calendar的其它方法，不受影响
        Assert.assertTrue((cal.getFirstDayOfWeek() == Calendar.MONDAY));
    }
}
```

MockUp & @Mock能帮我们解决大部分的Mock场景，其使用方式最直接。

比如下面的场景是MockUp & @Mock做不到的。

1. 一个类有多个实例。只对其中某1个实例进行mock。 
    最新版的JMockit已经让MockUp不再支持对实例的Mock了。1.19之前的老版本仍支持。
2. AOP动态生成类的Mock。
3. 对类的所有方法都需要Mock时，书写MockUp的代码量太大。
   比如web程序中，经常需要对HttpSession进行Mock。若用MockUp你要写大量的代码，可是用@Mocked就一行代码就可以搞定。

MockUp & @Mock的确是好东西，在实际Mock场景中，我们需要灵活运用JMockit其它的Mock API。让我们的Mock程序简单，高效。

MockUp & @Mock比较适合于一个项目中，用于对一些通用类的Mock，以减少大量重复的new Exceptations{{}}代码。

## Verifications

Verifications是用于做验证。验证Mock对象（即@Moked/@Injectable@Capturing修饰的或传入Expectation构造函数的对象)有没有调用过某方法，调用了多少次。与Exceptations的写法相似。如下：

```
new Verifications() {
    // 这是一个Verifications匿名内部类
    {
          // 这个是内部类的初始化代码块，我们在这里写验证脚本，脚本的格式要遵循下面的约定
        //方法调用(可是类的静态方法调用，也可以是对象的非静态方法调用)
        //times/minTimes/maxTimes 表示调用次数的限定要求。赋值要紧跟在方法调用后面，也可以不写（表示只要调用过就行，不限次数）
        //...其它准备验证脚本的代码
        //方法调用
        //times/minTimes/maxTimes 赋值
    }
};
  
还可以再写new一个Verifications，只要出现在重放阶段之后均有效。
new Verifications() {
       
    {
         //...验证脚本
    }
};
```

例子：

```
//Verification的使用
public class VerificationTest {
    @Test
    public void testVerification() {
        // 录制阶段
        Calendar cal = Calendar.getInstance();
        new Expectations(Calendar.class) {
            {
                // 对cal.get方法进行录制，并匹配参数 Calendar.YEAR
                cal.get(Calendar.YEAR);
                result = 2016;// 年份不再返回当前小时。而是返回2016年
                cal.get(Calendar.HOUR_OF_DAY);
                result = 7;// 小时不再返回当前小时。而是返回早上7点钟
            }
        };
        // 重放阶段
        Calendar now = Calendar.getInstance();
        Assert.assertTrue(now.get(Calendar.YEAR) == 2016);
        Assert.assertTrue(now.get(Calendar.HOUR_OF_DAY) == 7);
        // 验证阶段
        new Verifications() {
            {
                Calendar.getInstance();
                // 限定上面的方法只调用了1次，当然也可以不限定
                times = 1;
                cal.get(anyInt);
                // 限定上面的方法只调用了2次，当然也可以不限定
                times = 2;
            }
        };
    }
}
```

通常，在实际测试程序中，更倾向于通过JUnit/TestNG/SpringTest的Assert类对测试结果的验证，  对类的某个方法有没调用，调用多少次的测试场景并不是太多。因此在验证阶段，我们完全可以用JUnit/TestNG/SpringTest的Assert类取代new Verifications() {{}验证代码块。

除非，你的测试程序关心类的某个方法有没有调用，调用多少次，你可以使用new Verifications() {{}}验证代码块。

如果你还关心方法的调用顺序，你可以使用new VerificationsInOrder() {{}}。这里不做详细的介绍了。

