package com.bzn.fundamental.protocol.kafka;

import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.DestinationEntity;
import com.bzn.fundamental.common.entity.DestinationType;

public class KafkaMQDestinationUtil {
    public static DestinationEntity createDestinationEntity(DestinationType destinationType, String interfaze, ApplicationEntity applicationEntity) throws Exception {
        DestinationEntity destinationEntity = new DestinationEntity(destinationType, interfaze, applicationEntity);

        return destinationEntity;
    }
    
    public static DestinationEntity createDestinationEntity(String interfaze, ApplicationEntity applicationEntity) throws Exception {
        DestinationEntity destinationEntity = new DestinationEntity(interfaze, applicationEntity);

        return destinationEntity;
    }
}