package com.zhiyou100.mapper;

import java.util.List;
import java.util.Map;

import com.zhiyou100.entity.User;

public interface UserMapper {
	//²éÑ¯ËùÓÐ
	User selectById(Integer id) throws Exception;
	List<User> selectAll() throws Exception;
	void insert1(User user)throws Exception;
	void update1(Map<String, Object> map) throws Exception;
	void delete1(Integer id)throws Exception;
}
