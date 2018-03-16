package com.zhiyou100.entity;

import java.util.Date;

public class User {
	private Integer id;
	private String name;
	private Double sal;
	private Date birthday;
	private Integer[] ids = new Integer[5];
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
	public Double getSal() {
		return sal;
	}
	public void setSal(Double sal) {
		this.sal = sal;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sal=" + sal + ", birthday=" + birthday + "]";
	}
	
	
}
