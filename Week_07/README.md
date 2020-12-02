# 13.2 (必做)按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
[1.SpringBoot+Mybatis+hikari链接池，单测实现，耗时非常长，3个小时作用](my-dal/src/test/java/com/example/mydal/dao/UserMapperTest.java#L42)

[2. PreparedStatement+autoCommit=false+addBatch+每批次1000条 耗时171145ms](my-dal/src/test/java/com/example/mydal/db/MyDataSourceUtilsTest.java#L39)

[3. PreparedStatement+autoCommit=false+addBatch+每批次10000条 耗时134193ms](my-dal/src/test/java/com/example/mydal/db/MyDataSourceUtilsTest.java#L61)

[4. PreparedStatement+autoCommit=false+addBatch+一百万数据一次提交 耗时118307ms](my-dal/src/test/java/com/example/mydal/db/MyDataSourceUtilsTest.java#L61)

- 批次处理，先记录下数据在统一访问提交到数据库，由2、3、4得知访问访问数据库次数越少，越快，当然，记录缓存的数据也不能太大会OOM
# 13.6 (选做)尝试自己做一个ID生成器(可以模拟Seq或Snowflake)。
 [com.example.mydal.support.SequenceSupport](my-dal/src/main/java/com/example/mydal/support/SequenceSupport.java)
# 14.2 读写分离-动态切换数据源版本1.0
 [动态数据源配置](my-dal/src/main/java/com/example/mydal/db)

 [切面配置方法使用的数据源](my-dal/src/main/java/com/example/mydal/annotation/ReadOnlyAspect.java)

 # 14.3 读写分离-数据库框架版本2.0
