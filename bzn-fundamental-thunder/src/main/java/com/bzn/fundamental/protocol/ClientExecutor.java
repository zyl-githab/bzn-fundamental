package com.bzn.fundamental.protocol;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.ConnectionEntity;

public interface ClientExecutor extends ThunderDelegate {

    // 客户端启动连接
    void start(String interfaze, ApplicationEntity applicationEntity) throws Exception;

    // 客户端是否启动
    boolean started(String interfaze, ApplicationEntity applicationEntity) throws Exception;

    // 客户端上线，更新缓存
    ConnectionEntity online(String interfaze, ApplicationEntity applicationEntity, Object connnectionHandler) throws Exception;

    // 客户端下线，更新缓存
    void offline(String interfaze, ApplicationEntity applicationEntity) throws Exception;
}