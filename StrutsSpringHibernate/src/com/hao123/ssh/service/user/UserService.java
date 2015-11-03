package com.hao123.ssh.service.user;

import com.hao123.ssh.action.user.form.UserForm;
import com.hao123.ssh.entity.basic.User;
import com.hao123.ssh.entity.help.ResultModel;

public interface UserService {

	ResultModel<User> regist(UserForm userForm);

}
