package com.wlqq.chmatch.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskExecutor;

import com.wlqq.chmatch.kafka.KafkaProducerConnectionFactory;

/**
 * 生产者基础模版
 * 
 * @author：fengli
 * @since：2016年6月24日 上午11:43:12
 * @version:
 */
public abstract class KafkaProducerBaseTemplate implements InitializingBean, DisposableBean {

	protected KafkaProducer<byte[], byte[]> producer = null;

	protected TaskExecutor taskExecutor;

	private KafkaProducerConnectionFactory producerConnectionFactory;

	/**
	 * 异步发送
	 * 
	 * @param topic
	 * @param key
	 * @param value
	 */
	public abstract void sendAsync(String topic, String key, String value);

	/**
	 * 异步发送
	 * 
	 * @param topic
	 * @param value
	 */
	public abstract void sendAsync(String topic, String value);

	/**
	 * 同步发送
	 * 
	 * @param topic
	 * @param key
	 * @param value
	 */
	public abstract void sendSync(String topic, String key, String value);

	/**
	 * 同步发送
	 * 
	 * @param topic
	 * @param value
	 */
	public abstract void sendSync(String topic, String value);

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setProcessThreads(int processThreads) {
		if (processThreads <= 0) {
			throw new IllegalArgumentException("Invalid processThreads value:" + processThreads);
		}
	}

	public KafkaProducerConnectionFactory getProducerConnectionFactory() {
		return producerConnectionFactory;
	}

	public void setProducerConnectionFactory(
			KafkaProducerConnectionFactory producerConnectionFactory) {
		this.producerConnectionFactory = producerConnectionFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (producer == null) {
			producer = new KafkaProducer<byte[], byte[]>(
					this.producerConnectionFactory.getProducerConfig().getProducerConfig());
		}

	}

	@Override
	public void destroy() throws Exception {
		if (producer != null) {
			producer.close();
		}
	}

}
