package com.bzn.rpc.test.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bzn.fundamental.rpc.netty.client.RpcClient;
import com.bzn.rpc.test.model.Person;
import com.bzn.rpc.test.service.HelloService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context-netty-client.xml")
public class HelloServiceTest {

	@Autowired
	private RpcClient rpcClient;

	@Test
	public void helloTest1() {
		HelloService helloService = rpcClient.create(HelloService.class);
		String result = helloService.hello("World");

		System.out.println("result:" + result);
		Assert.assertEquals("Hello! World", result);
	}

	@Test
	public void helloTest2() {
		HelloService helloService = rpcClient.create(HelloService.class);
		Person person = new Person("Yong", "Huang");
		String result = helloService.hello(person);

		System.out.println("result:" + result);
		Assert.assertEquals("Hello! Yong Huang", result);
	}

}
