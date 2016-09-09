package com.bzn.rpc.test.service;

import com.bzn.rpc.test.model.Person;

public interface HelloService {

    String hello(String name);

    String hello(Person person);
}
