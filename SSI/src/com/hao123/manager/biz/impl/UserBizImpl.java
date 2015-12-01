package com.hao123.manager.biz.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.hao123.manager.biz.UserBiz;
import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.dao.custom.UserDao;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseModel;

public class UserBizImpl implements UserBiz{
	
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<User> queryUserList(UserForm form) {
		return this.userDao.queryUserList(form);
	}

	public ResponseModel insertUser(UserForm form) {
		int insertId = this.userDao.insertUser(form);
		return null;
	}
	
	

}
