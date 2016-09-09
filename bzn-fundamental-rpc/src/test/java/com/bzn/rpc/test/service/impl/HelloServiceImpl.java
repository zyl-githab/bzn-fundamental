package com.bzn.rpc.test.service.impl;

import com.bzn.fundamental.rpc.netty.server.RpcService;
import com.bzn.rpc.test.model.Person;
import com.bzn.rpc.test.service.HelloService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public String hello(Person person) {
        return "test:Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
