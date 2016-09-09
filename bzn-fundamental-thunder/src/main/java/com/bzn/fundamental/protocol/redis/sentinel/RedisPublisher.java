package com.bzn.fundamental.protocol.redis.sentinel;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.thread.ThreadPoolFactory;
import com.bzn.fundamental.protocol.ProtocolRequest;
import com.bzn.fundamental.serialization.SerializerExecutor;

import redis.clients.jedis.Jedis;

public class RedisPublisher extends RedisHierachy {
    private static final Logger LOG = LoggerFactory.getLogger(RedisPublisher.class);

    public void publish(final ProtocolRequest request, final ApplicationEntity applicationEntity) throws Exception {
        final Jedis jedis = RedisSentinelPoolFactory.getResource();
        if (jedis == null) {
            LOG.error("No redis sentinel resource found, publish failed");
            
            return;
        }
        
        final String url = applicationEntity.toUrl();
        final String interfaze = request.getInterface();
        ThreadPoolFactory.createThreadPoolClientExecutor(url, interfaze).submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                String channel = createChannel(interfaze, applicationEntity);

                try {
                    String json = SerializerExecutor.toJson(request);
                    jedis.publish(channel, json);
                } catch (Exception e) {
                    LOG.error("Publish failed", e);
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }

                return null;
            }
        });
    }
}