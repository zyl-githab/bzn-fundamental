package com.wlqq.kafka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wlqq.chmatch.kafka.producer.KafkaProducerTemplate;

public class Application {

	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
			KafkaProducerTemplate kafkaProducerTemplate = (KafkaProducerTemplate) context.getBean("kafkaProducerTemplate");
			
			for(int i=0;i<100000;i++)
			kafkaProducerTemplate.sendAsync("message_scan_count1", "张三李四觉得sad到底"+i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
