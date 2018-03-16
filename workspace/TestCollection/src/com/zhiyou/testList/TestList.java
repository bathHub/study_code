package com.zhiyou.testList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestList{
	public static void main(String[] args) {
		List<User> list = new ArrayList<User>();
		//  User user = new User(111, "zhang", 12);
		//System.out.println(user);
		//创建一个for循环，往list中放入数据
		for (int i = 0; i < 10; i++) {
			//ID唯一，年龄17-30，姓名不要求
			int id = (int) (Math.random()*9+10001);
			User user = new User(id, "String"+i,(int) (Math.random()*14+17) );
			while(list.contains(user)){
				id = (int) (Math.random()*9999+10001);
				user = new User(id, "String"+i,(int) (Math.random()*14+17) );
			}
			list.add(user);
		}
		for (User user : list) {
			System.out.println(user);
		}
		System.out.println("----------------------------------------");
		Collections.sort(list);
		for (User user : list) {
			System.out.println(user);
		}
	}	  
}

class User implements Comparable<User>{
	private int id;
	private String name;
	private int age;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public User(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	//重写user的equals方法
	@Override
	public boolean equals(Object obj){
		//首先判断obj是不是user类型的
		if (!(obj instanceof User)) {
			return false;
		}
		User u = (User) obj;
		return this.id==u.getId()?true:false;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id-o.getId();
	}
}
