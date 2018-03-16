package com.ali.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ali.entity.Role;
import com.ali.service.impl.RoleServiceImpl;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
   private RoleServiceImpl roleServiceImpl;	
	
	/*增加*/
	@RequestMapping("/addRoleUI")
	public ModelAndView addRoleUI(String num){
		  ModelAndView mav = new ModelAndView();
		  //相当于在attribute中放入参数
		  mav.addObject("num",num);
		  //相当于设置文件跳转的路径
		  mav.setViewName("/WEB-INF/jsp/role/addRole.jsp");
	     return mav;
	}
	@RequestMapping("/addRole")
	public String addRole(String name,String description){
		Role role = new Role();
		
		role.setName(name);
		role.setDescription(description);
		roleServiceImpl.addRole(role);
		 return "selectAllRoles";
	}	
	/*查找*/
	@RequestMapping("/selectAllRoles")
	public ModelAndView selectAllRoles(){		
		ModelAndView mav = new ModelAndView();
		 List<Role> roles = roleServiceImpl.selectAllRoles();
		 mav.addObject("roleList", roles);
		 mav.setViewName("/WEB-INF/jsp/role/roleList.jsp");		 
		return mav;
	}
	
	
	/*删除*/
	@RequestMapping("roleDeleteById")
	public String roleDeleteById(Integer id){
		roleServiceImpl.roleDeleteById(id);
		return "selectAllRoles";
	}
	
	
	/*修改*/
	@RequestMapping("/updateRoleUI")
	public ModelAndView updateRoleUI(Integer id,String num){
		 ModelAndView mav = new ModelAndView();
		 
		 mav.addObject("num", num);
		
		 Role role = roleServiceImpl.selectRoleById(id);
		
		mav.addObject("role", role);
		 
		 mav.setViewName("/WEB-INF/jsp/role/addRole.jsp");
		return mav;
	}
	@RequestMapping("/updateRole")
	public String updateRole(String name,String description,Integer id){
		Role role = new Role();
		role.setId(id);
		role.setDescription(description);
		role.setName(name);
		roleServiceImpl.updateRole(role);
		return "selectAllRoles";
	}
	
    
}
