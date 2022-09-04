package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserDao;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;

public class UserService {
	
	private UserDao userdao;
	
	public UserService() {
		userdao = new UserDao();
	}

	public int memberJoin(JoinReqDto dto) {
		int result = userdao.insert(dto);
		return result;
	}
	
	public User login(LoginReqDto dto) {
		return 	userdao.findByUsernameAndPassword(dto);
	}
	
	public int usernameDoubleCheck(String username) {
		int result = userdao.findByUsername(username);
		return result;
	}

}
