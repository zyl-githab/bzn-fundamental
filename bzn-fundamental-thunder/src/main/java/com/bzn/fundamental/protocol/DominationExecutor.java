package com.bzn.fundamental.protocol;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.entity.ApplicationType;

public interface DominationExecutor extends ThunderDelegate {

    // 监控处理
    void handleMonitor(ProtocolMessage message);
    
    // EventBus异步事件处理
    void handleEvent(ProtocolMessage message, ApplicationType applicationType);
}