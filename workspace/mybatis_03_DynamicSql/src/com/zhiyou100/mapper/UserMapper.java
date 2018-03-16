package com.zhiyou100.mapper;

import java.util.List;
import java.util.Map;

import com.zhiyou100.entity.User;

public interface UserMapper {
	List<User> selectByName(Map<String, Object> map) throws Exception;
	List<User> selectByName2(Map<String, Object> map) throws Exception;
	List<User> selectByName3(Map<String, Object> map) throws Exception;
	
	void update1(User user) throws Exception;
	void delete1(User user)throws Exception;
}
