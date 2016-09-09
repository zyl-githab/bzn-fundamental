package com.bzn.fundamental.protocol.redis.sentinel;

import com.bzn.fundamental.common.entity.ApplicationEntity;

public class RedisHierachy {
    public String createChannel(String interfaze, ApplicationEntity applicationEntity) {
        return RedisDestinationUtil.createDestinationEntity(interfaze, applicationEntity).toString();
    }
}