package com.zhiyou100.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.zhiyou100.entity.User;
import com.zhiyou100.mapper.UserMapper;
import com.zhiyou100.util.MybatisUtil;

public class MyTest {
	private SqlSessionFactory factory = MybatisUtil.getSqlSessionFactory();
	
	@Test
	public void test1() throws Exception{
		SqlSession session=factory.openSession();
		UserMapper userMapper=session.getMapper(UserMapper.class);
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "С");
		map.put("sal", 100.0);
		
		List<User> users=userMapper.selectByName(map);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void test2() throws Exception{
		SqlSession session=factory.openSession();
		UserMapper userMapper=session.getMapper(UserMapper.class);
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "С");
		map.put("sal", 100.0);
		
		List<User> users=userMapper.selectByName2(map);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void test3() throws Exception{
		SqlSession session=factory.openSession();
		UserMapper userMapper=session.getMapper(UserMapper.class);
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "С");
		map.put("sal", 100.0);
		
		List<User> users=userMapper.selectByName3(map);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void test4() throws Exception{
		SqlSession session=factory.openSession();
		UserMapper userMapper=session.getMapper(UserMapper.class);
		
		User u = new User();
		u.setName("Сǿ");
		u.setSal(2000.0);
		userMapper.update1(u);
		session.commit();
		session.close();
	}
	@Test
	 public void test5()throws Exception{
		SqlSession session = factory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		  User user = new User();
		  Integer[] ids = {1,2};
		  user.setIds(ids);
		
		userMapper.delete1(user);
		session.commit();
		session.close();
		
		
		
	}
	
}
