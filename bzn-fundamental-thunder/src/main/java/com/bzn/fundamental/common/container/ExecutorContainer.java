package com.bzn.fundamental.common.container;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

import java.util.List;

import com.bzn.fundamental.cluster.consistency.ConsistencyExecutor;
import com.bzn.fundamental.cluster.loadbalance.LoadBalanceExecutor;
import com.bzn.fundamental.monitor.MonitorExecutor;
import com.bzn.fundamental.protocol.ClientExecutor;
import com.bzn.fundamental.protocol.ClientExecutorAdapter;
import com.bzn.fundamental.protocol.ClientInterceptorAdapter;
import com.bzn.fundamental.protocol.ServerExecutor;
import com.bzn.fundamental.protocol.ServerExecutorAdapter;
import com.bzn.fundamental.registry.RegistryExecutor;
import com.bzn.fundamental.registry.RegistryInitializer;
import com.bzn.fundamental.security.SecurityExecutor;

public class ExecutorContainer {
    // 服务端启动适配器
    private ServerExecutorAdapter serverExecutorAdapter;
    
    // 客户端启动适配器
    private ClientExecutorAdapter clientExecutorAdapter;
    
    // 客户端调用拦截适配器
    private ClientInterceptorAdapter clientInterceptorAdapter;
    
    // 服务端启动器
    private ServerExecutor serverExecutor;
    
    // 客户端启动器
    private ClientExecutor clientExecutor;
    
    // 注册中心初始器
    private RegistryInitializer registryInitializer;
    
    // 注册中心执行器
    private RegistryExecutor registryExecutor;
    
    // 集群同步器
    private ConsistencyExecutor consistencyExecutor;
    
    // 负载均衡器
    private LoadBalanceExecutor loadBalanceExecutor;
    
    // 安全控制器
    private SecurityExecutor securityExecutor;
    
    // 监控器
    private List<MonitorExecutor> monitorExecutors;
    
    public ServerExecutorAdapter getServerExecutorAdapter() {
        return serverExecutorAdapter;
    }

    public void setServerExecutorAdapter(ServerExecutorAdapter serverExecutorAdapter) {
        this.serverExecutorAdapter = serverExecutorAdapter;
    }

    public ClientExecutorAdapter getClientExecutorAdapter() {
        return clientExecutorAdapter;
    }

    public void setClientExecutorAdapter(ClientExecutorAdapter clientExecutorAdapter) {
        this.clientExecutorAdapter = clientExecutorAdapter;
    }

    public ClientInterceptorAdapter getClientInterceptorAdapter() {
        return clientInterceptorAdapter;
    }

    public void setClientInterceptorAdapter(ClientInterceptorAdapter clientInterceptorAdapter) {
        this.clientInterceptorAdapter = clientInterceptorAdapter;
    }

    public ServerExecutor getServerExecutor() {
        return serverExecutor;
    }

    public void setServerExecutor(ServerExecutor serverExecutor) {
        this.serverExecutor = serverExecutor;
    }

    public ClientExecutor getClientExecutor() {
        return clientExecutor;
    }

    public void setClientExecutor(ClientExecutor clientExecutor) {
        this.clientExecutor = clientExecutor;
    }
    
    public RegistryInitializer getRegistryInitializer() {
        return registryInitializer;
    }

    public void setRegistryInitializer(RegistryInitializer registryInitializer) {
        this.registryInitializer = registryInitializer;
    }
    
    public RegistryExecutor getRegistryExecutor() {
        return registryExecutor;
    }

    public void setRegistryExecutor(RegistryExecutor registryExecutor) {
        this.registryExecutor = registryExecutor;
    }
    
    public ConsistencyExecutor getConsistencyExecutor() {
        return consistencyExecutor;
    }

    public void setConsistencyExecutor(ConsistencyExecutor consistencyExecutor) {
        this.consistencyExecutor = consistencyExecutor;
    }

    public LoadBalanceExecutor getLoadBalanceExecutor() {
        return loadBalanceExecutor;
    }

    public void setLoadBalanceExecutor(LoadBalanceExecutor loadBalanceExecutor) {
        this.loadBalanceExecutor = loadBalanceExecutor;
    }
    
    public SecurityExecutor getSecurityExecutor() {
        return securityExecutor;
    }

    public void setSecurityExecutor(SecurityExecutor securityExecutor) {
        this.securityExecutor = securityExecutor;
    }

    public List<MonitorExecutor> getMonitorExecutors() {
        return monitorExecutors;
    }

    public void setMonitorExecutors(List<MonitorExecutor> monitorExecutors) {
        this.monitorExecutors = monitorExecutors;
    }
}