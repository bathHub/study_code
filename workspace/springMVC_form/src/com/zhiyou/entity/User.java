package com.zhiyou.entity;

public class User {
	 private Integer id;
	 private String name;
	 private Integer age;
	 private String[] hobbies;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String[] getHobbies() {
		return hobbies;
	}
	public void setHobbies(String[] hobbies) {
		this.hobbies = hobbies;
	}
	public User(Integer id, String name, Integer age, String[] hobbies) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobbies = hobbies;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
