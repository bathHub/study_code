package com.zhiyou.mapper;

import java.util.List;

import com.zhiyou.entity.User;

public interface UserMapper {
   List<User> selectAll()throws Exception;
}
