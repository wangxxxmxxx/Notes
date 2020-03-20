# redis笔记

------

## redis的优势
####  数据存储结构丰富：

1. 字符类型
2. 散列类型	
3. 列表类型
4. 集合类型
5. 有序集合

#### 功能

1.	可以为每个key设置超时时间；
2.	可以通过列表类型来实现分布式队列的操作
3.	支持发布订阅的消息模式

#### 简单
1. 提供了很多命令与redis进行交互

## redis的应用场景

1. 数据缓存（商品数据、新闻、热点数据）
2. 单点登录
3. 秒杀、抢购
4. 网站访问排名
5. 应用的模块开发

## 安装下载及命令简介

#### 下载地址
- http://download.redis.io/releases/
- http://download.redis.io/releases/redis-3.2.8.tar.gz  #下载3.2.8版本
#### 安装

解压：

```
tar -zxvf redis-3.2.8.tar.gz 
```
编译：

```
cd redis-3.2.8/
make
	#报错：Redis make[1]: *** [adlist.o] Error 127
	yum install -y gcc-c++
make distclean	#注意先清理再编译
make
make test #可选
	报错：You need tcl 8.5 or newer in order to run the Redis test
 	wget http://downloads.sourceforge.net/tcl/tcl8.6.1-src.tar.gz 
 	tar xzvf tcl8.6.1-src.tar.gz  -C /usr/local/
 	cd  /usr/local/tcl8.6.1/unix/
 	./configure 
 	make
 	make install
make test
	\o/ All tests passed without errors!
 	Cleanup: may take some time... OK
	make[1]: Leaving directory `/root/redis-3.2.8/src'
make install #默认安装在src目录下，可以选择安装另一目录：make install PREFIX=/root/redis 直接执行，执行make install后再执行也可以。
 			
```

####  启停

安装源码后，如果安装在另外一个目录，把配置文件复制一份，源码目录可以删除了，需要的文件如下：


	[root@app1 ~]# find redis/ -type f
	redis/bin/redis-server
	redis/bin/redis-benchmark
	redis/bin/redis-cli
	redis/bin/redis-check-rdb
	redis/bin/redis-check-aof
	redis/bin/dump.rdb
	redis/redis.conf
启动命令：

```
./redis-server ../redis.conf	#默认前台启动，后台启动，修改配置文件daemonize属性为yes
```

关闭命令：

```
./redis-cli shutdown
```

连接：

```
./redis-cli [-h xxx.xxx.xxx.xxx -p xxx]		#默认端口6379
#默认情况下，连接redis只能通过127.0.0.1连接，如需要其他地址，或者远程连接，修改配置文件：
	protected-mode no
	#bind 127.0.0.1			#绑定的网卡
```

其他命令说明

```
redis-server 启动服务
redis-cli 访问到redis的控制台
redis-benchmark 性能测试的工具
redis-check-aof aof文件进行检测的工具
redis-check-dump  rdb文件检查工具
redis-sentinel  sentinel服务器配置
```

## 多数据库支持

1. 默认支持16个数据库，具体看配置文件说明：
```
# Set the number of databases. The default database is DB 0, you can select
# a different one on a per-connection basis using SELECT <dbid> where
# dbid is a number between 0 and 'databases'-1
```

2. 说明：
   redis分数据库，可以理解为一个命名空间
   跟关系型数据库不一样的点：

			1.	redis不支持自定义数据库名词
			2.	每个数据库不能单独设置授权
			3.	每个数据库之间并不是完全隔离的。 可以通过flushall命令清空redis实例面的所有数据库中的数据
	3-通过select dbid 去选择不同的数据库命名空间 。 默认情况下：dbid的取值范围默认是0 -15
		[root@app2 bin]# ./redis-cli 
		127.0.0.1:6379> select 7
		OK
		127.0.0.1:6379[7]> select 10
		OK
		127.0.0.1:6379[10]> select 16
		(error) ERR invalid DB index
		127.0.0.1:6379[10]>
5）简单使用入门
	1-获得一个符合匹配规则的键名列表：keys pattern  [? | * | []], 注意后面表达式越不清晰越消耗性能
		[root@app2 bin]# ./redis-cli 
		127.0.0.1:6379> SET runoob1 redis
		OK
		127.0.0.1:6379> SET runoob2 mysql
		OK
		127.0.0.1:6379> SET runoob3 mongodb
		OK
		127.0.0.1:6379> keys runoob*
		1) "runoob3"
		2) "runoob2"
		3) "runoob1"
	2-判断一个键是否存在：exists key 
	3-去获得这个key的数据结构类型： type key 
6）数据结构的使用
	1-字符类型
		1. 一个字符类型的key默认存储的最大容量是512M
		2. 赋值和取值
					SET key value
					GET key
		3. 递增数字:incr key
					递增为原子操作
						127.0.0.1:6379> set age 10
						OK
						127.0.0.1:6379> incr age
						(integer) 11
						127.0.0.1:6379> incr age
						(integer) 12
						127.0.0.1:6379> incr age
						(integer) 13
					错误的操作，程序中递增，线程不安全：
						int value= get key;
						value =value +1;
						set key value;
					应用：
						短信重发机制：sms:limit:mobile expire 
							对某电话号码进行次数限制，并设置过期时间，防止过期时间内重发次数超过该数量
		4. key的设计
					命名不建议太长，格式可以为：
						对象类型:对象id:对象属性:对象子属性
					建议对key进行分类，同步在自己公司的wiki中统一管理
		5. 其他操作：
					incryby key increment  			递增指定的整数,如：incryby age 6
					decr key   						原子递减
					append key value   				向指定的key追加字符串
					strlen  key  					获得key对应的value的长度
					mget  key [key1 ...]  			同时获得多个key的value，减少网络的传输
					mset key value [key value ...] 	同时设置多个key，value
					setnx							「SET if Not eXists」的缩写，也就是只有不存在的时候才设置，可以利用它来实现锁的效果
	2-列表类型
		1. list, 可以存储一个有序的字符串列表
					LPUSH/RPUSH： 从左边或者右边push数据：LPUSH/RPUSH key value value ...
					LPOP/RPOP : 从左边或者右边取数据
						127.0.0.1:6379> lpush test 11
						(integer) 1
						127.0.0.1:6379> lpush test 12
						(integer) 2
						127.0.0.1:6379> lpush test 13
						(integer) 3
						127.0.0.1:6379> rpush test 10
						(integer) 4
						127.0.0.1:6379> lpop test
						"13"
						127.0.0.1:6379> lpop test
						"12"
						127.0.0.1:6379> lpop test
						"11"
						127.0.0.1:6379> lpop test
						"10"
						127.0.0.1:6379> lpop test
						(nil)
		2. 应用场景
					可以用来做分布式消息队列
		3. 其他操作：
					llen key  				获得列表的长度
					lrange key start stop   索引可以是负数,-1表示最右边的第一个元素，lrange key 0 -1 # 展示列表所有元素
					lrem key count value	删除列表中count个值为value的元素
					lset key index value	设置列表中index位置的值为value
	3-散列类型
		1. 结构为：hash key value  不支持数据类型的嵌套
					比较适合存储对象：
						person
							age  18
							sex   男
							name li
							..
		2. 添加与获取
					hset key field value
					hget key filed
					如：
						127.0.0.1:6379> hset person age 18
						(integer) 1
						127.0.0.1:6379> hset person sex man
						(integer) 1
						127.0.0.1:6379> hset person name li
						(integer) 1
						127.0.0.1:6379> hget person name
						"li"
		3. 其他操作
					hmset key filed value [filed value …]  一次性设置多个属性值
					hmget key field field …  一次性获得多个值
					hgetall key  获得hash的所有信息，包括key和value
					hexists key field 判断字段是否存在。 存在返回1. 不存在返回0
					hincryby
					hsetnx
					hdel key field [field …] 删除一个或者多个字段
	4-集合类型
		1. set 跟list 不一样的点。 集合类型不能存在重复的数据。而且是无序的
		2. 添加与删除
					sadd key member [member ...] 	增加数据； 如果value已经存在，则会忽略存在的值，并且返回成功加入的元素的数量
					srem key member  				删除元素
					smembers key 					获得所有数据
		3. 其他操作
					sdiff key key ...  	对多个集合执行差集运算，差集来自于第一个key
					sunion key key ...	对多个集合执行并集操作, 同时存在在两个集合里的所有值，不存在的集合视为空集
	5-有序集合
		zadd key score member
		zrange key start stop [withscores] 去获得元素。 withscores是可以获得元素的分数
					如果两个元素的score是相同的话，那么根据(0<9<A<Z<a<z) 方式从小到大
7）redis的事务处理
	1-语法
		MULTI 	开启事务
		EXEC 	执行事务
		DISCARD	取消当前事务
	2-注意，运行时出错的事务不回滚：
		127.0.0.1:6379> multi
		OK
		127.0.0.1:6379> set aa hello
		QUEUED
		127.0.0.1:6379> incr aa
		QUEUED
		127.0.0.1:6379> set bb 1
		QUEUED
		127.0.0.1:6379> exec
		1) OK
		2) (error) ERR value is not an integer or out of range
		3) OK
		127.0.0.1:6379> get aa
		"hello"
8）过期时间
	expire key seconds 设置过期时间
	ttl key 			获得key的过期时间，随时间变化改变
9）发布订阅（使用较少）
	publish channel message
	subscribe channel [ ... ]
10）分布式锁
	1-锁是用来解决什么问题的;
		1.	一个进程中的多个线程，多个线程并发访问同一个资源的时候，如何解决线程安全问题。
		2.	一个分布式架构系统中的两个模块同时去访问一个文件对文件进行读写操作
		3.	多个应用对同一条数据做修改的时候，如何保证数据的安全性
		在进程中，我们可以用到synchronized、lock之类的同步操作去解决，但是对于分布式架构下多进程的情况下，如何做到跨进程的锁。就需要借助一些第三方手段来完成

	2-实现：
		1）数据库
			通过唯一约束创建表：
				lock(
				  id  int(11)
				  methodName  varchar(100),
				  memo varchar(1000) 
				  modifyTime timestamp
				 unique key mn (method)  --唯一约束
				)
			获取锁的伪代码：
				try{
					exec  insert into lock(methodName,memo) values(‘method’,’desc’);    method
					return true;
				}Catch(DuplicateException e){
					return false;
				}
			释放锁
				delete from lock where methodName=’’;
			存在的需要思考的问题
				1.	锁没有失效时间，一旦解锁操作失败，就会导致锁记录一直在数据库中，其他线程无法再获得到锁
				2.	锁是非阻塞的，数据的insert操作，一旦插入失败就会直接报错。没有获得锁的线程并不会进入排队队列，要想再次获得锁就要再次触发获得锁操作
				3.	锁是非重入的，同一个线程在没有释放锁之前无法再次获得该锁
		2）zookeeper实现分布式锁
			1-利用zookeeper的唯一节点特性或者有序临时节点特性获得最小节点作为锁. zookeeper 的实现相对简单，通过curator客户端，已经对锁的操作进行了封装
			2-zookeeper的优势
				1.	可靠性高、实现简单
				2.	zookeeper因为临时节点的特性，如果因为其他客户端因为异常和zookeeper连接中断了，那么节点会被删除，意味着锁会被自动释放
				3.	zookeeper本身提供了一套很好的集群方案，比较稳定
				4.	释放锁操作，会有watch通知机制，也就是服务器端会主动发送消息给客户端这个锁已经被释放了
			3-说明：
				假设服务器1，创建了一个临时节点/lock，成功了，那服务器1就获取了锁，服务器2再去创建相同的锁，就会失败，这个时候就只能监听这个节点的变化。等到服务器1处理完业务，删除了节点后，他就会得到通知，然后去创建同样的节点，获取锁处理业务，再删除节点，后续的100台服务器与之类似。注意这里的100台服务器并不是挨个去执行上面的创建节点的操作，而是并发的，当服务器1创建成功，那么剩下的99个就都会注册监听这个节点，等通知，以此类推。
				但是我们可能还需要注意到一点，就是惊群效应：举一个很简单的例子，当你往一群鸽子中间扔一块食物，虽然最终只有一个鸽子抢到食物，但所有鸽子都会被惊动来争夺，没有抢到...就是当服务器1节点有变化，会通知其余的99个服务器，但是最终只有1个服务器会创建成功，这样98还是需要等待监听，那么为了处理这种情况，就需要用到临时顺序性节点。大致意思就是，之前是所有99个服务器都监听一个节点，现在就是每一个服务器监听自己前面的一个节点。
				假设100个服务器同时发来请求，这个时候会在/lock节点下创建100个临时顺序性节点/lock/000000001，/lock/000000002，一直到/lock/000000100，这个编号就等于是已经给他们设置了获取锁的先后顺序了。当001节点处理完毕，删除节点后，002收到通知，去获取锁，开始执行，执行完毕，删除节点，通知003~以此类推。
		3）基于缓存的分布式锁实现
			1. redis中有一个setNx命令，这个命令只有在key不存在的情况下为key设置值。所以可以利用这个特性来实现分布式锁的操作
			2. 实现代码见本目录：redisdemo
			3. 说明：
				避免死锁方式：
					1-第一种就是在set完key之后，直接设置key的有效期 "expire key timeout" ，为key设置一个超时时间，单位为second，超过这个时间锁会自动释放，避免死锁。这种方式相当于，把锁持有的有效期，交给了Redis去控制。如果时间到了，你还没有给我删除key，那Redis就直接给你删了，其他服务器就可以继续去setnx获取锁。
					2-第二种方式，就是把删除key权利交给其他的服务器，那这个时候就需要用到value值了，比如服务器1，设置了value也就是timeout为当前时间+1秒 ，这个时候服务器2通过get发现时间已经超过系统当前时间了，那就说明服务器1没有释放锁，服务器1可能出问题了，服务器2就开始执行删除key操作，并且继续执行setnx操作。
						但是这块有一个问题，也就是不光你服务器2可能会发现服务器1超时了，服务器3也可能会发现，如果刚好服务器2 setnx操作完成，服务器3就接着删除，是不是服务器3也可以setnx成功了？
						那就等于是服务器2和服务器3都拿到锁了，那就问题大了。这个时候怎么办呢？
						这个时候需要用到“GETSET  key value”命令了。这个命令的意思就是获取当前key的值，并且设置新的值。
						假设服务器2发现key过期了，开始调用getset命令，然后用获取的时间判断是否过期，如果获取的时间仍然是过期的，那就说明拿到锁了。
						如果没有，则说明在服务2执行getset之前，服务器3可能也发现锁过期了，并且在服务器2之前执行了getset操作，重新设置了过期时间。
						那么服务器2就需要放弃后续的操作，继续等待服务器3释放锁或者去监测key的有效期是否过期。
						这块其实有一个小问题是，服务器3已经修改了有效期，拿到锁之后，服务器2也修改了有效期，但是没能拿到锁，但是这个有效期的时间已经被在服务器3的基础上有增加一些，但是这种影响其实还是很小的，几乎可以忽略不计。
11）Redis性能关于多路复用机制
	linux的内核会把所有外部设备都看作一个文件来操作，对一个文件的读写操作会调用内核提供的系统命令，返回一个 file descriptor（文件描述符）。对于一个socket的读写也会有相应的描述符，称为socketfd(socket 描述符)。而IO多路复用是指内核一旦发现进程指定的一个或者多个文件描述符IO条件准备好以后就通知该进程
	IO多路复用又称为事件驱动，操作系统提供了一个功能，当某个socket可读或者可写的时候，它会给一个通知。当配合非阻塞socket使用时，只有当系统通知我哪个描述符可读了，我才去执行read操作，可以保证每次read都能读到有效数据。操作系统的功能通过select/pool/epoll/kqueue之类的系统调用函数来使用，这些函数可以同时监视多个描述符的读写就绪情况，这样多个描述符的I/O操作都能在一个线程内并发交替完成，这就叫I/O多路复用，这里的复用指的是同一个线程
	多路复用的优势在于用户可以在一个线程内同时处理多个socket的io请求。达到同一个线程同时处理多个IO请求的目的。而在同步阻塞模型中，必须通过多线程的方式才能达到目的
12）redis中使用lua脚本
	1-lua脚本
		Lua是一个高效的轻量级脚本语言，用标准C语言编写并以源代码形式开放， 其设计目的是为了嵌入应用程序中，从而为应用程序提供灵活的扩展和定制功能
	2-使用脚本的好处
		1. 减少网络开销，在Lua脚本中可以把多个命令放在同一个脚本中运行
		2. 原子操作，redis会将整个脚本作为一个整体执行，中间不会被其他命令插入。换句话说，编写脚本的过程中无需担心会出现竞态条件
		3. 复用性，客户端发送的脚本会永远存储在redis中，这意味着其他客户端可以复用这一脚本来完成同样的逻辑 
	3-Lua在linux中的安装
			官网下载lua的tar.gz的源码包：tar -zxvf lua-5.3.0.tar.gz
			进入解压的目录：
				cd lua-5.2.0
				make linux  (linux环境下编译)
				make install
				如果报错，说找不到readline/readline.h, 可以通过yum命令安装
					yum -y install readline-devel ncurses-devel
				安装完以后再make linux  / make install
				最后，直接输入 lua命令即可进入lua的控制台
	4-lua的语法
			略
	5-Redis与Lua
		1. 在Lua脚本中调用Redis命令，可以使用redis.call函数调用。比如我们调用string类型的命令
				redis.call(‘set’,’hello’,’world’)
				redis.call 函数的返回值就是redis命令的执行结果。前面介绍过redis的5中类型的数据返回的值的类型也都不一样。redis.call函数会将这5种类型的返回值转化对应的Lua的数据类型
		2. 从Lua脚本中获得返回值
				在很多情况下我们都需要脚本可以有返回值，在脚本中可以使用return 语句将值返回给redis客户端，通过return语句来执行，如果没有执行return，默认返回为nil。
		3. 如何在redis客户端中执行lua脚本
				Redis提供了EVAL命令可以使开发者像调用其他Redis内置命令一样调用脚本。
					[EVAL]  [脚本内容] [key参数的数量]  [key …] [arg …]
				可以通过key和arg这两个参数向脚本中传递数据，他们的值可以在脚本中分别使用KEYS和ARGV 这两个类型的全局变量访问。比如我们通过脚本实现一个set命令，通过在redis客户端中调用，那么执行的语句是：
					lua脚本的内容为： return redis.call(‘set’,KEYS[1],ARGV[1])         //KEYS和ARGV必须大写
					eval "return redis.call('set',KEYS[1],ARGV[1])" 1 hello world
				EVAL命令是根据 key参数的数量-也就是上面例子中的1来将后面所有参数分别存入脚本中KEYS和ARGV两个表类型的全局变量。当脚本不需要任何参数时也不能省略这个参数。如果没有参数则为0
					eval "return redis.call(‘get’,’hello’)" 0
		4. EVALSHA命令
				考虑到我们通过eval执行lua脚本，脚本比较长的情况下，每次调用脚本都需要把整个脚本传给redis，比较占用带宽。为了解决这个问题，redis提供了EVALSHA命令允许开发者通过脚本内容的SHA1摘要来执行脚本。该命令的用法和EVAL一样，只不过是将脚本内容替换成脚本内容的SHA1摘要
				1.	Redis在执行EVAL命令时会计算脚本的SHA1摘要并记录在脚本缓存中
				2.	执行EVALSHA命令时Redis会根据提供的摘要从脚本缓存中查找对应的脚本内容，如果找到了就执行脚本，否则返回“NOSCRIPT No matching script,Please use EVAL”
			通过以下案例来演示EVALSHA命令的效果
				script load "return redis.call('get','hello')"          将脚本加入缓存并生成sha1命令
				evalsha "a5a402e90df3eaeca2ff03d56d99982e05cf6574" 0
			我们在调用eval命令之前，先执行evalsha命令，如果提示脚本不存在，则再调用eval命令
		5. lua脚本实战
				实现一个针对某个手机号的访问频次， 以下是lua脚本，保存为phone_limit.lua
					local num=redis.call('incr',KEYS[1])
					if tonumber(num)==1 then
					   redis.call('expire',KEYS[1],ARGV[1])
					   return 1
					elseif tonumber(num)>tonumber(ARGV[2]) then
					   return 0
					else
					   return 1
					end
				通过如下命令调用
					./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
				语法为 
					./redis-cli –eval [lua脚本] [key…]空格,空格[args…]
				Java执行见Redisdemo代码：
					RedisLua.javav
		6. 脚本的原子性
				redis的脚本执行是原子的，即脚本执行期间Redis不会执行其他命令。所有的命令必须等待脚本执行完以后才能执行。为了防止某个脚本执行时间过程导致Redis无法提供服务。Redis提供了lua-time-limit参数限制脚本的最长运行时间。默认是5秒钟。当脚本运行时间超过这个限制后，Redis将开始接受其他命令但不会执行（以确保脚本的原子性），而是返回BUSY的错误
		7. 实践操作
				打开两个客户端窗口
				在第一个窗口中执行lua脚本的死循环
					eval “while true do end” 0
				在第二个窗口中运行
					get hello
				最后第二个窗口的运行结果是Busy, 可以通过script kill命令终止正在执行的脚本。如果当前执行的lua脚本对redis的数据进行了修改，比如（set）操作，那么script kill命令没办法终止脚本的运行，因为要保证lua脚本的原子性。如果执行一部分终止了，就违背了这一个原则
				在这种情况下，只能通过 shutdown nosave命令强行终止

​	





​	



​			








