package com.wm.service;

import com.wm.dao.UserDao;
import com.wm.entity.User;

public class LoginService {

	UserDao userDao=new UserDao();
	
	public User LoginByUsernamePassword(String username,String password) throws Exception{
		User user=userDao.getUser(username, password);
		return user;
	}
}
