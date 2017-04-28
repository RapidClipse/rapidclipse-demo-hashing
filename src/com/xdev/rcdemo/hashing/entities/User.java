package com.xdev.rcdemo.hashing.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.xdev.dal.DAO;
import com.xdev.rcdemo.hashing.dal.UserDAO;
import com.xdev.security.authentication.CredentialsUsernamePassword;

@Entity
@DAO(daoClass = UserDAO.class)
@Table(name = "`USER`")
public class User implements CredentialsUsernamePassword {

	private byte[] password;
	private String username;

	public User() {
		super();
	}

	@Id
	@Column(name = "USERNAME", unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "`PASSWORD`", nullable = false)
	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Override
	public String username() {
		return this.getUsername();
	}

	@Override
	public byte[] password() {
		return this.getPassword();
	}

}
