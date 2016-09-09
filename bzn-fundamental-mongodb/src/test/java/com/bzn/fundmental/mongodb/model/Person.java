package com.bzn.fundmental.mongodb.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person implements Serializable{

	/**    
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
	 */    
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private int age;

	private Long birthday;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}

