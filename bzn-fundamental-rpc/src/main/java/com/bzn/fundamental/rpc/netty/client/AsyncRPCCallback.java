package com.bzn.fundamental.rpc.netty.client;

/**
 * Created by luxiaoxun on 2016-03-17.
 */
public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}
