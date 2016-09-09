package com.bzn.fundamental.mongodb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bzn.fundamental.mongodb.service.MongoService;
import com.bzn.fundamental.mongodb.utils.UpdateBeanUtils;
import com.mongodb.WriteResult;

/**
 * MongoDB服务类
 * 
 * @author：fengli
 * @since：2016年8月10日 下午1:32:47
 * @version:
 */
@Service
public class MongoServiceImpl<T> implements MongoService<T> {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}

	@Override
	public void insert(T objectToSave, String collectionName) {
		mongoTemplate.insert(objectToSave, collectionName);
	}

	@Override
	public void batchInsert(List<T> entitys, String collectionName) {
		mongoTemplate.insert(entitys, collectionName);
	}

	@Override
	public void dropCollection(String collectionName) {
		mongoTemplate.dropCollection(collectionName);
	}

	@Override
	public WriteResult remove(Query query, String collectionName) {
		return mongoTemplate.remove(query, collectionName);
	}

	@Override
	public WriteResult update(Query query, T t, String collectionName) {

		Update update = new Update();
		UpdateBeanUtils.ObjToUpdate(t, update);
		return mongoTemplate.updateMulti(query, update, collectionName);
	}

    @Override
    public WriteResult update(Query query, Update update, String collectionName) {
        return mongoTemplate.upsert(query,update,collectionName);
    }

    @Override
	public T findOne(Query query, Class<T> entityClass, String collectionName) {
		return mongoTemplate.findOne(query, entityClass, collectionName);
	}

	@Override
	public List<T> findAll(Query query, Class<T> entityClass,
			String collectionName) {
		return mongoTemplate.find(query, entityClass, collectionName);
	}

    @Override
    public AggregationResults<T> findByAggregation(Aggregation aggregation, String collectionName, Class<T> entityClass) {
        return mongoTemplate.aggregate(aggregation,collectionName, entityClass);
    }

    @Override
    public long getCount(Query query, String collectionName) {
        return mongoTemplate.count(query,collectionName);
    }

}
