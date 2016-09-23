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
		
		;
		System.out.println(redisOperations.getHashValue("open_policy_id_offset_hash"));
	}

	@Test
	public void testGet() {
		redisOperations.delete("open_policy_id_offset");
		redisOperations.delete("baidu_pack_policy_id_offset");
		redisOperations.delete("official_policy_id_offset");
		redisOperations.delete("open_policy_id_offset_set");
		redisOperations.delete("open_policy_id_offset_hash");
		redisOperations.delete("open_policy_id_offset_lock");
		redisOperations.delete("open_policy_id_old_offset");
	//	redisOperations.set("open_policy_id_offset", "2016-06-01 00:00:01");
		System.out.println("00");
	}
}
