package com.zhiyou.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhiyou.dao.UserDao;
import com.zhiyou.entity.User;
@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired
	 private SqlSessionTemplate template;

	@Override
	public List<User> selectAll() throws Exception {
		             
		return template.selectList("selectAll");
	}

	public SqlSessionTemplate getTemplate() {
		return template;
	}

	public void setTemplate(SqlSessionTemplate template) {
		this.template = template;
	}

	
}
