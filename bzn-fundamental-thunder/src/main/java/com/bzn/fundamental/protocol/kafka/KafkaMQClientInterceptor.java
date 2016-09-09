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

import java.util.Map;

import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.DestinationType;
import com.bzn.fundamental.common.entity.ReferenceEntity;
import com.bzn.fundamental.protocol.AbstractClientInterceptor;
import com.bzn.fundamental.protocol.ClientInterceptorAdapter;
import com.bzn.fundamental.protocol.ProtocolRequest;

public class KafkaMQClientInterceptor extends AbstractClientInterceptor {

    @Override
    public void invokeAsync(ProtocolRequest request) throws Exception {
        String interfaze = request.getInterface();
        
        ApplicationEntity applicationEntity = cacheContainer.getApplicationEntity();
        
        String topic = KafkaMQDestinationUtil.createDestinationEntity(DestinationType.RESPONSE_QUEUE, interfaze, applicationEntity).toString();
        
        KafkaMQProducer producer = getProducer(interfaze);
        producer.produceRequest(topic, applicationEntity, request);
    }

    @Override
    public Object invokeSync(ProtocolRequest request) throws Exception {
        ClientInterceptorAdapter clientInterceptorAdapter = executorContainer.getClientInterceptorAdapter();

        return clientInterceptorAdapter.invokeSync(this, request);
    }

    @Override
    public void invokeBroadcast(ProtocolRequest request) throws Exception {        
        String interfaze = request.getInterface();
        
        ApplicationEntity applicationEntity = cacheContainer.getApplicationEntity();
        
        String topic = KafkaMQDestinationUtil.createDestinationEntity(DestinationType.RESPONSE_TOPIC, interfaze, applicationEntity).toString();
        
        KafkaMQProducer producer = getProducer(interfaze);
        producer.produceRequest(topic, applicationEntity, request);
    }

    private KafkaMQProducer getProducer(String interfaze) {
        Map<String, ReferenceEntity> referenceEntityMap = cacheContainer.getReferenceEntityMap();
        ReferenceEntity referenceEntity = referenceEntityMap.get(interfaze);
        String server = referenceEntity.getServer();

        return KafkaMQCacheContainer.getReferenceContextMap().get(server).getProducer();
    }
}