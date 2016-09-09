package com.bzn.fundmental.mongodb.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bzn.fundamental.mongodb.service.MongoService;
import com.bzn.fundmental.mongodb.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
public class MongoServiceTest {

	@Autowired
	private MongoService<Person> mongoService;

	@Test
	public void testInsert() {

		Person person = new Person();
		person.setId(5222L);
		person.setName("张三");

		// 单个保存
		mongoService.insert(person, "person1");

		List<Person> entitys = new ArrayList<>();
		entitys.add(person);

		person = new Person();
		person.setId(5223L);
		person.setName("李四");
		entitys.add(person);

		// 批量保存
		//mongoService.batchInsert(entitys, "person2");
	}

	@Test
	public void testRemove() {
		Query query = new Query(Criteria.where("_id").is(5223));
		System.out.println(mongoService.remove(query, "person2"));
	}

	@Test
	public void testUpdate() {
		Query query = new Query(Criteria.where("_id").is(5222L));

		Person person = new Person();
		person.setName("lisi1");
		person.setAge(55);
		person.setBirthday(System.currentTimeMillis());
		System.out.println(mongoService.update(query, person, "person2"));
	}
	
	@Test
	public void testFindOne() {
		Query query = new Query(Criteria.where("name").is("张三"));
		Person data = mongoService.findOne(query, Person.class, "person1");

		System.out.println(data);
	}

	@Test
	public void testFindAll() {
		Query query = new Query(Criteria.where("_id").is(58));
		List<Person> data = mongoService.findAll(query, Person.class, "person1");

		System.out.println(data);
	}
}
