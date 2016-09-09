package com.bzn.fundamental.protocol.kafka;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.Arrays;
import java.util.concurrent.Callable;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bzn.fundamental.common.constant.ThunderConstants;
import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.MQPropertyEntity;
import com.bzn.fundamental.common.thread.ThreadPoolFactory;
import com.bzn.fundamental.protocol.ClientExecutorAdapter;
import com.bzn.fundamental.protocol.ProtocolResponse;
import com.bzn.fundamental.serialization.SerializerExecutor;

public class KafkaMQClientHandler extends KafkaMQConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMQClientHandler.class);
    
    private int consumerClientPollTimeout = 500;
    private boolean transportLogPrint;
    
    public KafkaMQClientHandler(MQPropertyEntity mqPropertyEntity, String groupId) {
        super(mqPropertyEntity, groupId);
        
        try {
            consumerClientPollTimeout = mqPropertyEntity.getInteger(ThunderConstants.KAFKA_CONSUMER_CLIENT_POLL_TIMEOUT_ATTRIBUTE_NAME);
            transportLogPrint = mqPropertyEntity.getBoolean(ThunderConstants.TRANSPORT_LOG_PRINT_ATTRIBUTE_NAME);
        } catch (Exception e) {
            LOG.error("Get properties failed", e);
        }
    }

    public void consume(final String topic, final String interfaze, final ApplicationEntity applicationEntity) throws Exception {  
        final String url = applicationEntity.toUrl();
        ThreadPoolFactory.createThreadPoolClientExecutor(url, interfaze).submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                int partitionIndex = getPartitionIndex(consumer, topic, url);
                TopicPartition partition = new TopicPartition(topic, partitionIndex);
                consumer.assign(Arrays.asList(partition));
                
                while (true) {
                    ConsumerRecords<String, byte[]> records = consumer.poll(consumerClientPollTimeout);
                    if (records != null && records.count() != 0) {
                        for (ConsumerRecord<String, byte[]> record : records) {
                            ProtocolResponse response = SerializerExecutor.deserialize(record.value());
                            try {
                                String responseSource = response.getResponseSource().toString();
                                // String requestSource = response.getRequestSource().toString();
                                
                                if (transportLogPrint) {
                                    LOG.info("Response from server={}, service={}", responseSource, interfaze);
                                }
                                
                                ClientExecutorAdapter clientExecutorAdapter = executorContainer.getClientExecutorAdapter();
                                clientExecutorAdapter.handle(response);
                            } catch (Exception e) {
                                LOG.error("Consume request failed", e);
                            }
                        }
                    }
                }
            }
        });
    }
    
    @SuppressWarnings("resource")
    private int getPartitionIndex(Consumer<String, byte[]> consumer, String topic, String key) {
        int partitionNumber = consumer.partitionsFor(topic).size();
        
        StringSerializer keySerializer = new StringSerializer();
        byte[] serializedKey = keySerializer.serialize(topic, key);

        int positive = Utils.murmur2(serializedKey) & 0x7fffffff;

        return positive % partitionNumber;
    }
}