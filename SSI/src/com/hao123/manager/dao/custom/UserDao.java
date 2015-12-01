package com.hao123.manager.dao.custom;

import java.util.List;

import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseModel;

public interface UserDao {

	List<User> queryUserList(UserForm form);

	int insertUser(UserForm form);

}
