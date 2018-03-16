package com.zhiyou.testList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestListSortComparator {
	

	
		public static void main(String[] args) {
			List<User1> list = new ArrayList<User1>();
			//  User user = new User(111, "zhang", 12);
			//System.out.println(user);
			//����һ��forѭ������list�з�������
			for (int i = 0; i < 10; i++) {
				//IDΨһ������17-30��������Ҫ��
				int id = (int) (Math.random()*9+10001);
				User1 user = new User1(id, "String"+i,(int) (Math.random()*14+17) );
				while(list.contains(user)){
					id = (int) (Math.random()*9999+10001);
					user = new User1(id, "String"+i,(int) (Math.random()*14+17) );
				}
				list.add(user);
			}
			for (User1 user : list) {
				System.out.println(user);
			}
			System.out.println("------------------------");
			Collections.sort(list,new Comparator<User1>() {
				@Override
				public int compare(User1 o1,User1 o2){
					return o1.getId()-o2.getId();
				}
			});
			
			
		}	  
	}

	class User1 {
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
		public User1(int id, String name, int age) {
			super();
			this.id = id;
			this.name = name;
			this.age = age;
		}
		public User1() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "User1 [id=" + id + ", name=" + name + ", age=" + age + "]";
		}
		//��дuser��equals����
		@Override
		public boolean equals(Object obj){
			//�����ж�obj�ǲ���user���͵�
			if (!(obj instanceof User1)) {
				return false;
			}
			User1 u = (User1) obj;
			return this.id==u.getId()?true:false;
		}

		
	}


