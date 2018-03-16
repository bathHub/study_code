package com.zhiyou.testList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestListSpeed {
       public static void main(String[] args) {
		//����һ��arraylist
    	   List<String> list = new ArrayList<String>();
    	   //����һ��linkedlist
    	   List<String> list2 = new LinkedList<String>();
    	   //��ȡ��ǰ��ϵͳʱ��
    	   long begin = System.currentTimeMillis();
    	   for( int i=0;i<100000;i++){
    		   list.add(0,"String"+1);
    	   }
    	   System.out.println("arraylist:"+(System.currentTimeMillis()-begin));
    	   //���»�ȡ��ǰʱ��
    	   begin = System.currentTimeMillis();
    	   for( int i=0;i<100000;i++){
    		   list2.add(0,"String"+1);
    	   }
    	   System.out.println("linked:"+(System.currentTimeMillis()-begin));
	}
}
    