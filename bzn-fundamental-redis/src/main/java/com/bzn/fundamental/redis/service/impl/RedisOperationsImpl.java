package com.bzn.fundamental.redis.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bzn.fundamental.redis.service.RedisOperations;

/**
 * redis服务接口实现类
 * 
 * @author：fengli
 * @since：2016年8月9日 下午1:06:47
 * @version:
 */
@Service("redisOperations")
public class RedisOperationsImpl implements RedisOperations {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/*
	 * 默认超期时间
	 */
	private static Long TIMEOUT_SECONDS = 60 * 60 * 5L;

	@Override
	public void set(String key, String value) {
		set(key, value, TIMEOUT_SECONDS);
	}

	@Override
	public void set(String key, String value, Long seconds) {
		if (seconds == -1L) {
			redisTemplate.boundValueOps(key).set(value);
		} else {
			redisTemplate.boundValueOps(key).set(value, seconds, TimeUnit.SECONDS);
		}
	}

	@Override
	public Boolean setNx(String key, String value) {
		return redisTemplate.boundValueOps(key).setIfAbsent(value);
	}

	@Override
	public Long setHset(String key, String... values) {
		return redisTemplate.boundSetOps(key).add(values);
	}

	@Override
	public void setHashValue(String key, String fieldName, String value) {
		redisTemplate.boundHashOps(key).put(fieldName, value);
	}

	@Override
	public Long incr(String key) {
		return incr(key, 1L);
	}

	@Override
	public Long incr(String key, Long delta) {
		return redisTemplate.boundValueOps(key).increment(delta);
	}

	@Override
	public Long hashIncr(String key, String fieldName) {
		return hashIncr(key, fieldName, 1L);
	}

	@Override
	public Long hashIncr(String key, String fieldName, Long delta) {
		return redisTemplate.boundHashOps(key).increment(fieldName, delta);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public String get(String key) {
		return redisTemplate.boundValueOps(key).get();
	}

	@Override
	public Object getHashValue(String key, String fieldName) {
		return redisTemplate.boundHashOps(key).get(fieldName);
	}

	@Override
	public Map<Object, Object> getHashValue(String key) {
		return redisTemplate.boundHashOps(key).entries();
	}

}
