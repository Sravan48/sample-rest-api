package com.cognitivescale.rest.auth;

import com.cognitivescale.rest.util.CacheUtil;

public class LoginAuthorizer {

	public static boolean authorize(String userName, String password) {
		if(CacheUtil.instance().exists(userName)) {
			if(CacheUtil.instance().getUser(userName).getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
}
