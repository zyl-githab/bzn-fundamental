package com.bzn.fundamental.protocol.netty;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bzn.fundamental.cluster.loadbalance.LoadBalanceExecutor;
import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.ConnectionEntity;
import com.bzn.fundamental.event.protocol.ProtocolEventFactory;
import com.bzn.fundamental.protocol.AbstractClientInterceptor;
import com.bzn.fundamental.protocol.ClientInterceptorAdapter;
import com.bzn.fundamental.protocol.ProtocolRequest;
import com.bzn.fundamental.protocol.redis.sentinel.RedisPublisher;
import com.bzn.fundamental.protocol.redis.sentinel.RedisSentinelPoolFactory;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import io.netty.channel.ChannelFuture;

public class NettyClientInterceptor extends AbstractClientInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(NettyClientInterceptor.class);

    @Override
    public void invokeAsync(ProtocolRequest request) throws Exception {
        String interfaze = request.getInterface();

        LoadBalanceExecutor loadBalanceExecutor = executorContainer.getLoadBalanceExecutor();
        ConnectionEntity connectionEntity = null;
        try {
            connectionEntity = loadBalanceExecutor.loadBalance(interfaze);
        } catch (Exception e) {
            request.setException(e);
            ProtocolEventFactory.postClientProducerEvent(cacheContainer.getProtocolEntity().getType(), request);
            
            throw e;
        }
        
        if (connectionEntity == null) {
            return;
        }

        ChannelFuture channelFuture = (ChannelFuture) connectionEntity.getConnectionHandler();
        if (channelFuture != null) {
            channelFuture.channel().writeAndFlush(request);
        }
    }

    @Override
    public Object invokeSync(ProtocolRequest request) throws Exception {
        ClientInterceptorAdapter clientInterceptorAdapter = executorContainer.getClientInterceptorAdapter();

        return clientInterceptorAdapter.invokeSync(this, request);
    }

    @Override
    public void invokeBroadcast(ProtocolRequest request) throws Exception {
        boolean redisEnabled = RedisSentinelPoolFactory.enabled();
        if (redisEnabled) {
            invokeRedisBroadcast(request);
        } else {
            LOG.info("Redis broadcast is disabled, use round broadcast");

            invokeRoundBroadcast(request);
        }
    }

    private void invokeRedisBroadcast(ProtocolRequest request) throws Exception {
        ApplicationEntity applicationEntity = cacheContainer.getApplicationEntity();

        RedisPublisher publisher = new RedisPublisher();
        publisher.publish(request, applicationEntity);
    }

    private void invokeRoundBroadcast(ProtocolRequest request) throws Exception {
        String interfaze = request.getInterface();
        
        List<ConnectionEntity> connectionEntityList = cacheContainer.getConnectionCacheEntity().getConnectionEntityList(interfaze);
        for (ConnectionEntity connectionEntity : connectionEntityList) {
            ApplicationEntity applicationEntity = connectionEntity.getApplicationEntity();
            ChannelFuture channelFuture = (ChannelFuture) connectionEntity.getConnectionHandler();
            if (channelFuture != null) {
                try {
                    channelFuture.channel().writeAndFlush(request);
                } catch (Exception e) {
                    LOG.error("Async broadcast failed, host={}, port={}, service={}, method={}", applicationEntity.getHost(), applicationEntity.getPort(), request.getInterface(), request.getMethod());
                    throw e;
                }
            }
        }
    }
}