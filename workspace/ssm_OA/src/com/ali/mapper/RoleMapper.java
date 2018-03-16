package com.ali.mapper;

import java.util.List;

import com.ali.entity.Role;

public interface RoleMapper {
         void insertRole(Role role) throws Exception;

		List<Role> selectAllRoles();

		void roleDeleteById(Integer id);

		void addRole(Role role);

		Role selectRoleById(Integer id);

		void updateRole(Role role);     
	
}
