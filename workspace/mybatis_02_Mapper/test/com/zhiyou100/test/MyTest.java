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
		//1、获取会话
		SqlSession session=factory.openSession();
		//2、获取mapper对象
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3、查询
		User u=userMapper.selectById(1);
		System.out.println(u);
		session.close();
	}
	
	@Test
	public void test2() throws Exception{
		//1、获取会话
		SqlSession session=factory.openSession();
		//2、获取mapper对象
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3、查询
		List<User> users=userMapper.selectAll();
		for (User user : users) {
			System.out.println(user);
		}
		session.close();
	}
	
	@Test
	public void test3() throws Exception{
		//1、获取会话
		SqlSession session=factory.openSession();
		//2、获取mapper对象
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3、参数封装到对象中
		User u = new User();
		u.setName("小强");
		u.setBirthday(new Date());
		u.setSal(3000.0);
		//调用方法
		userMapper.insert1(u);
		
		session.commit();
		session.close();
	}
	
	@Test
	public void test4() throws Exception{
		//1、获取会话
		SqlSession session=factory.openSession();
		//2、获取mapper对象
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3、参数封装到对象中
		Map<String, Object> map = new HashMap<>();
		map.put("id", 1);
		map.put("name", "阮小七");
		map.put("sal", 200);
		
		
		//调用方法
		userMapper.update1(map);
		
		session.commit();
		session.close();
	}
	
	@Test
	public void test5() throws Exception{
		//1、获取会话
		SqlSession session=factory.openSession();
		//2、获取mapper对象
		UserMapper userMapper=session.getMapper(UserMapper.class);
		
		//调用方法
		userMapper.delete1(3);
		
		session.commit();
		session.close();
	}
}
