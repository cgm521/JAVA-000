roaringbitmap 做去重
rocketMq
kafka
	https://shimo.im/docs/GVHqtQc36PgTQ6Wt/read
	https://www.infoq.cn/article/kafka-analysis-part-1/
 consumer group 是一个消费分组，可以消费一个topic 提升消费能力，一个 consumer group 可以有多个consumer并发消费
ifconfig | grep "inet"


docker run -d --name kafka9092 -p 9092:9092 -e KAFKA_BROKER_ID=10 -e KAFKA_ZOOKEEPER_CONNECT=192.168.0.102:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.0.102:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -v /etc/localtime:/etc/localtime wurstmeister/kafka

docker run -d --name kafka9093 -p 9093:9092 -e KAFKA_BROKER_ID=11 -e KAFKA_ZOOKEEPER_CONNECT=192.168.0.102:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.0.102:9093 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -v /etc/localtime:/etc/localtime wurstmeister/kafka

docker run -d --name kafka9094 -p 9094:9092 -e KAFKA_BROKER_ID=22 -e KAFKA_ZOOKEEPER_CONNECT=192.168.0.102:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.0.102:9094 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -v /etc/localtime:/etc/localtime wurstmeister/kafka
创建topic
bin/kafka-topics.sh --zookeeper 192.168.0.102:2181 --create --topic test32 --partitions 3 - -replication-factor 2

查看topic
./bin/kafka-topics.sh --zookeeper 192.168.0.102:2181 --describe --topic test32

客户端监听
./kafka-console-consumer.sh --bootstrap-server 192.168.0.102:9092 --topic test32 --from-beginning


./kafka-topics.sh  --create --topic testk --partitions 4  --replication-factor 1

bin/kafka-topics.sh --zookeeper 192.168.0.102:2181 --create --topic testk --partitions 3  --replication-factor 1