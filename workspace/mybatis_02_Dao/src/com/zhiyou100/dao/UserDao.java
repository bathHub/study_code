package com.zhiyou100.dao;

import java.util.List;

import com.zhiyou100.entity.User;

public interface UserDao {
	//²éÑ¯ËùÓĞ
	List<User> selectAll() throws Exception;
}
