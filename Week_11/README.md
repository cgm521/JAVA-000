# 4、(必做)基于Redis封装分布式数据操作:
## 1)在Java中实现一个简单的分布式锁;
- 加锁 [JedisUtils#lock](redis-demo/src/main/java/com/example/redisdemo/jedis/JedisUtils.java#L32-L51)
- 释放锁[JedisUtils#unLock](redis-demo/src/main/java/com/example/redisdemo/jedis/JedisUtils.java#L53-L74)
- 测试类[RedisDemoApplicationTests#concurrentLock](redis-demo/src/test/java/com/example/redisdemo/RedisDemoApplicationTests.java#L20-L44)
