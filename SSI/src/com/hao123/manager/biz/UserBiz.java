package com.hao123.manager.biz;

import java.util.List;

import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseModel;

/**
 * @author 罗乔
 * @time 2015-12-16 10:17:45
 *
 */
public interface UserBiz {

	List<User> queryUserList(UserForm form);
	
	ResponseModel insertUser(UserForm form);

}
