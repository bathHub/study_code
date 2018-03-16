package com.zhiyou100.bigdata13.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 需求：
 * 创建一个List，创建一个User类，在list中放入User
 * User有：姓名，ID，年龄。
 * 并且将这个List按照ID进行排序。
 * @author root
 *
 */
public class TestListSortComparator {
	public static void main(String[] args) {
		List<User1> list = new ArrayList<User1>();
		//创建一个for循环，往List中放入数据
		for(int i = 0;i<10;i++){
			//ID 唯一，年龄17——30，姓名无所谓
			//[0,1)[10001,20000)
			int id =(int)(Math.random()*9+10001);
			User1 user = new User1(id, (int)(Math.random()*14+17), "String"+i);
			while(list.contains(user)){
				id =(int)(Math.random()*9999+10001);
				user = new User1(id, (int)(Math.random()*14+17), "String"+i);
			}
			list.add(user);
		}
		for(User1 u:list){
			System.out.println(u);
		}
		System.out.println("------------");
		Collections.sort(list,new Comparator<User1>() {

			@Override
			public int compare(User1 o1, User1 o2) {
				return -o1.getID()+o2.getID();
			}
		});
		for(User1 u:list){
			System.out.println(u);
		}
	}
	

}

class User1{
	private int ID;
	private int age;
	private String name;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User1(int iD, int age, String name) {
		super();
		ID = iD;
		this.age = age;
		this.name = name;
	}
	public User1(){
	}
	@Override
	public String toString() {
		return "User1 [ID=" + ID + ", age=" + age + ", name=" + name + "]";
	}
	//重写User的equals方法
	@Override
	public boolean equals(Object obj) {
		//首先判断obj是不是User类型的
		if(!(obj instanceof User1))
			return false;
		User1 u = (User1) obj;
		return this.ID == u.getID()?true:false;
		
	}
}
