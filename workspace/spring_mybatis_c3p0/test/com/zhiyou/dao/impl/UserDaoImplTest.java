package com.zhiyou.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhiyou.entity.User;


public class UserDaoImplTest {
	private ClassPathXmlApplicationContext cxt;

	@Before
	public void setUp() throws Exception {
		 cxt  =new ClassPathXmlApplicationContext("spring.xml");
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testSelectAll() throws Exception {
		UserDaoImpl userDaoImpl = cxt.getBean("userDaoImpl", UserDaoImpl.class);
		   List<User> list = userDaoImpl.selectAll();
		   
		   for (User user : list) {
			System.out.println(user.getName());
		}
	}

}
