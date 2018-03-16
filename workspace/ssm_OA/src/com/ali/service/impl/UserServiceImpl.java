package com.ali.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ali.entity.User;
import com.ali.mapper.UserMapper;
import com.ali.service.UserService;
@Component
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
	@Override
	public void insertUser(User user) throws Exception {
		    userMapper.insertUser(user);
		    
	}

}
