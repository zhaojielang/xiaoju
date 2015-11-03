package com.hao123.ssh.action.user;

import javax.annotation.Resource;

import com.hao123.ssh.action.user.form.UserForm;
import com.hao123.ssh.entity.basic.User;
import com.hao123.ssh.entity.help.ResultModel;
import com.hao123.ssh.service.user.UserService;

public class UserAction {
	
	private UserForm userForm;
	
	@Resource
	private UserService userService;

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public UserForm getUserForm() {
		return userForm;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public String regist() {
		ResultModel<User> resultModel = this.userService.regist(userForm );
		return "success";
		
	}

}
