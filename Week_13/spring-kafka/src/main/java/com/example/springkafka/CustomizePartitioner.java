package com.example.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2021/1/12 下午11:53
 */
@Slf4j
public class CustomizePartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        log.info("自定义分区策略，topic：{},key:{},value:{},cluster:{}", topic, key, value, cluster);
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        log.info("分区信息：{}", partitions);
        int numPartitions = partitions.size();
        // hash the keyBytes to choose a partition
        return Utils.toPositive(Utils.murmur2(valueBytes)) % numPartitions;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
