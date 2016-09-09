package com.bzn.fundamental.registry;

import com.bzn.fundamental.common.entity.ProtocolType;

// 提供给外部程序所用
public interface RegistryLauncher {

    // 启动注册中心连接
    void start(String address, ProtocolType protocolType) throws Exception;

    // 停止注册中心连接
    void stop() throws Exception;

    // 获取注册中心执行器
    RegistryExecutor getRegistryExecutor();
}