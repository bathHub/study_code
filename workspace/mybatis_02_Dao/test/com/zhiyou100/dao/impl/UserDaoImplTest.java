package com.zhiyou100.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.zhiyou100.entity.User;

public class UserDaoImplTest {

	@Test
	public void testSelectAll() throws Exception {
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		List<User> users=userDaoImpl.selectAll();
		for (User user : users) {
			System.out.println(user);
		}
	}

}
