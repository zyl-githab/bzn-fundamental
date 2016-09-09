package com.bzn.fundmental.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bzn.fundamental.redis.service.RedisOperations;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
public class RedisOperationTest {

	@Autowired
	private RedisOperations redisOperations;

	@Test
	public void testSet() {
		redisOperations.set("ttt", "331");;
	}

	@Test
	public void testGet() {
		System.out.println("00" + redisOperations.get("ttt"));
	}
}
