package com.bzn.fundamental.common.delegate;

import com.bzn.fundamental.common.container.CacheContainer;
import com.bzn.fundamental.common.container.ExecutorContainer;
import com.bzn.fundamental.common.property.ThunderProperties;
import com.bzn.fundamental.common.util.ClassUtil;

public class ThunderDelegateImpl implements ThunderDelegate {
    protected ThunderProperties properties;
    protected CacheContainer cacheContainer;
    protected ExecutorContainer executorContainer;

    public ThunderDelegateImpl() {

    }
    
    @Override
    public ThunderProperties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(ThunderProperties properties) {
        this.properties = properties;
    }

    @Override
    public CacheContainer getCacheContainer() {
        return cacheContainer;
    }

    @Override
    public void setCacheContainer(CacheContainer cacheContainer) {
        this.cacheContainer = cacheContainer;
    }

    @Override
    public ExecutorContainer getExecutorContainer() {
        return executorContainer;
    }

    @Override
    public void setExecutorContainer(ExecutorContainer executorContainer) {
        this.executorContainer = executorContainer;
    }
    
    @Override
    public <T> T createDelegate(String delegateClassId) throws Exception {
        String delegateClassName = properties.get(delegateClassId);

        T delegateInstance = ClassUtil.createInstance(delegateClassName);
        
        ThunderDelegate delegate = (ThunderDelegate) delegateInstance;
        delegate.setProperties(properties);
        delegate.setCacheContainer(cacheContainer);
        delegate.setExecutorContainer(executorContainer);
        
        return delegateInstance;
    }
}