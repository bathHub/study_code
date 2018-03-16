package com.ali.service.impl;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ali.entity.Role;
import com.ali.service.RoleService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring_contect.xml")
public class RoleServiceImplTest {
	
	@Autowired 
	private RoleServiceImpl roleServiceImpl;
    
	
	
	@Test
	public void test() throws Exception {
		
	Role role = new Role(1, "向华强","很能干");
	  roleServiceImpl.insertRole(role);
	  Role role1 = new Role(1, "向佐","很厉害");
	  roleServiceImpl.insertRole(role1);
	  Role role2 = new Role(1, "向强","厉害");
	  roleServiceImpl.insertRole(role2);
	}
	@Test 
	public void test1()throws Exception{
		List<Role> list = roleServiceImpl.selectAllRoles();
		for (Role role : list) {
			System.out.println(role.getName());
		}
	}

}
