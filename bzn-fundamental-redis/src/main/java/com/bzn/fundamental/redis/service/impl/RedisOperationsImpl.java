package com.bzn.fundamental.redis.service.impl;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
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

	private final static Logger LOGGER = LoggerFactory.getLogger(RedisOperationsImpl.class);

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

	@Override
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Set<String> getKeys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public Boolean tryLock(String key, Long timeout, TimeUnit unit) {

		Long nano = System.nanoTime();
		Long nanos = unit.toNanos(timeout);

		try {
			do {
				if (setNx(key, "1")) {
					return Boolean.TRUE;
				}
				if (timeout == 0L) {
					break;
				}
				Thread.sleep(5);

			} while ((System.nanoTime() - nano) < nanos);

		} catch (InterruptedException e) {
			LOGGER.info("redis get lock fail:" + e.getMessage());
		}
		return Boolean.FALSE;
	}

	@Override
	public void unLock(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void expire(String key, Long timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 原子操作，设置key的value，返回老的value
	 *
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	@Override
	public String getSet(final String key, final String value) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				byte[] result = redisConnection.getSet(redisTemplate.getStringSerializer().serialize(key),
						redisTemplate.getStringSerializer().serialize(value));
				if (result != null) {
					return new String(result);
				}
				return null;
			}
		});
	}

	/**
	 * redis 分布式锁
	 *
	 * @param key 键值
	 * @param timeOutSecs 超时时间（s）
	 * @param expireSecs 到期时间（s）
	 * @return
	 */
	@Override
	public synchronized Boolean tryLock(String key, Long timeOutSecs, Long expireSecs) throws InterruptedException{
		while (timeOutSecs > 0L) {
			long expires = System.currentTimeMillis() + expireSecs + 1;
			String expiresStr = String.valueOf(expires); // 锁到期时间
			if (setNx(key, expiresStr)) {
				return true;
			}

			String currentValueStr = get(key); // redis中存储的到期时间
			// 已经到期
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				String oldValueStr = getSet(key, expiresStr);
				if (Objects.equals(oldValueStr, currentValueStr)) {
					// 多个线程到了这里，只有一个线程的设置值和当前值相同
					return true;
				}
			}
			timeOutSecs -= 100;
			Thread.sleep(100);
		}
		return false;
	}
}
