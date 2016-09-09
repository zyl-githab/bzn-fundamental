package com.bzn.fundamental.rpc.thrift.zookeeper;

/**
 * 发布服务地址及端口到服务注册中心，这里是zookeeper服务器
 * 
 * @author：fengli
 * @since：2016年8月11日 下午12:42:18
 * @version:
 */
public interface ThriftServerAddressRegister {
	/**
	 * 发布服务接口
	 * 
	 * @param service 服务接口名称，一个产品中不能重复
	 * @param version 服务接口的版本号，默认1.0.0
	 * @param address 服务发布的地址和端口
	 */
	void register(String service, String version, String address);
}
