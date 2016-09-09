package com.bzn.fundamental.protocol.redis.sentinel;

import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.DestinationEntity;

public class RedisDestinationUtil {
    public static DestinationEntity createDestinationEntity(String interfaze, ApplicationEntity applicationEntity) {
        DestinationEntity destinationEntity = new DestinationEntity(interfaze, applicationEntity);

        return destinationEntity;
    }
}