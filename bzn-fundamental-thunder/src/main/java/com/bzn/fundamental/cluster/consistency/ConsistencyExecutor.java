package com.bzn.fundamental.cluster.consistency;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.event.registry.ServiceInstanceEvent;

public interface ConsistencyExecutor extends ThunderDelegate {
    // 接受注册中心上下线事件，本地缓存与注册中心保持一致
    void consist(ServiceInstanceEvent event) throws Exception;
}