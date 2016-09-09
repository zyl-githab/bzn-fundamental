package com.wlqq.chmatch.kafka;

import org.springframework.beans.factory.FactoryBean;

import com.wlqq.chmatch.kafka.producer.KafkaProducerConfig;

/**
 * 消费端连接配置，配置zk相关的地址，自动提交offset的间隔时间
 * 
 * @author：fengli
 * @since：2016年6月24日 上午11:39:32
 * @version:
 */
public class KafkaProducerConnectionFactory implements FactoryBean<Object> {

	private KafkaProducerConfig producerConfig;

	public void setProducerConfig(KafkaProducerConfig producerConfig) {
		this.producerConfig = producerConfig;
	}

	@Override
	public Object getObject() throws Exception {
		return this;
	}

	@Override
	public Class<?> getObjectType() {
		return KafkaProducerConnectionFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public KafkaProducerConfig getProducerConfig() {
		return producerConfig;
	}
}
