package com.zhiyou100.dao;

import java.util.List;

import com.zhiyou100.entity.User;

public interface UserDao {
	//��ѯ����
	List<User> selectAll() throws Exception;
}
