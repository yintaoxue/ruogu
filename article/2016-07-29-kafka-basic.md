#Kafka

### Kafka基础
无论是否消费Kafka都会保留所有的消息，超过设定时间才会丢弃；存储大量数据对性能不会有太大影响；

Topic是由分区的commit log组成，有序的不可变的消息队列，每个partition都有一个offset id，唯一定位一条消息；

由消费者来记录消费消息的位置offset，轻量级的consumer，不会影响其他consumer和集群。

partition的作用：支持横向扩展，增加单个topic能够处理的数据量，提高并行化，分布式，replica

每个server负责处理其中一个partition，可以配置副本数量
每个partition都有一个leader，0个或多个follower，leader负责该partition的数据读写请求，follower被动同步数据
由producer决定将消息发送到topic的哪个patition

### 两种消息模型：消息队列和发布订阅模式

[Message queue](https://en.wikipedia.org/wiki/Message_queue)
一条消息根据策略分配给一个consumer

[Publish–subscribe pattern](https://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern)
一条消息发送给所有订阅者

kafka支持两种消息模型，同一个consumer group表示是一组consumer，一条消息只会被同一个group中的一个consumer消费。consumer实例可以是不同的进程或不同机器上的应用。
同一个consumer group相当于队列模式，不同的group间相当于是发布订阅模式

![](assets/20160729-kafka-cluster-group.png)

一个partition只会被一个consumer消费，consumer的数量小于等于partion的数量。
kafka只能保证同一个partition内的消息是有序的，可以通过consumer和partition的数量相同来保证同一个consumer读取一个partition的消息是有序处理的

### Use Cases
Messaging     与生产者系统解耦，缓存未处理数据等
Website Activity Tracking     site activity (page views, searches, or other actions users may take)
Metrics
Log Aggregation
Stream Processing
Event Sourcing
Commit Log

### Kafka版本升级
热升级

每个节点升级程序启动，依次升级
升级到0.10.0.0版本，协议有升级，需要先顺序升级程序，增加参数使用旧版本，再顺序升级协议，最后顺序升级日志格式版本

性能影响：
http://kafka.apache.org/documentation.html#upgrade_10_performance_impact

zero-copy


### 设计思路

read-ahead and write-behind

磁盘读写速度测试 
http://queue.acm.org/detail.cfm?id=1563874

![](http://deliveryimages.acm.org/10.1145/1570000/1563874/jacobs3.jpg)

- The memory overhead of objects is very high, often doubling the size of the data stored (or worse).
- Java garbage collection becomes increasingly fiddly and slow as the in-heap data increases.

pagecache-centric design
http://varnish.projects.linpro.no/wiki/ArchitectNotes

message set

http://man7.org/linux/man-pages/man2/sendfile.2.html


### 思考
1. 重启消费者客户端，消息的offset从什么地方开始？
	
	消费者客户端会定时，默认60*1000ms，同步offset到zookeeper上，所以在客户端重启或者该partition分配到cusumer group的其他节点时，会继续zk中记录的offset
2. 性能测试

### To be continue...

### Refrences

* http://kafka.apache.org/documentation.html

