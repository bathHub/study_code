package com.zhiyou100.bigdata13.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//测试ArrayList和LinkedList的插入速度
public class TestListSpeed {
	public static void main(String[] args) {
		//创建一个ArrayList
		List<String> list = new ArrayList<String>();
		//创建一个LinkedList
		List<String> list1 = new LinkedList<String>();
		//获取当前的系统时间
		long begin = System.currentTimeMillis();
		for(int i = 0;i<1000000;i++){
			list.add(0,"String"+1);
		}
		System.out.println("array："+(System.currentTimeMillis()-begin));
		//重新获取当前系统时间
		begin = System.currentTimeMillis();
		for(int i = 0;i<1000000;i++){
			list1.add(0,"String"+1);
		}
		System.out.println("linked："+(System.currentTimeMillis()-begin));

		
		
		
		
	}
}
