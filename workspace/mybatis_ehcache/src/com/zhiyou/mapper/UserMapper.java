package com.zhiyou.mapper;

import com.zhiyou.entity.User;

public interface UserMapper {
	 User selectById(Integer id)throws Exception;
}
