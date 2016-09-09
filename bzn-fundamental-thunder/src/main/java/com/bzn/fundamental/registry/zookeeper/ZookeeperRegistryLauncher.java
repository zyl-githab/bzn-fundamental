package com.bzn.fundamental.registry.zookeeper;

import com.bzn.fundamental.common.entity.ProtocolEntity;
import com.bzn.fundamental.common.entity.ProtocolType;
import com.bzn.fundamental.common.entity.RegistryEntity;
import com.bzn.fundamental.common.property.ThunderProperties;
import com.bzn.fundamental.common.property.ThunderPropertiesManager;
import com.bzn.fundamental.registry.RegistryExecutor;
import com.bzn.fundamental.registry.RegistryInitializer;
import com.bzn.fundamental.registry.RegistryLauncher;

public class ZookeeperRegistryLauncher implements RegistryLauncher {
    private RegistryInitializer registryInitializer;
    private RegistryExecutor registryExecutor;

    @Override
    public void start(String address, ProtocolType protocolType) throws Exception {
        // 读取配置文件
        ThunderProperties properties = ThunderPropertiesManager.getProperties();

        RegistryEntity registryEntity = new RegistryEntity();
        registryEntity.setAddress(address);

        ProtocolEntity protocolEntity = new ProtocolEntity();
        protocolEntity.setType(protocolType);

        // 启动Zookeeper连接
        registryInitializer = new ZookeeperRegistryInitializer();
        registryInitializer.start(registryEntity, properties);

        registryExecutor = new ZookeeperRegistryExecutor();
        registryExecutor.setRegistryInitializer(registryInitializer);
        registryExecutor.setProperties(properties);
        registryExecutor.setProtocolEntity(protocolEntity);
    }

    @Override    
    public void stop() throws Exception {
        // 停止Zookeeper连接
        registryInitializer.stop();
    }

    @Override    
    public RegistryExecutor getRegistryExecutor() {
        return registryExecutor;
    }
}