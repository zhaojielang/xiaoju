package com.hao123.ssh.dao.user.impl;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.hao123.ssh.action.user.form.UserForm;
import com.hao123.ssh.dao.user.UserDao;
import com.hao123.ssh.entity.basic.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public int insert(final UserForm userForm) {
		User user = new User();
		user.setPassword(userForm.getPassword());
		user.setUsername(userForm.getUsername());
		List<?> list = this.getHibernateTemplate().find("from User where username=? and password=?",new Object[]{userForm.getUsername(),userForm.getPassword()});
		if (list.size()<=1) {
			Long id = (Long) this.getHibernateTemplate().save(user);
			return 0;
		}else {
			return 1;
		}
	}

}
