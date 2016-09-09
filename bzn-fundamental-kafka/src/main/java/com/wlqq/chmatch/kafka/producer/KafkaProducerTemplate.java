package com.wlqq.chmatch.kafka.producer;

import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 生产者发送模版
 * 
 * @author：fengli
 * @since：2016年6月24日 上午11:46:41
 * @version:
 */
public class KafkaProducerTemplate extends KafkaProducerBaseTemplate {

	private List<String> topics;

	public KafkaProducerTemplate() {
		super();
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	@Override
	public void sendAsync(String topic, String value) {
		if (!topics.contains(topic)) {
			throw new IllegalArgumentException("Invalid topic value:" + topic);
		}
		final ProducerRecord<byte[], byte[]> data = new ProducerRecord<byte[], byte[]>(
				topic, value.getBytes());
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				producer.send(data);
			}
		});
	}

	@Override
	public void sendAsync(String topic, String key, String value) {
		if (!topics.contains(topic)) {
			throw new IllegalArgumentException("Invalid topic value:" + topic);
		}
		final ProducerRecord<byte[], byte[]> data = new ProducerRecord<byte[], byte[]>(topic,
				value.getBytes());
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				producer.send(data);
			}
		});
	}

	@Override
	public void sendSync(String topic, String key, String value) {
		if (!topics.contains(topic)) {
			throw new IllegalArgumentException("Invalid topic value:" + topic);
		}
		ProducerRecord<byte[], byte[]> data = new ProducerRecord<byte[], byte[]>(topic, value.getBytes());
		producer.send(data);
	}

	@Override
	public void sendSync(String topic, String value) {
		if (!topics.contains(topic)) {
			throw new IllegalArgumentException("Invalid topic value:" + topic);
		}
		ProducerRecord<byte[], byte[]> data = new ProducerRecord<byte[], byte[]>(topic, value.getBytes());
		producer.send(data);
	}

}
