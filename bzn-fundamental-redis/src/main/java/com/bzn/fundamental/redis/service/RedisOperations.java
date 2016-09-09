package com.bzn.fundamental.redis.service;

/**
 * redis服务接口
 * 
 * @author：fengli
 * @since：2015年8月5日 下午11:10:41
 * @version:
 */
public interface RedisOperations {

	/**
	 * 将value存入redis中
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value);

	/**
	 * 将value存入redis中
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void set(String key, String value, Long seconds);

	/**
	 * 设置不重复的key
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean setNx(String key, String value);

	/**
	 * 设置hash值
	 * 
	 * @param key
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public void setHashValue(String key, String fieldName, String value);

	/**
	 * 将key的值自增
	 * 
	 * @param key
	 * @return
	 */
	public Long incr(String key);

	/**
	 * 将key的值自增delta
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public Long incr(String key, Long delta);

	/**
	 * 根据key删除值
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 根据key查询值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * 获取Hash中key值
	 * 
	 * @param key
	 * @return
	 */
	public Object getHashValue(String key, String fieldName);

}
