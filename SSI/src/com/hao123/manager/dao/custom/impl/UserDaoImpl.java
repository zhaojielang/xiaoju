package com.hao123.manager.dao.custom.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.dao.custom.UserDao;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseModel;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

	public List<User> queryUserList(UserForm form) {
		return this.getSqlSession().selectList("custom_user_mapper.queryUserList", form);
	}

	public int insertUser(UserForm form) {
		return this.getSqlSession().insert("custom_user_mapper.insertUser",form);
	}

}
