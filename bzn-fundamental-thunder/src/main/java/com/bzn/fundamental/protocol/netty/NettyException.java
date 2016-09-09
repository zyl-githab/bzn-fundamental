package com.bzn.fundamental.protocol.netty;

/**
 * <p>Title: Nepxion Thunder</p>
 * <p>Description: Nepxion Thunder For Distribution</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @email 1394997@qq.com
 * @version 1.0
 */

public class NettyException extends RuntimeException {
    private static final long serialVersionUID = 851864048447611118L;

    public NettyException() {
        super();
    }

    public NettyException(String message) {
        super(message);
    }

    public NettyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NettyException(Throwable cause) {
        super(cause);
    }
}