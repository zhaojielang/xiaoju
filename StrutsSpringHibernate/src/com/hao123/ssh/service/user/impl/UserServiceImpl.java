package com.hao123.ssh.service.user.impl;

import com.hao123.ssh.action.user.form.UserForm;
import com.hao123.ssh.biz.user.UserBiz;
import com.hao123.ssh.entity.basic.User;
import com.hao123.ssh.entity.help.ResultModel;
import com.hao123.ssh.service.user.UserService;

public class UserServiceImpl implements UserService {

	private UserBiz userBiz;

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

	public UserBiz getUserBiz() {
		return userBiz;
	}

	public ResultModel<User> regist(UserForm userForm) {
		System.out.println("service");
		return this.userBiz.regist(userForm);
	}

}
