package com.bzn.fundamental.rpc.thrift;

/**
 * 自定义异常
 * 
 * @author：fengli
 * @since：2016年8月11日 下午12:40:34
 * @version:
 */
public class ThriftException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ThriftException() {
		super();
	}

	public ThriftException(String msg) {
		super(msg);
	}

	public ThriftException(Throwable e) {
		super(e);
	}

	public ThriftException(String msg, Throwable e) {
		super(msg, e);
	}
}
