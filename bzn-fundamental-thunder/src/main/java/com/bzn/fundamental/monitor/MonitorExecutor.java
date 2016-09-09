package com.bzn.fundamental.monitor;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.entity.MonitorStat;
import com.bzn.fundamental.protocol.ProtocolMessage;

public interface MonitorExecutor extends ThunderDelegate {

    // 创建监控对象
    MonitorStat createMonitorStat(ProtocolMessage message);
    
    // 执行监控过程
    void execute(MonitorStat monitorStat) throws Exception;
}