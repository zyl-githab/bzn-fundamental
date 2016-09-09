package com.bzn.fundamental.cluster.loadbalance;

import com.bzn.fundamental.common.delegate.ThunderDelegate;
import com.bzn.fundamental.common.entity.ConnectionEntity;

public interface LoadBalanceExecutor extends ThunderDelegate {  
    // 负载均衡
    ConnectionEntity loadBalance(String interfaze) throws Exception;
}