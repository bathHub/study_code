package com.zhiyou.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhiyou.entity.User;
import com.zhiyou.mapper.UserMapper;
import com.zhiyou.service.UserService;

@Component
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;

	public void insertUser(User user) throws Exception {
		user.setPwd(DigestUtils.md5Hex(user.getPwd()));
		userMapper.insertUser(user);
	}

	

	

}
