package com.an.server;


public interface IUserService {
	public User login(String account, String password);
	public int update(String account,String password);
	public boolean insert(User user);
}
