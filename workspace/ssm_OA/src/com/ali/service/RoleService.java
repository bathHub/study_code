package com.ali.service;

import java.util.List;

import com.ali.entity.Role;

public interface RoleService {
       void insertRole(Role role) throws Exception;   
	   List<Role> selectAllRoles()throws Exception;
	   void roleDeleteById(Integer id) throws Exception;
	   void addRole(Role role)throws Exception;
	   Role selectRoleById(Integer id)throws Exception;
	   void updateRole(Role role) throws Exception;
}
