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
		redisOperations.set("sync_data_switch","1",-1L);
		redisOperations.delete("sync_data_offset");
		redisOperations.delete("global_policy_id_offset");
	}

	@Test
	public void testGet() {
		System.out.println(redisOperations.get("sync_data_offset"));
	/*	redisOperations.delete("sync_data_offset");
	 	redisOperations.delete("open_policy_id_offset");
		redisOperations.delete("baidu_pack_policy_id_offset");
		redisOperations.delete("official_policy_id_offset");
		redisOperations.delete("open_policy_id_offset_set");
		redisOperations.delete("open_policy_id_offset_hash");
		redisOperations.delete("open_policy_id_offset_lock");
		redisOperations.delete("open_policy_id_old_offset");*/
	//	redisOperations.set("open_policy_id_offset", "2016-06-01 00:00:01");
		System.out.println("00");
	}
}
