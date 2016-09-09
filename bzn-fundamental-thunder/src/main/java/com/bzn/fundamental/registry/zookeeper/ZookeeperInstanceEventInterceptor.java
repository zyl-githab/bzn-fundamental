package com.bzn.fundamental.registry.zookeeper;

import com.bzn.fundamental.common.entity.ApplicationEntity;
import com.bzn.fundamental.common.entity.ProtocolType;
import com.bzn.fundamental.event.eventbus.EventControllerFactory;
import com.bzn.fundamental.event.eventbus.EventControllerType;
import com.bzn.fundamental.event.registry.ReferenceInstanceEvent;
import com.bzn.fundamental.event.registry.ServiceInstanceEvent;
import com.bzn.fundamental.registry.RegistryExecutor;
import com.bzn.fundamental.registry.RegistryLauncher;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Neptune
 * @email 1394997@qq.com
 * @version 1.0
 */

import com.google.common.eventbus.Subscribe;

public abstract class ZookeeperInstanceEventInterceptor {
    private RegistryLauncher registryLauncher;
    
    public ZookeeperInstanceEventInterceptor() {
        EventControllerFactory.getController(ServiceInstanceEvent.getEventName(), EventControllerType.ASYNC).register(this);
        EventControllerFactory.getController(ReferenceInstanceEvent.getEventName(), EventControllerType.ASYNC).register(this);
    }
    
    public void start(String address, ProtocolType protocolType) throws Exception {
        registryLauncher = new ZookeeperRegistryLauncher();
        registryLauncher.start(address, protocolType);
    }
    
    public void stop() throws Exception {
        registryLauncher.stop();
    }
    
    public RegistryExecutor getRegistryExecutor() {
        return registryLauncher.getRegistryExecutor();
    }
    
    public void addServiceInstanceWatcher(String interfaze, ApplicationEntity applicationEntity) throws Exception {
        getRegistryExecutor().addServiceInstanceWatcher(interfaze, applicationEntity);
    }
    
    public void addReferenceInstanceWatcher(String interfaze, ApplicationEntity applicationEntity) throws Exception {
        getRegistryExecutor().addReferenceInstanceWatcher(interfaze, applicationEntity);
    }

    // 监听Service上下线
    @Subscribe
    public void listen(ServiceInstanceEvent event) {
        onEvent(event);
    }
    
    // 监听Reference上下线
    @Subscribe
    public void listen(ReferenceInstanceEvent event) {
        onEvent(event);
    }
    
    protected abstract void onEvent(ServiceInstanceEvent event);
    
    protected abstract void onEvent(ReferenceInstanceEvent event);
}