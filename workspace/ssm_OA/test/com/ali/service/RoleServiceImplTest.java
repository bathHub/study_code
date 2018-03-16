package com.ali.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ali.entity.Role;
import com.ali.service.impl.RoleServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring_contect.xml")
public class RoleServiceImplTest {
	@Autowired
private RoleServiceImpl roleServiceImpl;
	@Test
	public void testUpdateRole() {
		Role role = new Role();
		role.setId(24);
		role.setName("总裁办公司");
		role.setDescription("能够管理一切实务");
		roleServiceImpl.updateRole(role);
	}

}
