package com.hao123.manager.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hao123.manager.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
	@ResponseBody
	@RequestMapping("/test")
	public User test(){
		System.out.println("-------测试------");
		return new User(1L, "小马", "123456");
	}
}
