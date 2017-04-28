package com.xdev.rcdemo.hashing.dal;

import com.xdev.dal.JPADAO;
import com.xdev.rcdemo.hashing.entities.User;

public class UserDAO extends JPADAO<User, String> {

	public UserDAO() {
		super(User.class);
	}

}
