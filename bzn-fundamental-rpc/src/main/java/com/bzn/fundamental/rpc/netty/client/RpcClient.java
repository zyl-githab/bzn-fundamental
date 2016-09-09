package com.bzn.fundamental.rpc.netty.client;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.bzn.fundamental.rpc.netty.client.proxy.IAsyncObjectProxy;
import com.bzn.fundamental.rpc.netty.client.proxy.ObjectProxy;
import com.bzn.fundamental.rpc.netty.registry.ServiceDiscovery;

/**
 * RPC Client
 * 
 * @author：fengli
 * @since：2016年8月22日 下午1:30:47
 * @version:
 */
public class RpcClient {

	private String serverAddress;
	private ServiceDiscovery serviceDiscovery;
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

	public RpcClient(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public RpcClient(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> interfaceClass) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, new ObjectProxy<T>(interfaceClass));
	}

	public <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
		return new ObjectProxy<T>(interfaceClass);
	}

	public static void submit(Runnable task) {
		threadPoolExecutor.submit(task);
	}

	public void stop() {
		threadPoolExecutor.shutdown();
		serviceDiscovery.stop();
		ConnectManage.getInstance().stop();
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public ServiceDiscovery getServiceDiscovery() {
		return serviceDiscovery;
	}

	public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}
}
