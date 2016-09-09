package com.bzn.fundamental.protocol;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.entity.ApplicationEntity;

public interface ServerExecutor extends ThunderDelegate {
    // 服务端启动
    void start(String interfaze, ApplicationEntity applicationEntity) throws Exception;
    
    // 服务端是否启动
    boolean started(String interfaze, ApplicationEntity applicationEntity) throws Exception;
}