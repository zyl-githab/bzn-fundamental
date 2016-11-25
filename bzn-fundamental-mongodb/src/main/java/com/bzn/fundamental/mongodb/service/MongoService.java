package com.bzn.fundamental.mongodb.service;

import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Mongo操作接口
 * 
 * @author：fengli
 * @since：2016年8月10日 上午10:05:27
 * @version:
 */
public interface MongoService<T> {

	/**
	 * 创建集合
	 * 
	 * @param collectionName
	 */
	public void createCollection(String collectionName);

	/**
	 * 新增数据
	 * 
	 * @param entity
	 * @param collectionName
	 */
	public void insert(T entity, String collectionName);

	/**
	 * 批量新增数据
	 * 
	 * @param entitys
	 * @param collectionName
	 */
	public void batchInsert(List<T> entitys, String collectionName);

	/**
	 * 删除集合
	 * 
	 * @param collectionName
	 */
	public void dropCollection(String collectionName);

	/**
	 * 删除数据
	 * 
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public WriteResult remove(Query query, String collectionName);

	/**
	 * 删除数据
	 * 
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public List<T> removeAll(Query query, Class<T> clazz);

	/**
	 * 修改数据
	 * 
	 * @param query
	 * @param entity
	 * @param collectionName
	 * @return
	 */
	public WriteResult update(Query query, T entity, String collectionName);

	/**
	 * 修改数据
	 * 
	 * @param query
	 * @param update
	 * @param collectionName
	 * @return
	 */
	public WriteResult update(Query query, Update update, String collectionName);

	/**
	 * 根据条件查找单条数据
	 * 
	 * @param query
	 * @param entityClass
	 * @param collectionName
	 * @return
	 */
	public T findOne(Query query, Class<T> entityClass, String collectionName);

	/**
	 * 根据条件查询多条数据F
	 * 
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public List<T> findAll(Query query, Class<T> entityClass, String collectionName);

	/**
	 * 聚合数据
	 * 
	 * @param aggregation
	 * @param collectionName
	 * @param entityClass
	 * @return
	 */
	public AggregationResults<T> findByAggregation(Aggregation aggregation, String collectionName,
			Class<T> entityClass);

	/**
	 * 查找数据条数
	 * 
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public long getCount(Query query, String collectionName);

}
