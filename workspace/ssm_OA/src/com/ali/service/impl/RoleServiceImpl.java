package com.ali.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ali.entity.Role;
import com.ali.mapper.RoleMapper;
import com.ali.service.RoleService;
@Component
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
	@Override
	public void insertRole(Role role) throws Exception {
		   roleMapper.insertRole(role);
		    
	}
	@Override
	public List<Role> selectAllRoles() {
		
		return roleMapper.selectAllRoles();
	}
	@Override
	public void roleDeleteById(Integer id) {
		roleMapper.roleDeleteById(id);
		
	}
	public void addRole(Role role) {
		roleMapper.addRole(role);
		
	}
	@Override
	public Role selectRoleById(Integer id) {
		return roleMapper.selectRoleById(id);
		
	}
	
	public void updateRole(Role role) {
		roleMapper.updateRole(role);
		
	}
	
}
