package com.hao123.manager.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;

import com.hao123.manager.biz.UserBiz;
import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseModel;
import com.hao123.manager.service.UserService;

public class UserServiceImpl implements UserService{
	private Log log = LogFactory.getLog(UserServiceImpl.class);
	
	private UserBiz userBiz;
	
	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
	
	public List<User> queryUserList(UserForm form) {
		log.debug("-------------debug------------");
		return this.userBiz.queryUserList(form);
	}
	public ResponseModel insertUser(UserForm form) {
		return this.userBiz.insertUser(form);
	}

}
