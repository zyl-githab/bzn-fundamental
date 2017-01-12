package com.bzn.fundmental.mongodb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bzn.fundamental.mongodb.service.MongoService;
import com.bzn.fundmental.mongodb.model.Insured;
import com.bzn.fundmental.mongodb.model.Policy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
public class MongoTest {

	@Autowired
	private MongoService<Policy> mongoService;

	@Test
	public void test() {

		Policy p = new Policy();
		p.setPolicyId("22535");
		p.setPolicyNo("4352342");
		p.setUserId("2525253");
		List<Insured> insureds = new ArrayList<>();

		Insured insured = new Insured();

		insured.setCertNo("fsdfsfdsf");
		insureds.add(insured);
		p.setInsureds(insureds);

		List<Policy> entitys = new ArrayList<>();
		entitys.add(p);
		mongoService.batchInsert(entitys, "policy");
	}
	
	/**
	 * 删除
	 */
	@Test
	public void test1() {

		Policy p = new Policy();
		p.setPolicyId("22535");
		p.setPolicyNo("4352342");
		p.setUserId("2525253");
		List<Insured> insureds = new ArrayList<>();

		Insured insured = new Insured();
		
		insured.setCertNo("fsdfsfdsf");
		insureds.add(insured);
		p.setInsureds(insureds);

		List<Policy> entitys = new ArrayList<>();
		entitys.add(p);
		mongoService.removeSubArrayElement("insureds", insured.getCertNo(), "policyId", p.getPolicyId(), Policy.class);
	}
	
	/**
	 * 添加
	 */
	@Test
	public void test2() {

		Policy p = new Policy();
		p.setPolicyId("22535");
		p.setPolicyNo("4352342");
		p.setUserId("2525253");
		Insured[] insureds = new Insured[1];

		Insured insured = new Insured();
		
		insured.setCertNo("fsdfsfdsf");
		insureds[0]=insured;
		//p.setInsureds(insureds);

		List<Map<?, ?>> m = new ArrayList<>();
		m.add(new BeanMap(insured));
		List<Policy> entitys = new ArrayList<>();
		entitys.add(p);
		mongoService.addSubArrayElements("insureds", insureds, "policyId", p.getPolicyId(), Policy.class);
	}
}
