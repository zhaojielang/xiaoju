package com.hao123.manager.controller.user;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseDatagridMode;
import com.hao123.manager.entity.help.ResponseModel;
import com.hao123.manager.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@ResponseBody
	@RequestMapping("/test")
	public List<User> test(UserForm form){
		System.out.println("-------测试------");
		List<User> list = this.userService.queryUserList(form);
		return list;
	}
	
	/**
	 * 插入新用户
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertUser")
	public ResponseModel insertUser(UserForm form){
		return this.userService.insertUser(form);
	}
	
	/**
	 * 查询用户信息列表
	 * 
	 * @param form 查询条件
	 * @return 封装成datagrid需要的数据格式
	 */
	@ResponseBody
	@RequestMapping("/queryUserPaging")
	public ResponseDatagridMode queryUserPaging(UserForm form){
		return this.userService.queryUserPaging(form);
	}
	
	
	
}
