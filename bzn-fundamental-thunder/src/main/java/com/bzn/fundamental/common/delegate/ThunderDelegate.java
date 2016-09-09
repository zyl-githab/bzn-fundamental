package com.bzn.fundamental.common.delegate;

import com.bzn.fundamental.common.container.CacheContainer;
import com.bzn.fundamental.common.container.ExecutorContainer;
import com.bzn.fundamental.common.property.ThunderProperties;

public interface ThunderDelegate {
    // 获取属性句柄容器
    ThunderProperties getProperties();
    
    void setProperties(ThunderProperties properties);
    
    // 获取缓存容器
    CacheContainer getCacheContainer();

    void setCacheContainer(CacheContainer cacheContainer);
    
    // 获取执行器句柄容器
    ExecutorContainer getExecutorContainer();
    
    void setExecutorContainer(ExecutorContainer executorContainer);
    
    // 反射创建Delegate类
    <T> T createDelegate(String delegateClassId) throws Exception;
}