package com.bzn.fundamental.rpc.netty.server;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bzn.fundamental.rpc.netty.protocol.RpcDecoder;
import com.bzn.fundamental.rpc.netty.protocol.RpcEncoder;
import com.bzn.fundamental.rpc.netty.protocol.RpcRequest;
import com.bzn.fundamental.rpc.netty.protocol.RpcResponse;
import com.bzn.fundamental.rpc.netty.registry.ServiceRegistry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * RPC Server
 * 
 * @author huangyong,luxiaoxun
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

	private String serverAddress;

	private String serverPort;

	private ServiceRegistry serviceRegistry;

	private Map<String, Object> handlerMap = new HashMap<>();

	private static ThreadPoolExecutor threadPoolExecutor;

	public RpcServer(String serverPort) {
		this.serverAddress = getServerIp() + ":" + serverPort;

	}

	public RpcServer(String serverPort, ServiceRegistry serviceRegistry) {
		this.serverAddress = getServerIp() + ":" + serverPort;
		this.serviceRegistry = serviceRegistry;
	}

	public String getServerIp() {
		String serverIp = "127.0.0.1";
		// 一个主机有多个网络接口
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = netInterfaces.nextElement();
				// 每个网络接口,都会有多个"网络地址",比如一定会有lookback地址,会有siteLocal地址等.以及IPV4或者IPV6
				// .
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					if (address instanceof Inet6Address) {
						continue;
					}
					if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
						serverIp = address.getHostAddress();
						continue;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return serverIp;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
		if (MapUtils.isNotEmpty(serviceBeanMap)) {
			for (Object serviceBean : serviceBeanMap.values()) {
				String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class)
						.value().getName();
				handlerMap.put(interfaceName, serviceBean);
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel channel) throws Exception {
							channel.pipeline()
									.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
									.addLast(new RpcDecoder(RpcRequest.class))
									.addLast(new RpcEncoder(RpcResponse.class))
									.addLast(new RpcHandler(handlerMap));
						}
					}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			String[] array = serverAddress.split(":");
			String host = array[0];
			int port = Integer.parseInt(array[1]);

			ChannelFuture future = bootstrap.bind(host, port).sync();
			LOGGER.debug("Server started on port {}", port);

			if (serviceRegistry != null) {
				serviceRegistry.register(serverAddress);
			}

			future.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void submit(Runnable task) {
		if (threadPoolExecutor == null) {
			synchronized (RpcServer.class) {
				if (threadPoolExecutor == null) {
					threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS,
							new ArrayBlockingQueue<Runnable>(65536));
				}
			}
		}
		threadPoolExecutor.submit(task);
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

}
