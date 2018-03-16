package com.zhiyou100.bigdata13.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TestTreeMap {
	public static void main(String[] args) {
		Map<Student, String> map = new TreeMap<Student, String>(new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return 0;
			}
		});
		Student s1 = new Student(10001, "zhangsan1");
		Student s2 = new Student(10002, "zhangsan2");
		Student s3 = new Student(10003, "zhangsan3");
		Student s4 = new Student(10003, "zhangsan4");
		map.put(s1, s1.getName());
		map.put(s2, s2.getName());
		map.put(s3, s3.getName());
		map.put(s4, s4.getName());
		System.out.println(map);
		/*Map<Student, String> map = new TreeMap<Student, String>();
		Student s1 = new Student(10001, "zhangsan1");
		Student s2 = new Student(10002, "zhangsan2");
		Student s3 = new Student(10003, "zhangsan3");
		Student s4 = new Student(10003, "zhangsan4");
		map.put(s1, s1.getName());
		map.put(s2, s2.getName());
		map.put(s3, s3.getName());
		map.put(s4, s4.getName());
		System.out.println(map);*/
	}
	

}
class Student{
	private int id;
	private String name;
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
	public Student(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Student() {
		super();
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	/*@Override
	public int compareTo(Student o) {
		return this.id - o.getId();
	}*/
	
}