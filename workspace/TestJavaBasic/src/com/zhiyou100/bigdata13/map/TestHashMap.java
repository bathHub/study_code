package com.zhiyou100.bigdata13.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 1. Map中的key是如何保证唯一的
 * 2. Map中的key如何排序
 * @author root
 *
 */
public class TestHashMap {
	public static void main(String[] args) {
		//创建一个map，将User作为key，将User的分数作为value
		Map<User, Integer> map = new HashMap<User, Integer>();
		User user = new User(1001, "郭伟", 100);
		map.put(user, user.getScore());
		user = new User(1002, "李光耀", 99);
		map.put(user, user.getScore());
		System.out.println(map);
		//System.out.println("abc".hashCode());
		Set<User> set = map.keySet();
		Iterator<User> it = set.iterator();
		while(it.hasNext()){
			User u = it.next();
			System.out.println(u+"---"+map.get(u));
		}
		System.out.println(set);
		
		
		
		
		
		
		
/*		System.out.println(map);
		user = new User(1002, "王耀鹏", 59);
		map.put(user, user.getScore());
		System.out.println(map);
		user.setId(1003);
		user.setName("宋太平");
		user.setScore(0);
		System.out.println(map);
		int a = map.put(user, user.getScore());
		System.out.println(map);
		System.out.println(a);*/
	}

}
class User{
	private int id;
	private String name;
	private int score;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", score=" + score + "]";
	}
	public User(int id, String name, int score) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
	}
	public User(){
	}
	@Override
	public int hashCode() {
		return id;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof User)) return false;
		User u = (User) obj;
		return id==u.getId()?true:false;
	}
	
}