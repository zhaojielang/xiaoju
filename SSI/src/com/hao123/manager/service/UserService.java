package com.hao123.manager.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hao123.manager.controller.user.form.UserForm;
import com.hao123.manager.entity.custom.User;
import com.hao123.manager.entity.help.ResponseDatagridMode;
import com.hao123.manager.entity.help.ResponseModel;

public interface UserService {
	
	/**
	 * @param form
	 * @return
	 */
	@Cacheable(value="user_ehcache", key="#form.id")
	List<User> queryUserList(UserForm form);
	
	/**
	 * @param form
	 * @return
	 */
	@CacheEvict(value="user_ehcache",key="#form.id")
	ResponseModel insertUser(UserForm form);
	
	/**
	 * @param form
	 * @return
	 */
	ResponseDatagridMode queryUserPaging(UserForm form);

}
