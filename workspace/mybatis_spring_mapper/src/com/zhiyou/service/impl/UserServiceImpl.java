package com.zhiyou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhiyou.entity.User;
import com.zhiyou.mapper.UserMapper;
import com.zhiyou.service.UserService;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
	
	
	
	@Override
	public List<User> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return userMapper.selectAll();
	}

}
