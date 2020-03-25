# redis笔记

------

# Redis的特点：

- 内存数据库，速度快，也支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
- Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
- Redis支持数据的备份，即master-slave模式的数据备份。
- 支持事务

# Redis的优势

#### 性能极高

Redis能读的速度是110000次/s,写的速度是81000次/s 。

####  数据存储结构丰富

1. 字符类型 Strings
2. 散列类型 Hashes
3. 列表类型 Lists
4. 集合类型 Sets 
5. 有序集合 Ordered Sets

#### 丰富的特性

1. 可以为每个key设置超时时间；
2. 可以通过列表类型来实现分布式队列的操作
3. 支持发布订阅的消息模式
4. ......

#### 原子性

Redis的所有操作都是原子性的，同时Redis还支持对几个操作合并后的原子性执行。（事务）

# Redis与其他key-value存储有什么不同？

- Redis有着更为复杂的数据结构并且提供对他们的原子性操作，这是一个不同于其他数据库的进化路径。Redis的数据类型都是基于基本数据结构的同时对程序员透明，无需进行额外的抽象。
- Redis运行在内存中但是可以持久化到磁盘，所以在对不同数据集进行高速读写时需要权衡内存，因为数据量不能大于硬件内存。在内存数据库方面的另一个优点是，相比在磁盘上相同的复杂的数据结构，在内存中操作起来非常简单，这样Redis可以做很多内部复杂性很强的事情。同时，在磁盘格式方面他们是紧凑的以追加的方式产生的，因为他们并不需要进行随机访问。

# Redis的应用场景

1. 数据缓存（商品数据、新闻、热点数据）
2. 单点登录
3. 秒杀、抢购
4. 网站访问排名
5. 应用的模块开发

# 安装下载及命令简介

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

> 注意**命令不区分大小写**

#### info查看redis应用信息

```
127.0.0.1:6379> info
# Server
redis_version:3.2.8
redis_git_sha1:00000000
redis_git_dirty:0
redis_build_id:a2ec0c4e29fa4075
redis_mode:standalone
os:Linux 3.10.0-229.el7.x86_64 x86_64
arch_bits:64
multiplexing_api:epoll
gcc_version:4.8.5
process_id:67418
run_id:33c174e00a655a0e4c3a2d1307a5cfbcaea5cf7c
tcp_port:6379
uptime_in_seconds:48080
uptime_in_days:0
hz:10
lru_clock:7989860
executable:/root/redis/bin/./redis-server
config_file:/root/redis/redis.conf

# Clients
connected_clients:1
client_longest_output_list:0
client_biggest_input_buf:0
blocked_clients:0

# Memory
used_memory:1871704
used_memory_human:1.78M
used_memory_rss:9990144
used_memory_rss_human:9.53M
used_memory_peak:1871704
used_memory_peak_human:1.78M
total_system_memory:1027260416
total_system_memory_human:979.67M
used_memory_lua:45056
used_memory_lua_human:44.00K
maxmemory:0
maxmemory_human:0B
maxmemory_policy:noeviction
mem_fragmentation_ratio:5.34
mem_allocator:jemalloc-4.0.3

# Persistence
loading:0
rdb_changes_since_last_save:0
rdb_bgsave_in_progress:0
rdb_last_save_time:1585043747
rdb_last_bgsave_status:ok
rdb_last_bgsave_time_sec:0
rdb_current_bgsave_time_sec:-1
aof_enabled:0
aof_rewrite_in_progress:0
aof_rewrite_scheduled:0
aof_last_rewrite_time_sec:-1
aof_current_rewrite_time_sec:-1
aof_last_bgrewrite_status:ok
aof_last_write_status:ok

# Stats
total_connections_received:9
total_commands_processed:27
instantaneous_ops_per_sec:0
total_net_input_bytes:1362
total_net_output_bytes:18170226
instantaneous_input_kbps:0.01
instantaneous_output_kbps:3653.62
rejected_connections:0
sync_full:1
sync_partial_ok:0
sync_partial_err:0
expired_keys:2
evicted_keys:0
keyspace_hits:1
keyspace_misses:0
pubsub_channels:0
pubsub_patterns:0
latest_fork_usec:3933
migrate_cached_sockets:0

# Replication
role:master
connected_slaves:0
master_repl_offset:6105
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:2
repl_backlog_histlen:6104

# CPU
used_cpu_sys:47.03
used_cpu_user:3.14
used_cpu_sys_children:0.03
used_cpu_user_children:0.00

# Cluster
cluster_enabled:0

# Keyspace
db0:keys=9,expires=0,avg_ttl=0
db7:keys=3,expires=0,avg_ttl=0

```

# 多数据库支持

**默认支持16个数据库**，具体看配置文件说明：

```
# Set the number of databases. The default database is DB 0, you can select
# a different one on a per-connection basis using SELECT <dbid> where
# dbid is a number between 0 and 'databases'-1
```

redis分数据库，可以理解为一个命名空间

跟关系型数据库不一样的点：

1.	redis不支持自定义数据库名词
2.	每个数据库不能单独设置授权
3.	每个数据库之间并不是完全隔离的。 可以通过flushall命令清空redis实例面的所有数据库中的数据

通过**select dbid 去选择不同的数据库命名空间** 。 默认情况下：dbid的取值范围默认是0 -15

```
[root@app2 bin]# ./redis-cli 
127.0.0.1:6379> select 7
OK
127.0.0.1:6379[7]> select 10
OK
127.0.0.1:6379[10]> select 16
(error) ERR invalid DB index
127.0.0.1:6379[10]>
```

# 使用

#### 简单使用入门

##### 符合匹配规则的键名列表

```
# keys pattern  [? | * | []], 注意后面表达式越不清晰越消耗性能 
127.0.0.1:6379> set test1 foo1
OK
127.0.0.1:6379> set test2 foo2
OK
127.0.0.1:6379> set test3 foo3
OK
127.0.0.1:6379> keys foo*
(empty list or set)
127.0.0.1:6379> keys test*
1) "test2"
2) "test1"
3) "test3"
```

##### 判断一个键是否存在

```
# exists key
127.0.0.1:6379> exists aa bb cc
(integer) 0
127.0.0.1:6379> exists aa bb test1
(integer) 1
127.0.0.1:6379> exists test1 test2
(integer) 2
```

##### 获得这个key的数据结构类型

```
# type key
127.0.0.1:6379> type test1
string
127.0.0.1:6379> type aa
none
```

#### 数据结构的使用

##### 字符类型

- 一个字符类型的key默认存储的最大容量是512M

- 赋值和取值

```
set key value
get key
```

- 递增数字	

```
# incr key #递增为原子操作
127.0.0.1:6379> set age 10
OK
127.0.0.1:6379> incr age
(integer) 11
127.0.0.1:6379> incr age
(integer) 12
127.0.0.1:6379> incr age
(integer) 13
```

错误的操作，程序中递增，线程不安全：

```
int value= get key;
value =value +1;
set key value;
```

- 应用：
  短信重发机制：sms:limit:mobile expire
  对某电话号码进行次数限制，并设置过期时间，防止过期时间内重发次数超过该数量

- key的设计		

命名不建议太长，格式可以为：对象类型:对象id:对象属性:对象子属性

建议对key进行分类，同步在自己公司的wiki中统一管理

- 其他操作

```
incryby key increment  			递增指定的整数,如：incryby age 6
decr key   						原子递减
append key value   				向指定的key追加字符串
strlen  key  					获得key对应的value的长度
mget key [key1 ...]  			同时获得多个key的value，减少网络的传输
mset key value [key value ...] 	同时设置多个key，value
setnx							「SET if Not eXists」的缩写，也就是只有不存在的时候才设置，可以利用它来实现锁的效果
```

##### 列表类型

- 可以存储一个有序的字符串列表，双向链表

```
# LPUSH/RPUSH： 从左边或者右边push数据：LPUSH/RPUSH key value value ...
# LPOP/RPOP : 从左边或者右边取数据

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
```

- 应用场景    

可以用来做分布式消息队列，一边存放，一边获取

- 其他操作		

```
llen key  				获得列表的长度
lrange key start stop   索引可以是负数,-1表示最右边的第一个元素，lrange key 0 -1 # 展示列表所有元素
lrem key count value	删除列表中count个值为value的元素
lset key index value	设置列表中index位置的值为value
```

##### 散列类型

- 结构 hash key value
  比较适合存储对象：
  key    -->	person
  	value   -->   age  18
  						sex   男
  						name li
  						..

- 添加与获取

```
# hset key field value
# hget key filed

127.0.0.1:6379> hset person age 18
(integer) 1
127.0.0.1:6379> hset person sex man
(integer) 1
127.0.0.1:6379> hset person name li
(integer) 1
127.0.0.1:6379> hget person name
"li"
```

- 其他操作

```
hmset key filed value [filed value …]  一次性设置多个属性值
hmget key field field …  	一次性获得多个值
hgetall key  				获得hash的所有信息，包括key和value
hexists key field 			判断字段是否存在。 存在返回1. 不存在返回0
hincryby
hsetnx
hdel key field [field …] 删除一个或者多个字段
```

##### 集合类型

- set 跟list 不一样的点。 集合类型不能存在重复的数据。而且是无序的

- 添加与删除

```
sadd key member [member ...] 	增加数据； 如果value已经存在，则会忽略存在的值，并且返回成功加入的元素的数量
srem key member  				删除元素
smembers key 					获得所有数据
```

- 其他操作

```
sdiff key key ...  	对多个集合执行差集运算，差集来自于第一个key
sunion key key ...	对多个集合执行并集操作, 同时存在在两个集合里的所有值，不存在的集合视为空集
```

##### 有序集合

```
zadd key score member
zrange key start stop [withscores] 去获得元素。 withscores是可以获得元素的分数
				如果两个元素的score是相同的话，那么根据(0<9<A<Z<a<z) 方式从小到大
```

#### Redis的事务处理

```
MULTI 	开启事务
EXEC 	执行事务
DISCARD	取消当前事务
```

注意，运行时出错的事务不回滚：

```
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
```

#### 过期时间

```
expire key seconds 设置过期时间
ttl key 			获得key的过期时间，随时间变化改变，当key不存在时，返回-2。当key存在但没有设置剩余生存时间时，返回-1
```

#### 发布订阅（使用较少）

```
#publish channel message
#subscribe channel [ ... ]

127.0.0.1:6379> PUBLISH redisChat "Redis is a great caching technique"
(integer) 1
127.0.0.1:6379> PUBLISH redisChat "2222222222222222"
(integer) 2

127.0.0.1:6379>  SUBSCRIBE redisChat
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "redisChat"
3) (integer) 1
1) "message"
2) "redisChat"
3) "Redis is a great caching technique"
1) "message"
2) "redisChat"
3) "2222222222222222"
```

#### 分布式锁

##### 锁是用来解决什么问题的

1.	一个进程中的多个线程，多个线程并发访问同一个资源的时候，如何解决线程安全问题。
2.	一个分布式架构系统中的两个模块同时去访问一个文件对文件进行读写操作
3.	多个应用对同一条数据做修改的时候，如何保证数据的安全性

在进程中，我们可以用到synchronized、lock之类的同步操作去解决，但是对于分布式架构下多进程的情况下，如何做到跨进程的锁。就需要借助一些第三方手段来完成

##### 数据库实现

```
通过唯一约束创建表：
lock(
	id  int(11)
	methodName  varchar(100), --锁定方法名称
	memo varchar(1000), 			--描述
	modifyTime timestamp,		--时间戳
	unique key mn (method)  --唯一约束
)
```

获取锁的伪代码：

```
try{
	exec  insert into lock(methodName,memo) values(‘method’,’desc’);    method
				return true;
}Catch(DuplicateException e){
	return false;
}
# 注意上面是非重入锁

释放锁
	delete from lock where methodName=’’;
```

存在的需要思考的问题

1. 锁没有失效时间，一旦解锁操作失败，就会导致锁记录一直在数据库中，其他线程无法再获得到锁
2. 锁是非阻塞的，数据的insert操作，一旦插入失败就会直接报错。没有获得锁的线程并不会进入排队队列，要想再次获得锁就要再次触发获得锁操作
3. 锁是非重入的，同一个线程在没有释放锁之前无法再次获得该锁

##### zookeeper实现

利用zookeeper的唯一节点特性或者有序临时节点特性获得最小节点作为锁，zookeeper 的实现相对简单，通过curator客户端，已经对锁的操作进行了封装。

实现优势：

1. 可靠性高、实现简单
2. zookeeper因为临时节点的特性，如果因为其他客户端因为异常和zookeeper连接中断了，那么节点会被删除，意味着锁会被自动释放
3. zookeeper本身提供了一套很好的集群方案，比较稳定
4. 释放锁操作，会有watch通知机制，也就是服务器端会主动发送消息给客户端这个锁已经被释放了

##### 基于缓存的分布式锁实现

redis中有一个setNx命令，这个命令只有在key不存在的情况下为key设置值。所以可以利用这个特性来实现分布式锁的操作

具体实现代码：

1. 引入依赖

```
<dependency>
	<groupId>redis.clients</groupId>
	<artifactId>jedis</artifactId>
	<version>2.9.0</version>
</dependency>
```

2. Redis获取连接代码

```
package com.eussi;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wangxueming
 * @create 2019-07-01 23:32
 * @description
 */
public class RedisManager {
    private static JedisPool jedisPool;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.198.201", 6379);
    }

    public static Jedis getJedis() throws Exception {
        if(jedisPool!=null) {
            return jedisPool.getResource();
        }
        throw new Exception("JedisPool is not init");
    }
}
```

分布式锁的具体实现

```
package com.eussi;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * @author wangxueming
 * @create 2019-07-01 23:37
 * @description
 */
public class RedisLock {

    public String getLock(String key, int timeout) {
        try {
            Jedis jedis = RedisManager.getJedis();
            String value = UUID.randomUUID().toString();//UUID
            long end = System.currentTimeMillis() + timeout*1000;
            while (System.currentTimeMillis() < end) {//在一段时间内尝试获取锁
                if (jedis.setnx(key, value) == 1) {//设置成功返回1
                    jedis.expire(key, timeout);
                    //锁设置成功, redis操作成功
                    return value;
                }
                if (jedis.exists(key) && jedis.ttl(key) == -1) {//edis.setnx(key, value) == 1执行完毕宕机，重新检测过期时间,并设置
                    jedis.expire(key, timeout);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean releaseLock(String key, String value) {
        try {
            Jedis jedis = RedisManager.getJedis();
            while(true) {
                jedis.watch(key);//一般是和事务一起使用，当对某个key进行watch后如果其他的客户端对这个key进行了更改，那么本次事务会被取消，事务的exec会返回null。jedis.watch(key)都会返回OK
                if(value.equals(jedis.get(key))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(key);
                    List<Object> list = transaction.exec();
                    if(list==null) {
                        continue;
                    }
                    return true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        RedisLock redisLock = new RedisLock();
        String lockId = redisLock.getLock("lock:test2", 5);
        if(null!=lockId) {
            System.out.println("获得锁成功：" + lockId);
        } else {
            System.out.println("获得锁失败！");
        }

//        redisLock.releaseLock("lock:test2", lockId);//注释此句代码则获取失败,注释后调整下面锁的获取时间，大于超时时间也可以


        lockId = redisLock.getLock("lock:test2", 5);
        if(null!=lockId) {
            System.out.println("获得锁成功：" + lockId);
        } else {
            System.out.println("获得锁失败！");
        }
    }
}
```

# Redis多路复用机制

linux的内核会把所有外部设备都看作一个文件来操作，对一个文件的读写操作会调用内核提供的系统命令，返回一个 file descriptor（文件描述符）。对于一个socket的读写也会有响应的描述符，称为socketfd(socket 描述符)。而IO多路复用是指内核一旦发现进程指定的一个或者多个文件描述符IO条件准备好以后就通知该进程

IO多路复用又称为事件驱动，操作系统提供了一个功能，当某个socket可读或者可写的时候，它会给一个通知。当配合非阻塞socket使用时，只有当系统通知我哪个描述符可读了，我才去执行read操作，可以保证每次read都能读到有效数据。操作系统的功能通过select/pool/epoll/kqueue之类的系统调用函数来使用，这些函数可以同时监视多个描述符的读写就绪情况，这样多个描述符的I/O操作都能在一个线程内并发交替完成，这就叫I/O多路复用，这里的复用指的是同一个线程。

多路复用的优势在于用户可以在一个线程内同时处理多个socket的 io请求。达到同一个线程同时处理多个IO请求的目的。而在同步阻塞模型中，必须通过多线程的方式才能达到目的

# Redis中使用lua脚本

#### lua脚本

Lua是一个高效的轻量级脚本语言，用标准C语言编写并以源代码形式开放， 其设计目的是为了嵌入应用程序中，从而为应用程序提供灵活的扩展和定制功能

#### 使用脚本的好处

1. 减少网络开销，在Lua脚本中可以把多个命令放在同一个脚本中运行
2. 原子操作，redis会将整个脚本作为一个整体执行，中间不会被其他命令插入。换句话说，编写脚本的过程中无需担心会出现竞态条件
3. 复用性，客户端发送的脚本会永远存储在redis中，这意味着其他客户端可以复用这一脚本来完成同样的逻辑

#### Lua在linux中的安装

下载版本如：lua-5.3.0.tar.gz

```
$ tar -zxvf lua-5.3.0.tar.gz
$ cd lua-5.3.0
$ make linux
	报错如下
	gcc -std=gnu99 -O2 -Wall -Wextra -DLUA_COMPAT_5_2 -DLUA_USE_LINUX    -c -o lua.o 		lua.c
	lua.c:80:31: fatal error: readline/readline.h: No such file or directory
 	#include <readline/readline.h>
 	执行：
	$ yum -y install readline-devel ncurses-devel
$ make install
```

#### lua语法简介

变量

全局变量、局部变量

a=1

local b=2 #

```
-- test.lua 文件脚本
a = 5               -- 全局变量
local b = 5         -- 局部变量

function joke()
    c = 5           -- 全局变量
    local d = 6     -- 局部变量
end

joke()
print(c,d)          --> 5 nil

do 
    local a = 6     -- 局部变量
    b = 6           -- 对局部变量重新赋值
    print(a,b);     --> 6 6
end

print(a,b)      --> 5 6
```

运算

\+ - * / %

逻辑表达式

a==b 比较相等

~= 不等于

逻辑运算符

and/or/not

a..b连接

控制语句

```
if expression then

...

elseif expression then

...

else

...

end
```

```
while expression do
...
end
```

```
for i=1,100 do
	print(i)
end

local xx={"a", "b", "c"}
for i,v in iparis(xx) do
	print V
end
```

注释

--[[ ]]

函数

```
[scope] function(params [...])
[return xx]
end
```

#### Redis与Lua

在Lua脚本中调用Redis命令，可以使用redis.call函数调用。比如我们调用string类型的命令

```
redis.call(‘set’,’hello’,’world’)
```

**redis.call函数的返回值就是redis命令的执行结果**。前面我们介绍过redis的5种类型的数据返回的值的类型也都不一样。redis.call函数会将这5种类型的返回值转化对应的Lua的数据类型

##### 从Lua脚本中获得返回值

在很多情况下我们都需要脚本可以有返回值，在脚本中可以使用return语句将值返回给redis客户端，通过return语句来执行，如果没有执行return，默认返回为nil。

##### 如何在redis中执行lua脚本

Redis提供了EVAL命令可以使开发者像调用其他Redis内置命令一样调用脚本。

```
[EVAL] [脚本内容] [key参数的数量] [key …] [arg …]
```

可以通过key和arg这两个参数向脚本中传递数据，他们的值可以在脚本中分别使用**KEYS**和**ARGV** 这两个类型的全局变量访问。比如我们通过脚本实现一个set命令，通过在redis客户端中调用，那么执行的语句是：

lua脚本的内容为： 

```
return redis.call(‘set’,KEYS[1],ARGV[1])     //KEYS和ARGV必须大写
```

执行：

```
eval "return redis.call('set',KEYS[1],ARGV[1])" 1 hello world
```

EVAL命令是根据 key参数的数量，也就是上面例子中的1来将后面所有参数分别**存入脚本中KEYS和ARGV两个表类型的全局变量**。当脚本不需要任何参数时也不能省略这个参数。如果没有参数则为0

```
eval "return redis.call('get', 'hello')" 0
```

执行如下：

```
127.0.0.1:6379> eval "return redis.call('set',KEYS[1],ARGV[1])" 1 hello world
OK
127.0.0.1:6379> get hello
"world"
127.0.0.1:6379> eval "return redis.call('get', 'hello')" 0
"world"
```

##### EVALSHA命令

考虑到我们通过eval执行lua脚本，脚本比较长的情况下，每次调用脚本都需要把整个脚本传给redis，比较占用带宽。为了解决这个问题，**redis提供了EVALSHA命令允许开发者通过脚本内容的SHA1摘要来执行脚本**。该命令的用法和EVAL一样，只不过是将脚本内容替换成脚本内容的SHA1摘要

1. Redis在执行EVAL命令时会计算脚本的SHA1摘要并记录在脚本缓存中
2.  执行EVALSHA命令时Redis会根据提供的摘要从脚本缓存中查找对应的脚本内容，如果找到了就执行脚本，否则返回“NOSCRIPT No matching script,Please use EVAL”

通过以下案例来演示EVALSHA命令的效果：

```
script load "return redis.call('get','hello')"     将脚本加入缓存并生成sha1命令

evalsha "a5a402e90df3eaeca2ff03d56d99982e05cf6574" 0
```

我们在调用eval命令之前，先执行evalsha命令，如果提示脚本不存在，则再调用eval命令

```
127.0.0.1:6379> script load "return redis.call('get','hello')"
"fc1c184a3f0f566037349a820acef20fc7ece599"
127.0.0.1:6379> evalsha "a5a402e90df3eaeca2ff03d56d99982e05cf6574" 0
(error) NOSCRIPT No matching script. Please use EVAL.
127.0.0.1:6379> evalsha "fc1c184a3f0f566037349a820acef20fc7ece599" 0
"world"
```

##### lua脚本实战

实现一个针对某个手机号的访问频次， 以下是lua脚本，保存为phone_limit.lua

```
local num=redis.call('incr',KEYS[1])
if tonumber(num)==1 then
   redis.call('expire',KEYS[1],ARGV[1])
   return 1
elseif tonumber(num)>tonumber(ARGV[2]) then
   return 0
else
   return 1
end
```

通过如下命令调用

```
./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
```

语法为` ./redis-cli –eval [lua脚本] [key…]空格,空格[args…]`

在10s内连续执行如下：

```
[root@app1 bin]# ./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
(integer) 1
[root@app1 bin]# ./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
(integer) 1
[root@app1 bin]# ./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
(integer) 1
[root@app1 bin]# ./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
(integer) 0
[root@app1 bin]# ./redis-cli --eval phone_limit.lua rate.limiting:13700000000 , 10 3
(integer) 0
```

##### 脚本的原子性

redis的脚本执行是原子的，即脚本执行期间Redis不会执行其他命令。所有的命令必须等待脚本执行完以后才能执行。为了防止某个脚本执行时间过程导致Redis无法提供服务。Redis提供了lua-time-limit参数限制脚本的最长运行时间。默认是5秒钟。

当脚本运行时间超过这个限制后，Redis将开始接受其他命令但不会执行（以确保脚本的原子性），而是返回BUSY的错误

实践操作如下

打开两个客户端窗口，在第一个窗口中执行lua脚本的死循环

```
eval "while true do end" 0
```

在第二个窗口中运行：

```
127.0.0.1:6379> get hello 
(error) BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.
(3.63s)
127.0.0.1:6379> get hello
(error) BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.
127.0.0.1:6379> get hello
(error) BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.
127.0.0.1:6379> get hello
```

第一个get hello很快执行的话，需要等一会，也就是lua-time-limit的时间，后续的就很快就会返回BUSY。最后第二个窗口的运行结果是Busy, 可以通过script kill命令终止正在执行的脚本。如果当前执行的lua脚本对redis的数据进行了修改，比如（set）操作，那么script kill命令没办法终止脚本的运行，因为要保证lua脚本的原子性。如果执行一部分终止了，就违背了这一个原则。

在这种情况下，只能通过 **shutdown nosave**命令强行终止

##### 代码实现

```
package com.eussi;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxueming
 * @create 2019-07-04 1:32
 * @description
 */
public class RedisLua {
    public static void main(String[] args) throws Exception {
        //执行Lua脚本
        evalLua();
        //通过摘要执行Lua脚本缓存
        evalshaLua();

        //根据传入参数，可能会失败
        evalshaLua();
        evalshaLua();
    }

    private static void evalLua() throws Exception {
        Jedis jedis = RedisManager.getJedis();

        String lua = "local num=redis.call('incr',KEYS[1])\n" +
                "if tonumber(num)==1 then\n" +
                "   redis.call('expire',KEYS[1],ARGV[1])\n" +
                "   return 1\n" +
                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
                "   return 0\n" +
                "else\n" +
                "   return 1\n" +
                "end";

        List<String> keys = new ArrayList<String>();
        keys.add("phone:limit");
        List<String> argss = new ArrayList<String>();
        argss.add("10");
        argss.add("2");
        Object obj = jedis.eval(lua, keys, argss);
        System.out.println(obj);
    }

    private static void evalshaLua() throws Exception {
        Jedis jedis = RedisManager.getJedis();

        String lua = "local num=redis.call('incr',KEYS[1])\n" +
                "if tonumber(num)==1 then\n" +
                "   redis.call('expire',KEYS[1],ARGV[1])\n" +
                "   return 1\n" +
                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
                "   return 0\n" +
                "else\n" +
                "   return 1\n" +
                "end";

//        String sha = jedis.scriptLoad(lua);//仅需在第一次执行时进行缓存或者摘要即可
//        System.out.println("脚本sha:" + sha);

        String sha = "8a8ee74e246c39d3ac49ddfc938fa2942c56e087";

        List<String> keys = new ArrayList<String>();
        keys.add("phone:limit");
        List<String> argss = new ArrayList<String>();
        argss.add("10");
        argss.add("2");
        Object obj = jedis.evalsha(sha, keys, argss);
        System.out.println(obj);
    }
}
```

# redis持久化机制

redis提供了两种持久化策略

#### RDB(Redis DataBase)

##### RDB的持久化策略

按照规则定时将内存的数据同步到磁盘，通过snapshot方式实现的，生成的快照文件存储在**bin/dump.rdb**中

redis在指定的情况下会触发快照

1. 自己配置的快照规则

   ```
   # 默认三个配置，可以在redis.conf中查看，上面有说明
   save 900 1 		#当在900秒内被更改的key的数量大于1的时候，就执行快照
   save 300 10
   save 60 10000
   #以上规则是or的关系
   ```

2. save或者bgsave

   save: 执行内存的数据同步到磁盘的操作，这个操作会阻塞客户端的请求(当数据较多时，会有一定的影响)

   bgsave: 在后台异步执行快照操作，这个操作不会阻塞客户端的请求

3. 执行flushall的时候

   清除内存的所有数据，只要快照的规则不为空，也就是第一个规则存在。那么redis会执行快照

4. 执行复制的时候

##### 快照的实现原理

1. redis使用fork函数复制一份当前进程的副本(子进程)

2. 父进程继续接收并处理客户端发来的命令，而子进程开始将内存中的数据写入硬盘中的临时文件

3. 当子进程写入完所有数据后会用该临时文件替换旧的RDB文件，至此，一次快照操作完成。 

注意：redis在进行快照的过程中不会修改RDB文件，只有快照结束后才会将旧的文件替换成新的，也就是说任何时候RDB文件都是完整的。 这就**使得我们可以通过定时备份RDB文件来实现redis数据库的备份**， RDB文件是经过压缩的二进制文件，占用的空间会小于内存中的数据，更加利于传输。

##### RDB的优缺点

1. 使用RDB方式实现持久化，一旦Redis异常退出，就会丢失最后一次快照以后更改的所有数据。这个时候我们就需要根据具体的应用场景，通过组合设置自动快照条件的方式来将可能发生的数据损失控制在能够接受范围。如果数据相对来说比较重要，希望将损失降到最小，则可以使用AOF方式进行持久化
2. RDB可以最大化Redis的性能：父进程在保存RDB文件时唯一要做的就是fork出一个子进程，然后这个子进程就会处理接下来的所有保存工作，父进程无需执行任何磁盘I/O操作。同时这个也是一个缺点，如果**数据集比较大的时候，fork可以能比较耗时**，造成服务器在一段时间内停止处理客户端的请求；

##### 实践

修改redis.conf中的appendonly yes ; 重启后执行对数据的变更命令， 会在bin目录下生成对应的.aof文件， aof文件中会记录所有的操作命令

如下两个参数可以去对aof文件做优化

auto-aof-rewrite-percentage 100 表示当前aof文件大小超过上一次aof文件大小的百分之多少的时候会进行重写。如果之前没有重写过，以启动时aof文件大小为准

auto-aof-rewrite-min-size 64mb  限制允许重写最小aof文件大小，也就是文件大小小于64mb的时候，不需要进行优化

#### AOF(Append Of File)

##### AOF的持久化策略

AOF可以将Redis执行的每一条写命令追加到硬盘文件中，这一过程显然会降低Redis的性能，但大部分情况下这个影响是能够接受的，另外**使用较快的硬盘可以提高AOF**的性能

##### aof重写的原理

Redis 可以在 AOF 文件体积变得过大时，自动地在后台对 AOF 进行重写： 重写后的新 AOF 文件包含了恢复当前数据集所需的最小命令集合。 整个重写操作是绝对安全的，因为 Redis 在创建新 AOF 文件的过程中，会继续将命令追加到现有的 AOF 文件里面，即使重写过程中发生停机，现有的 AOF 文件也不会丢失。 而一旦新 AOF 文件创建完毕，Redis 就会从旧 AOF 文件切换到新 AOF 文件，并开始对新 AOF 文件进行追加操作。AOF 文件有序地保存了对数据库执行的所有写入操作， 这些写入操作以 Redis 协议的格式保存， 因此 AOF 文件的内容非常容易被人读懂， 对文件进行分析（parse）也很轻松。

Redis不希望AOF重写会造成服务器无法处理请求，所以Redis决定将AOF重写程序放到（后台）子进程里执行。这样处理的最大好处是： 

1. 子进程进行AOF重写期间，主进程可以继续处理命令请求； 
2. 子进程带有主进程的数据副本，使用子进程而不是线程，可以避免在锁的情况下，保证数据的安全性。  子进程在进行AOF重写期间，主进程还要继续处理命令请求，而新的命令可能对现有的数据进行修改，这会让当前数据库的数据和重写后的AOF文件中的数据不一致。  为了解决这个问题，Redis增加了一个**AOF重写缓存**，这个缓存在fork出子进程之后开始启用，Redis主进程在接到新的写命令之后，除了会将这个写命令的内容追加到现有的AOF文件之外，还会追加到这个缓存中： Redis之AOF重写及其实现原理也就是说，子进程在执行AOF重写时，主进程需要执行以下三个工作：
   1. 处理命令请求；
   2. 将写命令追加到现有的AOF文件中； 
   3. 将写命令追加到AOF重写缓存中。 如此可以保证： 现有的AOF功能继续执行，即使AOF重写期间发生停机，也不会有任何数据丢失；所有对数据库进行修改的命令都会被记录到AOF重写缓存中。  当子进程完成对AOF文件重写之后，它会向父进程发送一个完成信号，父进程接到该完成信号之后，会调用一个信号处理函数，该函数完成以下工作： 将AOF重写缓存中的内容全部写入到新的AOF文件中； 对新的AOF文件进行改名，覆盖原有的AOF文件。  当“将AOF重写缓存中的内容全部写入到新的AOF文件中；”执行完毕后，现有AOF文件、新的AOF文件和数据库三者的状态就完全一致了。 当“对新的AOF文件进行改名，覆盖原有的AOF文件。”执行完毕后，程序就完成了新旧两个AOF文件的替换。 当这个信号处理函数执行完毕之后，主进程就可以继续像往常一样接收命令请求了。在整个AOF后台重写过程中，只有最后的“主进程写入命令到AOF缓存”和“对新的AOF文件进行改名，覆盖原有的AOF文件。”这两个步骤会造成主进程阻塞，在其他时候，AOF后台重写都不会对主进程造成阻塞，这将AOF重写对性能造成的影响降到最低。

##### 同步磁盘数据

redis每次更改数据的时候， aof机制都会讲命令记录到aof文件，但是实际上由于操作系统的缓存机制，数据并没有实时写入到硬盘，而是进入硬盘缓存。再通过硬盘缓存机制去刷新到保存到文件，查看redis.conf文件内容如下:

```
\# appendfsync always 每次执行写入都会进行同步  ， 这个是最安全但是是效率比较低的方式
appendfsync everysec  每一秒执行
\# appendfsync no 不主动进行同步操作，由操作系统去执行，这个是最快但是最不安全的方式
```

##### aof文件损坏以后如何修复  

服务器可能在程序正在对 AOF 文件进行写入时停机， 如果停机造成了 AOF 文件出错（corrupt）， 那么 Redis 在重启时会拒绝载入这个 AOF 文件， 从而确保数据的一致性不会被破坏。

当发生这种情况时， 可以用以下方法来修复出错的 AOF 文件：

1. 为现有的 AOF 文件创建一个备份。

2. 使用 Redis 附带的 redis-check-aof 程序，对原来的 AOF 文件进行修复。

   ```
   redis-check-aof --fix
   ```

重启 Redis 服务器，等待服务器载入修复后的 AOF 文件，并进行数据恢复。

##### 实践

默认情况下Redis没有开启AOF（append only file）方式的持久化，可以通过appendonly参数启用，在redis.conf中找到 appendonly yes

开启AOF持久化后每执行一条会更改Redis中的数据的命令后，Redis就会将该命令写入硬盘中的AOF文件。AOF文件的保存位置和RDB文件的位置相同，都是通过dir参数设置的，默认的文件名是apendonly.aof。可以在redis.conf中的属性 appendfilename appendonlyh.aof修改

其余相关参数，见redis.conf描述

```
no-appendfsync-on-rewrite no  #rewrite时，主线程不同步，IO刷盘交给操作系统控制，可能丢数据

auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
```

#### RDB 和 AOF, 如何选择

一般来说,如果对数据的安全性要求非常高的话，应该同时使用两种持久化功能。如果可以承受数分钟以内的数据丢失，那么可以只使用 RDB 持久化。有很多用户都只使用 AOF 持久化， 但并不推荐这种方式： 因为定时生成 RDB 快照（snapshot）非常便于进行数据库备份， 并且 RDB 恢复数据集的速度也要比 AOF 恢复的速度要快 。

**两种持久化策略可以同时使用，也可以使用其中一种。如果同时使用的话， 那么Redis重启时，会优先使用AOF文件来还原数据**

# 集群

保证高可用，避免单点故障

#### 复制（master、slave）

和MySQL主从复制的原因一样，Redis虽然读取写入的速度都特别快，但是也会产生读压力特别大的情况。为了分担读压力，Redis支持主从复制，Redis的主从结构可以采用一主多从或者级联结构，下级联结构即从可以继续有从。

##### 配置过程

修改作为从服务器的redis.conf文件，增加slaveof masterip masterport，如

```
slaveof 192.168.11.138 6379
```

通过`info replication`查看集群信息

##### 实现原理

Redis主从复制可以根据是否是全量分为全量同步和增量同步。

Redis全量复制一般发生在Slave初始化阶段，这时Slave需要将Master上的所有数据都复制一份。具体步骤如下： 

1. 从服务器连接主服务器，发送SYNC命令； 

2. 主服务器接收到SYNC命名后，开始执行BGSAVE命令生成RDB文件并使用缓冲区记录此后执行的所有写命令； 

3. 主服务器BGSAVE执行完后，向所有从服务器发送快照文件，并在发送期间继续记录被执行的写命令； 

4. 从服务器收到快照文件后丢弃所有旧数据，载入收到的快照； 

5. 主服务器快照发送完毕后开始向从服务器发送缓冲区中的写命令； 

6. 从服务器完成对快照的载入，开始接收命令请求，并执行来自主服务器缓冲区的写命令； 


无硬盘复制，见参数 
```
repl-diskless-sync no
```

增量同步

Redis增量复制是指Slave初始化后开始正常工作时主服务器发生的写操作同步到从服务器的过程。 

```
# 传输如下内容到master
PSYNC master run id. offset
```

增量复制的过程主要是主服务器每执行一个写命令就会向从服务器发送相同的写命令，从服务器接收并执行收到的写命令。

```
#可以在从服务器上执行：
#replconf listening-port 6379
#sync
#查看主服务器同步过来的内容

127.0.0.1:6379> replconf listening-port 6379
OK
127.0.0.1:6379> sync
Entering slave output mode...  (press Ctrl-C to quit)
SYNC with master, discarding 267 bytes of bulk transfer...
SYNC done. Logging commands from master.
"PING"

```

1. ##### 复制的方式

   1. 基于rdb文件的复制（第一次连接或者重连的时候）
   2. 无硬盘复制（禁用rdb时）
   3. 增量复制

##### 缺点

没有办法对master进行动态选举，master挂掉就没有master了

#### 哨兵机制 sentinel

##### 概述

Redis Sentinel是一个分布式系统，为Redis提供高可用性解决方案。可以在一个架构中运行多个 Sentinel 进程(progress)， 这些进程使用流言协议(gossip protocols)来接收关于主服务器是否下线的信息， 并使用投票协议(agreement protocols)来决定是否执行自动故障迁移， 以及选择哪个从服务器作为新的主服务器。

Redis 的 Sentinel 系统用于管理多个 Redis 服务器(instance)， 该系统执行以下三个任务:

- **监控(Monitoring)**: Sentinel 会不断地定期检查你的主服务器和从服务器是否运作正常。
- **提醒(Notification)**: 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。
- **自动故障迁移(Automaticfailover)**: 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中 一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器; 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。

##### 哨兵文件详解

 配置一：`sentinel monitor <master-name> <ip> <redis-port> <quorum>`

这个配置表达的是哨兵节点定期监控名字叫做 \<master-name>并且 IP 为\<ip> 端口号为\<redis-port> 的主节点。\<quorum> 表示的是哨兵判断主节点是否发生故障的票数。也就是说如果我们将\<quorum>设置为2就代表至少要有两个哨兵认为主节点故障了，才算这个主节点是客观下线的了，一般是设置为sentinel节点数的一半加一。

配置二：`sentinel down-after-milliseconds <master-name> <milliseconds>`

 每个哨兵节点会定期发送ping命令来判断Redis节点和其余的哨兵节点是否是可达的，如果超过了配置的\<times>时间没有收到ping回复，就主观判断节点是不可达的,<times>的单位为毫秒。

配置三：`sentinel parallel-syncs <master-name> <numslaves>`

 当哨兵节点都认为主节点故障时，哨兵投票选出的leader会进行故障转移，选出新的主节点，原来的从节点们会向新的主节点发起复制，这个配置就是控制在故障转移之后，每次可以向新的主节点发起复制的节点的个数，最多为\<nums>个，因为如果不加控制会对主节点的网络和磁盘IO资源很大的开销。

配置四：`sentinel failover-timeout <master-name> <milliseconds>`

 这个代表哨兵进行故障转移时如果超过了配置的\<times>时间就表示故障转移超时失败。

配置五： `sentinel auth-pass <master-name> <password>`

 如果主节点设置了密码，则需要这个配置，否则哨兵无法对主节点进行监控。

##### 为什么要用到哨兵

1. 从Redis宕机

这个相对而言比较简单,在Redis中从库重新启动后会自动加入到主从架构中,自动完成同步数据。在Redis2.8版本后,主从断线后恢复的情况下实现增量复制。

2. 主Redis宕机

这个相对而言就会复杂一些,需要以下2步才能完成
a. 在从数据库中执行SLAVEOF NO ONE命令,断开主从关系并且提升为主库继续服务
b. 将主库重新启动后,执行SLAVEOF命令,将其设置为其他库的从库,这时数据就能更新回来

由于这个手动完成恢复的过程其实是比较麻烦的并且容易出错,所以Redis提供的哨兵(sentinel)的功能来解决

3. 哨兵机制（sentinel）的高可用

Sentinel（哨兵）是**Redis 的高可用性解决方案**：由一个或多个Sentinel 实例组成的Sentinel 系统可以监视任意多个主服务器，以及这些主服务器属下的所有从服务器，并在被监视的主服务器进入下线状态时，自动将下线主服务器属下的某个从服务器升级为新的主服务器。

##### 主要工作原理

1. 每个Sentinel以每秒钟一次的频率向它所知的Master，Slave以及其他 Sentinel 实例发送一个 PING 命令。

2. 如果一个实例（instance）距离最后一次有效回复 PING 命令的时间超过 down-after-milliseconds 选项所指定的值， 则这个实例会被 Sentinel 标记为主观下线。 

3. 如果一个Master被标记为主观下线，则正在监视这个Master的所有 Sentinel 要以每秒一次的频率确认Master的确进入了主观下线状态。 

4. 当有足够数量的 Sentinel（大于等于配置文件指定的值）在指定的时间范围内确认Master的确进入了主观下线状态， 则Master会被标记为客观下线 。

5. 在一般情况下， 每个 Sentinel 会以每 10 秒一次的频率向它已知的所有Master，Slave发送 INFO 命令 。

6. 当Master被 Sentinel 标记为客观下线时，Sentinel 向下线的 Master 的所有 Slave 发送 INFO 命令的频率会从 10 秒一次改为每秒一次 。

7. 若没有足够数量的 Sentinel 同意 Master 已经下线， Master 的客观下线状态就会被移除。 

    若 Master 重新向 Sentinel 的 PING 命令返回有效回复， Master 的主观下线状态就会被移除。

##### 补充

- 哨兵至少需要3个实例，来保证自己的健壮性。
- 哨兵+redis主从的部署架构，是不会保证数据零丢失的，只能保证redis集群的高可用性
- 对于哨兵+redis主从这种复杂的部署架构，尽量在测试环境和生产环境，都进行充分的测试和演练。

##### redis哨兵主备切换的数据丢失问题

###### 两种丢失情况：

- 异步复制导致的数据丢失
   因为master->slave的复制是异步的，所以可能有部分数据还没复制到slave，master就宕机了，这些数据就丢失了。
- 脑裂导致的数据丢失
  脑裂，也就是说，某个master所在机器突然脱离了正常的网络，跟其他slave机器不能连接，但是实际上master还运行着
  这个时候，集群中就会出现两个master。
  此时虽然某个slave被切换成了master，但是可能client还没来得及切换到新的master，还继续写向旧master数据可能就会丢失。
  因此master在恢复的时候，会被作为一个slave挂到新的master上，自己的数据会被清空，从新的master复制数据

###### 解决异步复制和脑裂导致的数据丢失

min-slaves-to-write 1
min-slaves-max-lag 10
要求至少有1个slave，数据复制和同步的延迟不能超过10秒
如果说一旦所有slave，数据复制和同步的延迟都超过了10秒钟，那么这个时候，master就不会再接收任何请求了。
 （1）减少异步复制的数据丢失
 有了min-slaves-max-lag这个配置，就可以确保说，一旦slave复制数据和ack延时太长，就认为可能master宕机后损失的数据太多了，那么就拒绝写请求，这样可以把master宕机时由于部分数据未同步到slave导致的数据丢失降低的可控范围内
 （2）减少脑裂的数据丢失
 如果一个master出现了脑裂，跟其他slave丢了连接，那么上面两个配置可以确保说，如果不能继续给指定数量的slave发送数据，而且slave超过10秒没有给自己ack消息，那么就直接拒绝客户端的写请求
 这样脑裂后的旧master就不会接受client的新数据，也就避免了数据丢失
上面的配置就确保了，如果跟任何一个slave丢了连接，在10秒后发现没有slave给自己ack，那么就拒绝新的写请求，因此在脑裂场景下，最多就丢失10秒的数据

#### 集群（redis3.0以后的功能）

即使使用哨兵，redis集群的每个数据库依然存有集群中的所有数据，从而导致集群的总数据存储受限于可用内存最小的数据库节点，形成木桶效应。由于redis中的所有数据都基于内存存储，这一问题尤为突出，特别是把redis用作持久化存储服务时。

在旧版redis中通常使用**客户端分片**来解决水平扩容问题，即启动多个redis数据库节点，由客户端决定每个键交由哪个节点存储，下次客户端读取该键时直接到该节点读取。这样可以实现将整个数据分布存储在N个数据库节点中，每个节点只存放总数据量的1/N。但是对于需要扩容的场景来说，在客户端分片后，如果想增加节点，需要对数据进行手工迁移，同时在迁移的过程中为了保证数据一致性，还需要将集群暂时下线，相对比较复杂。

考虑到redis实例非常轻量的特点，可以采用**预分片技术（presharding）**在一定程度上避免此问题。具体来说是在节点部署初期，就提前考虑日后的存储规模，建立足够多的实例（如128个节点），初期时数据很少，所以每个节点存储的数据也非常少。由于节点轻量的特性，数据之外的内存开销并不大，这使得只需要很少的服务器即可运行这些实例。日后存储规模扩大后，所要做的不过是将某些实例迁移到其它服务器上，而不需要对所有数据进行重新分片并进行集群下线的数据迁移。

无论如何，客户端分片终归有很多缺点，如维护成本高，增加、移除节点较繁琐等。**redis 3.0版的一大特性就是支持数据分片的集群功能**。集群的特点在于拥有和单机实例同样的性能，同时在网络分区后能够提供一定的可访问性以及对主库故障恢复的支持。另外集群支持几乎所有的单机实例支持的命令，对于涉及多键的命令（如mget），如果每个键都位于同一节点中，则可以正常支持，否则会提示错误。除此之外集群还有一个限制是只能使用默认的0号数据库，如果执行select切换数据库则会提示错误：

```
(error) ERR SELECT is not allowed in cluster mode
```

哨兵与集群是两个独立的功能，但从特性来看哨兵可以视为集群的子集，当不需要数据分片或者在客户端进行分片的场景下使用哨兵就足够了，但如果需要进行水平扩容，则集群是一个较好的选择。

##### 集群的原理

Redis Cluster中，Sharding采用slot(槽)的概念，一共分成16384个槽，这有点儿类似前面讲的pre sharding思路。对于每个进入Redis的键值对，根据key进行散列，分配到这16384个slot中的某一个中。使用的hash算法也比较简单，就是CRC16后16384取模。Redis集群中的每个node(节点)负责分摊这16384个slot中的一部分，也就是说，每个slot都对应一个node负责处理。当动态添加或减少node节点时，需要将16384个槽做个再分配，槽中的键值也要迁移。当然，这一过程，在目前实现中，还处于半自动状态，需要人工介入。Redis集群，要保证16384个槽对应的node都正常工作，如果某个node发生故障，那它负责的slots也就失效，整个集群将不能工作。为了增加集群的可访问性，官方推荐的方案是将node配置成主从结构，即一个master主节点，挂n个slave从节点。这时，如果主节点失效，Redis Cluster会根据选举算法从slave节点中选择一个上升为主节点，整个集群继续对外提供服务。这非常类似服务器节点通过Sentinel监控架构成主从结构，只是Redis Cluster本身提供了故障转移容错的能力。

假如有3个节点

```
节点		   槽
node1 		0 5460
node2 		5461 10922
node3 		10923 16383
```

节点新增

node4 0-1364,5461-6826,10923-12287

删除节点

先将节点的数据移动到其他节点上，然后才能执行删除

##### 市面上提供了集群方案

1. redis shardding  而且jedis客户端就支持shardding操作  SharddingJedis ； 

   增加和减少节点的问题：pre shardding

   即：3台虚拟机 redis 。但是我部署了9个节点 。每一台部署3个redis增加cpu的利用率，9台虚拟机单独拆分到9台服务器。

2. codis基于redis2.8.13分支开发了一个codis-server

3. twemproxy 也叫 nutcraker， twitter提供的开源解决方案（Redis 和 Memcache 代理服务器）

# redis缓存的更新

先删除缓存，再更新数据库，会存在脏数据的情况，例如，一个更新进程，一个查询进程，查询未命中缓存，去查询数据库，但数据库此时还未更新，然后缓存会存储旧的缓存数据，导致一段时间内，缓存中还是旧的数据。

使用强一致性算法，如2PC/3PC算法，或者 Zookeeper的核心算法——Paxos算法可以完成一致性，但是肯定会很损耗性能。

一般采用尽可能减少损失：

1. 先更新数据库，更新成功后，让缓存失效

2. 更新数据的时候，只更新缓存，不更新数据库，然后同步异步调度去批量更新数据库

# 缓存击穿

#### 缓存穿透

缓存穿透是指查询一个一定不存在的数据，由于缓存是不命中时被动写的，并且出于容错考虑，如果从存储层查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到存储层去查询，失去了缓存的意义。在流量大时，可能DB就挂掉了，要是有人利用不存在的key频繁攻击我们的应用，这就是漏洞。

解决：

最常见的则是采用布隆过滤器，将所有可能存在的数据哈希到一个足够大的bitmap中，一个一定不存在的数据会被这个bitmap拦截掉，从而避免了对底层存储系统的查询压力。

另外也有一个更为简单粗暴的方法（我们采用的就是这种），如果一个查询返回的数据为空（不管是数据不存在，还是系统故障），我们仍然把这个空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。

#### 缓存击穿

对于一些设置了过期时间的key，如果这些key可能会在某些时间点被超高并发地访问，是一种非常“热点”的数据。这个时候，需要考虑一个问题：缓存被“击穿”的问题，这个和缓存雪崩的区别在于这里针对某一key缓存，前者则是很多key。

缓存在某个时间点过期的时候，恰好在这个时间点对这个Key有大量的并发请求过来，这些请求发现缓存过期一般都会从后端DB加载数据并回设到缓存，这个时候大并发的请求可能会瞬间把后端DB压垮。

解决：

1.使用互斥锁(mutex key)

业界比较常用的做法，是使用mutex。简单地来说，就是在缓存失效的时候（判断拿出来的值为空），不是立即去load db，而是先使用缓存工具的某些带成功操作返回值的操作（比如Redis的SETNX或者Memcache的ADD）去set一个mutex key，当操作返回成功时，再进行load db的操作并回设缓存；否则，就重试整个get缓存的方法。

SETNX，是「SET if Not eXists」的缩写，也就是只有不存在的时候才设置，可以利用它来实现锁的效果。在redis2.6.1之前版本未实现setnx的过期时间，所以这里给出两种版本代码参考：

```
//2.6.1前单机版本锁
String get(String key) {  
   String value = redis.get(key);  
   if (value  == null) {  
    if (redis.setnx(key_mutex, "1")) {  
        // 3 min timeout to avoid mutex holder crash  
        redis.expire(key_mutex, 3 * 60)  
        value = db.get(key);  
        redis.set(key, value);  
        redis.delete(key_mutex);  
    } else {  
        //其他线程休息50毫秒后重试  
        Thread.sleep(50);  
        get(key);  
    }  
  }  
}
```

```
public String get(key) {
      String value = redis.get(key);
      if (value == null) { //代表缓存值过期
          //设置3min的超时，防止del操作失败的时候，下次缓存过期一直不能load db
		  if (redis.setnx(key_mutex, 1, 3 * 60) == 1) {  //代表设置成功
               value = db.get(key);
                      redis.set(key, value, expire_secs);
                      redis.del(key_mutex);
              } else {  //这个时候代表同时候的其他线程已经load db并回设到缓存了，这时候重试获取缓存值即可
                      sleep(50);
                      get(key);  //重试
              }
          } else {
              return value;      
          }
 }
```

memcache代码：

```
if (memcache.get(key) == null) {  
    // 3 min timeout to avoid mutex holder crash  
    if (memcache.add(key_mutex, 3 * 60 * 1000) == true) {  
        value = db.get(key);  
        memcache.set(key, value);  
        memcache.delete(key_mutex);  
    } else {  
        sleep(50);  
        retry();  
    }  
} 
```

2. 后台刷新，后台定义一个job(定时任务)专门主动更新缓存数据。比如,一个缓存中的数据过期时间是30分钟,那么job每隔29分钟定时刷新数据(将从数据库中查到的数据更新到缓存中)。这种方案比较容易理解，但会增加系统复杂度。比较适合那些 key 相对固定,cache粒度较大的业务，key 比较分散的则不太适合，实现起来也比较复杂。

3. 检查更新，将缓存key的过期时间(绝对时间)一起保存到缓存中(可以拼接,可以添加新字段,可以采用单独的key保存..不管用什么方式,只要两者建立好关联关系就行)。在每次执行get操作后,都将get出来的缓存过期时间与当前系统时间做一个对比,如果缓存过期时间-当前系统时间<=1分钟(自定义的一个值),则主动更新缓存。这样就能保证缓存中的数据始终是最新的(和方案一一样,让数据不过期。)

   这种方案在特殊情况下也会有问题。假设缓存过期时间是12:00，而 11:59 到 12:00这 1 分钟时间里恰好没有 get 请求过来，又恰好请求都在 11:30 分的时 候高并发过来，那就悲剧了。这种情况比较极端，但并不是没有可能。因为“高并发”也可能是阶段性在某个时间点爆发。

4. 分级缓存，采用 L1 (一级缓存)和 L2(二级缓存) 缓存方式，L1 缓存失效时间短，L2 缓存失效时间长。 请求优先从 L1 缓存获取数据，如果 L1缓存未命中则加锁，只有 1 个线程获取到锁,这个线程再从数据库中读取数据并将数据再更新到到 L1 缓存和 L2 缓存中，而其他线程依旧从 L2 缓存获取数据并返回。

   这种方式，主要是通过避免缓存同时失效并结合锁机制实现。所以，当数据更新时，只能淘汰 L1 缓存，不能同时将 L1 和 L2 中的缓存同时淘汰。L2 缓存中可能会存在脏数据，需要业务能够容忍这种短时间的不一致。而且，这种方案 可能会造成额外的缓存空间浪费。

#### 缓存雪崩（失效）

缓存雪崩是指在我们设置缓存时采用了相同的过期时间，导致缓存在某一时刻同时失效，请求全部转发到DB，DB瞬时压力过重雪崩。

解决：

缓存失效时的雪崩效应对底层系统的冲击非常可怕。大多数系统设计者考虑用加锁或者队列的方式保证缓存的单线 程（进程）写，从而避免失效时大量的并发请求落到底层存储系统上。这里分享一个简单方案就时讲缓存失效时间分散开，比如我们可以在原有的失效时间基础上增加一个随机值，比如1-5分钟随机，这样每一个缓存的过期时间的重复率就会降低，就很难引发集体失效的事件。

























