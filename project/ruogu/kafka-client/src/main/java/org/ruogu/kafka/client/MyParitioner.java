package org.ruogu.kafka.client;

import org.apache.kafka.clients.producer.Partitioner;

/**
 * MyParitioner
 * 
 * @author xueyintao 2016年8月4日
 */
public class MyParitioner implements Partitioner {

	public int partition(Object key, int numPartitions) {
		return Math.abs(key.hashCode() % numPartitions);
	}

}
