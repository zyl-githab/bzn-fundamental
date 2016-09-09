package com.wlqq.chmatch.kafka.producer;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

/**
 * 生产者属性配置
 * 
 * @author：fengli
 * @since：2016年6月24日 上午11:45:29
 * @version:
 */
public class KafkaProducerConfig {

	private String brokers;

	private String serializerClass;

	private String ack;

	public String getBrokers() {
		return brokers;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}

	public String getSerializerClass() {
		return serializerClass;
	}

	public void setSerializerClass(String serializerClass) {
		this.serializerClass = serializerClass;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public Properties getProducerConfig() {
		if (StringUtils.isBlank(brokers)) {
			throw new IllegalArgumentException("Blank brokers");
		}
		if (StringUtils.isBlank(serializerClass)) {
			throw new IllegalArgumentException("Blank serializerClass");
		}
		if (StringUtils.isBlank(ack)) {
			throw new IllegalArgumentException("Blank ack");
		}
		Properties props = new Properties();
		props.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, this.brokers);
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
		return props;
	}
}
