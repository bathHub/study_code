package com.zhiyou100.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.zhiyou100.dao.UserDao;
import com.zhiyou100.entity.User;
import com.zhiyou100.util.MybatisUtil;

public class UserDaoImpl implements UserDao{
	private SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();

	@Override
	public List<User> selectAll() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		return session.selectList("user.selectAll");
	}

}
