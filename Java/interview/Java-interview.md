# Java 面试题收集

## 基础篇

### 基础

**==和equals的区别**

答： ==比较的是变量内存中存放对象的地址，用来判断两个对象的地址是否相同，即是否指向同一个对象。<br/>
equals用来比较两个对象的内容是否相等。

**transient 的作用**

答： transient 可以应用于类的成员变量，指出该成员变量不应该在包含它的类实例已序列化时被序列化；

**throw 与 throws 的区别**

答： throw 抛出一个异常对象；throws 声明一个异常可能被抛出；

**volatile**

答： volatile 用于表示可以被多个线程异步修改的成员变量；

**线程安全性的几种级别**

1. 不可变的 (immutable) —— 该类实例不可变，所以不需要外部同步。例如String、Long、BigInteger；
2. 无条件的线程安全 (unconditionally thread-safe) —— 该类实例可变，但有足够的内部同步，所以实例可以被并发使用，无需外部同步。例如Random、ConcurrentHashMap；
3. 有条件的线程安全 (conditionally thread-safe) —— 除了部分方法为进行安全的并发操作而需要外部同步外，这种线程安全级别与无条件的线程安全相同。例如Collection.synchronized 包装的集合，它们的迭代器 (iterator) 要求外部同步；
4. 非线程安全 (not thread-safe) —— 该类实例可变。必须利用自己选择的外部同步包围每个方法调用。例如ArrayList、HahsMap；
5. 线程对立的 (thread-hostile) —— 该类不能被多个线程并发使用；

**多线程实现的四种方式**

1. 继承 Thread 类创建线程。
2. 实现 Runnable 接口创建线程。
3. 实现 Callable 接口通过 FutureTask 包装器来创建 Thread 线程。
4. 使用 ExecutorService、Callable、Future 实现有返回结果的线程。

**JSP中九个内置对象**

1. request
2. response
3. config
4. application
5. session
6. exception
7. page
8. out
9. pageContext

**JSP四个作用域**

1. ServletContext ---- context 域
2. HttpServletRequest ---- request 域
3. HttpSession ---- session 域
4. PageContext ---- page 域

### 集合

**Collection 里面有哪些子类？**<br/>

答：List、Set实现了 Collection 接口；


**List的特性**<br/>

答： 

1. 可以允许重复的对象；
2. 可以插入多个 null 元素；
3. 保持每个元素的插入顺序；
4. 常用实现类有：ArrayList、LinkedList、Vector；


**ArrayList和LinkedList的区别**

答：

1. ArrayList 实现了基于动态数组的数据结构，LinkedList基于链表的数据结构；
2. ArrayList 查找快，因为 LinkedList 要移动指针；
3. LinkedList 增删快，因为 ArrayList 要移动数据；

> 注：ArrayList和LinkedList 非线程同步。


**Set的特性**

1.  不允许重复对象；
2.  无序容器；
3.  只允许一个 null 元素；
4.  实现类：HashSet、LinkedHashSet、TreeSet

**HashSet 和 TreeSet 的区别**

1. HashSet 按照哈希算法来存取集合中的对象，存取速度比较快；
2. TreeSet 实现了 SortedSet 接口，能够对集合中的对象进行排序；

**Map的特性**

1. 提供 key-value 的映射；
2. 每个 key 对应唯一一个 value；

**HashTable 和 HashMap 的区别**

1. HashTable 线程安全；
2. HashTable 在遇到 null 时，会抛出 NullPointerException 异常；
3. HashMap 可以接受 null 的键值；

## 框架篇
### Spring

**Spring的优点**
1. 降低了组件之间的耦合，实现了软件各层之间的解耦；<br/>
2. 提供了AOP技术，容易实现权限拦截，运行期监控等；<br/>
3. 对于主流的框架提供了集成支持，如hibernate，JPA等；<br/>

**事务**

> 事务（Transaction)：由一些列对系统中数据进行访问与更新的操作所组成的一个程序执行逻辑单元；<br/>
> 事务特性：原子性、一致性、隔离性、持久性<br/>
>> 一致性：一个事务在执行之前和执行之后，数据库都必须处于一致性状态；<br/>
>> 隔离性：一个事务的执行不能受到其他事务干扰；<br/>
>> 原子性：一个事务的执行要么全部成功，要么全部失败回滚；<br/>
>> 持久性：一个事务的执行对数据库中的数据是永久性的；<br/>

> MySQL事务隔离级别
>> 读未提交：最低级别，任何情况都无法保证<br/>
>> 读已提交：可避免脏读的发生<br/>
>> 可重复读：可避免脏读、不可重复读的发生<br/>
>> 串行化：可避免脏读、不可重复读、幻读的发生<br/>

> 事务隔离性
>> 脏读：一个事务读取了被另一个事务改写但尚未提交的数据时；<br/>
>> 不可重复读：一个事务执行相同的查询两次或两次以上，但每次查询结果都不相同时；<br/>
>> 幻读：当一个事务（T1）读取几行记录后，另一个并发事务（T2）插入了一些记录；<br/>

Spring 事务包含分布式事务和单机事务，用得较多的是单机事务，也就是只操作一个数据库的事务。单机事务可以分为“编程式事务模型”（TransactionTemplate) 和 声明式事务模型（@Transactional），后者可以理解为 AOP + 编程事务模型。

**Spring 五个事务隔离级别和七个事务传播行为**

TransactionDefinition 接口中定义的隔离级别：

- ISOLATION_DEFAULT ： 默认隔离级别，使用数据库默认的事务隔离级别，另外四个与JDBC 的隔离级别相对应；
- ISOLATION_READ_UNCOMMITTED : 事务最低隔离级别，允许其他事务看到这个事务未提交数据，会产生脏读，不可重复读，幻读；
- ISOLATION_READ_COMMITTED ： 保证一个事务修改的数据提交后才能被其他事务读取，可避免脏读，不能避免不可重复读，幻读；
- ISOLATION_REPEATABLE_READ : 可防止脏读，不可重复读，但可能出现幻读；
- ISOLATION_SERIALIZABLE : 最高代价，最可靠隔离级别，事务被处理为顺序执行，避免了脏读、不可重复读、幻读；

TransactionDefinition 接口中定义的事务传播行为：

- PROPAGATION_REQUIRED : 如果存在一个事务，则支持当前事务，如果没有事务，则开启一个新的事务；
- PROPAGATION_SUPPORTS : 如果存在一个事务，支持当前事务，如果没有事务，则非事务执行；
- PROPAGATION_MANDATORY : 如果已经一个事务，支出当前事务，如果没有事务，则抛出异常；
- PROPAGATION_REQUIRES_NEW : 总是开启一个新的事务。如果存在事务，则将这个事务挂起；
- PROPAGATION_NOT_SUPPORTED : 总是非事务执行，并挂起任何存在的事务；
- PROPAGATION_NEVER : 总是非事务执行，如果存在一个活动事务，则抛出异常；
- PROPAGATION_NESTED ： 如果一个活动的事务存在，则运行一个嵌套的事务中，如果没有活动事务则按照"PROPAGATION_REQUIRED" 执行；

**Spring BeanFactory 与 FactoryBean 的区别**

答：

- BeanFactory

BeanFactory，以Factory结尾，表示它是一个工厂类(接口)，用于管理 Bean 的一个工厂。在 Spring 中，BeanFactory 是IOC容器的核心接口，职责包括：实例化、定位、配置应用程序中的对象及建立对象间的依赖。

- FactoryBean
	
以Bean 结尾，表示它是一个 Bean，不同于普通的 Bean 的是： 它实现了 FactoryBean<T> 接口的 bean，根据该 Bean 的 ID 从 BeanFactory中获取的实际上是 FactoryBean 的 getObject() 返回对象，而不是 FactoryBean 本身，如果要获取FactoryBean 对象，需要在 Id 前面加上一个"&"符号来获取。

**Spring 中的dao 和 service 是单例还是多例**

答：

Spring 生成的对象默认是单例的，可以通过 <i>bean</i> 的属性 scope="prototype" 改为非单例。注解形式： @Scope("prototype")

>Spring scope 的四种作用域：singleton、prototype、request、session

>> singleton: 单例，即 bean 对应的类只有一个实例；<br/>
>> prototype: 多例，即每次从容器中取出 bean 时，都会生成一个新的实例；<br/>
>> request: 每次接收一个请求时，都会生成一个新的实例；<br/>
>> session: 在每一个 session 中只有一个该对象；<br/>

### Spring cloud

**什么是Spring Cloud**

Spring Cloud 是一系列框架的集成，利用Spring boot 的开发便利性巧妙地简化了分布式系统基础设施开发，如服务注册、服务发现、配置中心、消息总线、负载均衡、断路器、数据监控等；

Spring Cloud 是一套分布式服务治理的框架，专注于服务之间的通讯、熔断、监控等。

Spring Boot 是 Spring 的一套快速配置脚手架，可以基于Spring Boot 快速开发单个微服务，Spring Cloud 是基于Spring Boot 实现的云应用开发工具； 


**什么是微服务**

答：微服务是系统架构上的一种设计风格，主旨将一个原本独立的系统拆分成多个小型服务，这些小型服务都在各自独立的进程中运行，服务之间通过基于 HTTP 的 RESTful API 进行通信协作。

**关键组件**

- Spring Cloud Config : 配置管理工具，可实现应用配置的外部化存储，支持客户端配置信息刷新、加密/解密配置内容等。
- Spring Cloud Netflix ： 核心组件，对多个 Netflix OSS 开源套件进行整合。
	- Eureka: 服务治理组件，包含服务注册中心，服务注册于发现机制的实现。
	- Hystrix: 容错管理组件，实现断路器模式，帮助服务依赖中出现的延迟和为故障提供强大的容错能力。
	- Ribbon: 客户端负载均衡的服务调用组件。 
	- Feign： 基于Ribbon 和 Hystrix 的声明式服务调用组件。
	- Zuul： 网关组件，提供智能路由，访问过滤等功能。
	- Archaius: 外部化配置组件。
- Spring Cloud Bus: 事件、消息总线，用于传播集群中的状态变化或事件，以触发后续的处理，比如用来动态刷新配置等。
- Spring Cloud Cluster: 针对Zookeeper、Redis、Hazelcast、Consul 的选举算法和通用状态模式的实现。
- Spring Cloud Consul: 服务发现与配置管理工具。
- Spring Cloud Stream: 通过Redis、Rabbit、或者 Kafka 实现的消费微服务，可以通过简单的声明式模型来发送和接收消息。
- Spring Cloud Security: 安全工具包，提供 Zuul 代理中对 OAuth2 客户端请求中继器。
- Spring Cloud ZooKeeper: 基于 Zookeeper 的服务发现与配置管理组件。
- Spring Cloud Starters: Spring Cloud 的基础组件，基于Spring Boot风格项目的基础依赖模块。
- Spring Cloud CLI: 用于快速创建 Spring Cloud 应用的 Spring Boot CLI 插件。
- ......
### Spring boot

**Spring boot 优缺点**

优点：

- 简化配置
- 简化依赖
- 插件扩展

缺点：

- 学习成本



## 设计模式篇

**单例模式**

介绍：

- 意图：保证一个类仅有一个实例，并提供一个访问它的全局访问点。
- 主要解决：一个全局使用的类频繁地创建与销毁。
- 何时使用：当你想控制实例数目，节省系统资源的时候。
- 如何解决：判断系统是否已经有这个单例，有则返回，没有则创建。
- 关键代码：构造函数私有化，提供静态方法调用，判断对象是否实例，返回实例对象。

**简单工厂**

介绍：

- 意图：定义一个创建对象的接口，让其之类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行。
- 主要解决：主要解决接口选择的问题。
- 何时使用：明确计划不同条件下创建不同实例。
- 如何解决：让其之类实现工厂接口,返回的也是一个抽象的产品。
- 关键代码：创建过程在其之类执行。

## 服务器篇

### Tomcat

**Tomcat目录层次结构**

- bin: 存放启动和关闭Tomcat 的脚本文件
- conf: 存放Tomcat 服务器的各种配置文件
- lib: 存放Tomcat 服务器支撑的 jar 包
- logs: 存放Tomcat 的日志文件
- temp: 存放Tomcat 运行时产生的临时文件
- webapps: web 资源的存放目录
- work: Tomcat 的工作目录

**Tomcat默认内存大小**

JVM 在启动的时候会自动设置 Heap size 的值、初始空间是物理内存的1/64，最大空间是物理内存的1/4，可利用JVM 提供的 -Xmn -Xms -Xmx等选项进行设置。

**Tomcat默认上传大小**

Tomcat 通过POST上传文件大小的最大值为2M。

**Tomcat默认最大请求数**

Tomcat 默认配置的最大请求数是150。

>操作系统对于进程中的线程数有限制，默认情况下，一个线程的栈需要预留 1M 的内存空间

>> Windos 每个进程中的线程数不允许超过 2000；<br/>
>> Linux 每个进程中的线程数不允许超过 1000； <br/>

## 概念及应用篇

**为什么要面向对象**

答： 面向对象实现了对系统可维护性、可扩展性、可重用性。思想：对对象的状态与行为进行归纳与分类，分析个体与个体间相互作用与影响方法。

**为什么使用MVC模式**

1. 各司其职，互不干涉；
2. 利于开发中的分工；
3. 利于组件重用；

优点：分层清晰，便于扩展；

缺点：复杂程度提高，降低运行效率；

**分布式Session 的几种实现方式**

1. 基于数据库的 Session 共享；
2. 基于 NFS 共享文件系统；
3. 基于Redis 进行 session 共享；
4. 基于 Cookie 进行 session 共享；

**Session 和 Cookie 的区别**

1. session 保存在服务器中，客户端不知道其中信息，cookie 保存在客户端，服务器能够知道其中信息；
2. session 中保存的是对象，cookie 保存的是字符串；
3. session 不能区分路径，同一用户在访问一个网站期间，所有 session 在任何一个地方都能访问到，cookie 中如果设置了路径参数，同一个网站中的不同路径下的 cookie 互相访问不到；

## 数据库篇

**MySQL InnoDB和 MyISAM 存储引擎区别**

1. InnoDB 支持事务，MyISAM 不支持事务；
2. InnoDB 支持行锁，MyISAM 支持表锁；
3. InnoDB 支持外键，MyISAM 不支持；
4. InnoDB 不支持全文索引，MyISAM 支持；

特点： MyISAM 适用于大量查询操作，InnoDB使用与大量增删操作。

**DROP,DELETE,TRUNCATE 的区别**

1. DELETE 语句执行删除的过程是每次从表中删除一行，并将该行的删除操作作为事务记录在日志中保存，以便于进行回滚操作。
2. TRUNCATE 语句是一次性充表中删除所有数据，并不把单独的删除操作记录在日志保存，删除行不可恢复，并且在删除过程中不会激活与表有关的删除触发器。执行速度快。
3. DROP 删除表结构及所有数据，并将表所占用空间全部释放，不能回滚，不会触发触发器。

注：速度上 drop > truncate > delete

**数据库范式**

1. 第一范式：无重复的列；
2. 第二范式：有主键，非主键字段依赖主键；
3. 第三范式：属性不依赖与其它非主属性；

**SQL 语句优化**

1. 尽量避免在 where 子句中使用 != 或 <> 操作符，否则将引起放弃使用索引而进行全表扫描；
2. 尽量避免在 where 子句中对字段进行 null 值判断；
3. 尽量避免 select * ，使用具体的列代替 *，避免多余的列；
4. 使用 where 限定具体要查询的数据，避免多余的行；
5. 是用 top , distinct 关键字减少多余重复的行；

**数据库索引**

1. 数据库索引是一种数据结构，它以额外的写入和存储空间为代价提高数据库表上的数据检索操作的速度，以维护索引数据结构。
2. 索引用于快速定位数据，而无需在每次访问数据库表时搜索数据库表中的每一行。可以使用数据库表的一列或多列创建索引，为快速随机查找和有序记录的有效访问提供基础。
3. 索引是来自表的所选数据列的副本。

> 索引会加快数据库的检索速度，但需要占物理和数据空间，索引会降低插入、删除、修改等维护任务的速度。


### Redis

**单线程的Redis为什么速度快**

- 纯内存操作
- 单线程操作，避免了频繁的上下文切换
- 采用的非阻塞I/O多路复用机制

> I/O 多路复用指的是，让单个线程来检查多个文件描述符(Socket) 的就绪状态，一旦某个描述符就绪(一般指读或写就绪)，能够通知程序进行相应的读写操作。


**Redis的数据类及使用场景**

- String： 常规的 get/set 操作。Value可以是 String 或数字，一般用于普通的 key/value 存储。
- Hash: 常用命令hget，hset，hgetall。Value，存放的是结构化对象，常用于操作其中某个字段。
- List： 常用命令 lpush，rpush，lpop，rpop，lrange，可应用于消息队列，或分页功能。
- Set： 常用命令sadd，spop，smembers，sunion，存储的是一堆不重复值的组合，可以做去重操作。
- Sorted Set：常用命令zadd，zrange，zrem，zcard，可以通过用户额外提供一个优先级参数来为成员排序。
- Pub/Sub： 发布订阅，能够实时订阅取消频道。