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
		//1����ȡ�Ự
		SqlSession session=factory.openSession();
		//2����ȡmapper����
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3����ѯ
		User u=userMapper.selectById(1);
		System.out.println(u);
		session.close();
	}
	
	@Test
	public void test2() throws Exception{
		//1����ȡ�Ự
		SqlSession session=factory.openSession();
		//2����ȡmapper����
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3����ѯ
		List<User> users=userMapper.selectAll();
		for (User user : users) {
			System.out.println(user);
		}
		session.close();
	}
	
	@Test
	public void test3() throws Exception{
		//1����ȡ�Ự
		SqlSession session=factory.openSession();
		//2����ȡmapper����
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3��������װ��������
		User u = new User();
		u.setName("Сǿ");
		u.setBirthday(new Date());
		u.setSal(3000.0);
		//���÷���
		userMapper.insert1(u);
		
		session.commit();
		session.close();
	}
	
	@Test
	public void test4() throws Exception{
		//1����ȡ�Ự
		SqlSession session=factory.openSession();
		//2����ȡmapper����
		UserMapper userMapper=session.getMapper(UserMapper.class);
		//3��������װ��������
		Map<String, Object> map = new HashMap<>();
		map.put("id", 1);
		map.put("name", "��С��");
		map.put("sal", 200);
		
		
		//���÷���
		userMapper.update1(map);
		
		session.commit();
		session.close();
	}
	
	@Test
	public void test5() throws Exception{
		//1����ȡ�Ự
		SqlSession session=factory.openSession();
		//2����ȡmapper����
		UserMapper userMapper=session.getMapper(UserMapper.class);
		
		//���÷���
		userMapper.delete1(3);
		
		session.commit();
		session.close();
	}
}
