package com.bzn.fundamental.registry;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.entity.RegistryEntity;
import com.bzn.fundamental.common.property.ThunderProperties;

public interface RegistryInitializer extends ThunderDelegate {
    
    // 启动和注册中心的连接
    void start(RegistryEntity registryEntity) throws Exception;

    // 启动和注册中心的连接
    void start(RegistryEntity registryEntity, ThunderProperties properties) throws Exception;

    // 停止注册中心的连接
    void stop() throws Exception;
}