package org.ruogu.kafka.client;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


/**
 * Producer
 * 
 * @author xueyintao 2016年8月4日
 */
public class Producer {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("partitioner.class", "org.ruogu.kafka.client.MyParitioner");

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		for (int i = 0; i < 10; i++) {
			producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), "msg-" + i));
			System.out.println("msg-" + i);
		}

		Thread.sleep(10000L);

		producer.close();
	}

}
