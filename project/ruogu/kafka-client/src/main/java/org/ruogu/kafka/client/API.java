package org.ruogu.kafka.client;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * API
 * 
 * @author xueyintao 2016年8月10日 下午7:29:19
 */
public class API {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		StringSerializer ss = new StringSerializer();
		ByteArraySerializer bs = new ByteArraySerializer();
	}

}
