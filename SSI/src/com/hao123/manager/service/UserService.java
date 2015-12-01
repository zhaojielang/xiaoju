package com.hao123.manager.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseModel;

public interface UserService {
	
	@Cacheable(value="user_ehcache", key="#form.id")
	List<User> queryUserList(UserForm form);
	@CacheEvict(value="user_ehcache",key="#form.id")
	ResponseModel insertUser(UserForm form);

}
