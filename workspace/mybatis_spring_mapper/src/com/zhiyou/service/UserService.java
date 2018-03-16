package com.zhiyou.service;

import java.util.List;

import com.zhiyou.entity.User;

public interface UserService {
      List<User> selectAll()throws Exception;
}
