package com.cognitivescale.rest.controller;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognitivescale.rest.auth.LoginAuthorizer;
import com.cognitivescale.rest.pojo.User;
import com.cognitivescale.rest.util.CacheUtil;

@RestController
@RequestMapping("/")
public class RestAPIController {

	public RestAPIController() {
	}
	
	@RequestMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		return CacheUtil.instance().save(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public int login (@RequestHeader(value = "username") String userName, @RequestHeader(value = "password") String password) {
		if(LoginAuthorizer.authorize(userName, password)) {
			return 200;
		} else {
			return 401;
		}
	}

	@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public User profile (@RequestHeader(value = "username") String userName, @RequestHeader(value = "password") String password) throws Exception {
		if(LoginAuthorizer.authorize(userName, password)) {
			return CacheUtil.instance().getUser(userName);
		} else {
			throw new Exception("401 : Unauthorised");
		}
	}
	
	@RequestMapping(value = "/listallprofiles", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Collection<User> listAllProfile () throws Exception {
			return CacheUtil.instance().listUsers();
	}
}
