package com.bzn.fundamental.rpc.netty.client.proxy;

import com.bzn.fundamental.rpc.netty.client.RPCFuture;

/**
 * Created by luxiaoxun on 2016/3/16.
 */
public interface IAsyncObjectProxy {
    public RPCFuture call(String funcName, Object... args);
}