package com.hao123.ssh.biz.user.impl;

import org.springframework.transaction.annotation.Transactional;

import com.hao123.ssh.action.user.form.UserForm;
import com.hao123.ssh.biz.user.UserBiz;
import com.hao123.ssh.dao.user.UserDao;
import com.hao123.ssh.entity.basic.User;
import com.hao123.ssh.entity.help.ResultModel;

public class UserBizeImpl implements UserBiz {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	@Transactional
	public ResultModel<User> regist(UserForm userForm) {
		int insertNum = this.userDao.insert(userForm);
		if (insertNum==1) {
			
		}
		return null;
	}

}
