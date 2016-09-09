package com.bzn.fundamental.common.property;

import com.bzn.fundamental.common.entity.ApplicationEntity;

// 从外部接口获取配置文件
public interface ThunderPropertiesExecutor {
    
    // 获取Property文本配置信息
    String retrieveProperty(ApplicationEntity applicationEntity) throws Exception;
    
    // 持久化Property文本配置信息
    void persistProperty(String property, ApplicationEntity applicationEntity) throws Exception;
}