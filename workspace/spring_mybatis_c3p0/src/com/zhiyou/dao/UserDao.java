package com.zhiyou.dao;

import java.util.List;

import com.zhiyou.entity.User;

public interface UserDao {
	List<User> selectAll()throws Exception;
}
