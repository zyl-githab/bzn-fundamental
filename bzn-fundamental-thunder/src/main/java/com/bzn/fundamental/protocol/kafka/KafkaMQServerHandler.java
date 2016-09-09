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

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bzn.fundamental.common.constant.ThunderConstants;
import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.MQPropertyEntity;
import com.bzn.fundamental.common.thread.ThreadPoolFactory;
import com.bzn.fundamental.protocol.ProtocolRequest;
import com.bzn.fundamental.protocol.ProtocolResponse;
import com.bzn.fundamental.protocol.ServerExecutorAdapter;
import com.bzn.fundamental.serialization.SerializerExecutor;

public class KafkaMQServerHandler extends KafkaMQConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMQServerHandler.class);

    private KafkaMQProducer producer;
    private int consumerServerPollTimeout = 500;
    private boolean transportLogPrint;

    public KafkaMQServerHandler(MQPropertyEntity mqPropertyEntity, String groupId) {
        super(mqPropertyEntity, groupId);

        try {
            consumerServerPollTimeout = mqPropertyEntity.getInteger(ThunderConstants.KAFKA_CONSUMER_SERVER_POLL_TIMEOUT_ATTRIBUTE_NAME);
            transportLogPrint = mqPropertyEntity.getBoolean(ThunderConstants.TRANSPORT_LOG_PRINT_ATTRIBUTE_NAME);
        } catch (Exception e) {
            LOG.error("Get properties failed", e);
        }

        producer = new KafkaMQProducer(mqPropertyEntity);
    }

    public void consume(final String responseTopic, final String requestTopic, final String interfaze, final ApplicationEntity applicationEntity) throws Exception {
        String url = applicationEntity.toUrl();
        ThreadPoolFactory.createThreadPoolServerExecutor(url, interfaze).submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                consumer.subscribe(Arrays.asList(responseTopic));

                while (true) {
                    ConsumerRecords<String, byte[]> records = consumer.poll(consumerServerPollTimeout);
                    if (records != null && records.count() != 0) {
                        for (ConsumerRecord<String, byte[]> record : records) {
                            ProtocolRequest request = SerializerExecutor.deserialize(record.value());
                            String requestSource = request.getRequestSource().toString();
                            
                            if (transportLogPrint) {
                                LOG.info("Request from client={}, service={}", requestSource, interfaze);
                            }
                            
                            ProtocolResponse response = new ProtocolResponse();
                            try {
                                ServerExecutorAdapter serverExecutorAdapter = executorContainer.getServerExecutorAdapter();
                                serverExecutorAdapter.handle(request, response);
                            } catch (Exception e) {
                                LOG.error("Consume request failed", e);
                            }

                            boolean feedback = request.isFeedback();
                            if (feedback) {
                                try {
                                    producer.produceResponse(requestTopic, applicationEntity, response, requestSource);
                                } catch (Exception e) {
                                    LOG.error("Produce response failed", e);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}