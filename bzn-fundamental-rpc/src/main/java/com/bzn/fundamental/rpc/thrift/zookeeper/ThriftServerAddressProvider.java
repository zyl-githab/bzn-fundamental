package com.bzn.fundamental.rpc.thrift.zookeeper;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * thrift server-service地址提供者,以便构建客户端连接池
 * 
 * @author：fengli
 * @since：2016年8月11日 下午12:41:54
 * @version:
 */
public interface ThriftServerAddressProvider extends Closeable {

	// 获取服务名称
	String getService();

	/**
	 * 获取所有服务端地址
	 * 
	 * @return
	 */
	List<InetSocketAddress> findServerAddressList();

	/**
	 * 选取一个合适的address,可以随机获取等' 内部可以使用合适的算法.
	 * 
	 * @return
	 */
	InetSocketAddress selector();
}
