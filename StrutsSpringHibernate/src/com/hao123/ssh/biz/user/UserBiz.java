package com.hao123.ssh.biz.user;

import com.hao123.ssh.action.user.form.UserForm;
import com.hao123.ssh.entity.basic.User;
import com.hao123.ssh.entity.help.ResultModel;

public interface UserBiz {

	ResultModel<User> regist(UserForm userForm);

}
