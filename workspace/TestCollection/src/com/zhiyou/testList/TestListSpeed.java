package com.zhiyou.testList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestListSpeed {
       public static void main(String[] args) {
		//创建一个arraylist
    	   List<String> list = new ArrayList<String>();
    	   //创建一个linkedlist
    	   List<String> list2 = new LinkedList<String>();
    	   //获取当前的系统时间
    	   long begin = System.currentTimeMillis();
    	   for( int i=0;i<100000;i++){
    		   list.add(0,"String"+1);
    	   }
    	   System.out.println("arraylist:"+(System.currentTimeMillis()-begin));
    	   //重新获取当前时间
    	   begin = System.currentTimeMillis();
    	   for( int i=0;i<100000;i++){
    		   list2.add(0,"String"+1);
    	   }
    	   System.out.println("linked:"+(System.currentTimeMillis()-begin));
	}
}
    