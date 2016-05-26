package com.cognitivescale.rest.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.cognitivescale.rest.dao.HibernateDAO;
import com.cognitivescale.rest.pojo.User;

public class CacheUtil {

	private HashMap<String, User> userNameCache = new HashMap<String, User>();
	private HashMap<Integer, User> userIdCache = new HashMap<Integer, User>();

	private static CacheUtil instance;

	static {
		instance = new CacheUtil();
	}
	
	public CacheUtil() {
		loadAllUsers();
	}

	private void loadAllUsers() {
		List<?> list = HibernateDAO.getList(User.class, null);
		for(Object obj : list) {
			User user = (User) obj;
			updateCache(user);
		}
	}
	
	public void reloadAllUsers() {
		userNameCache.clear();
		userIdCache.clear();
		loadAllUsers();
	}

	public static CacheUtil instance() {
		return instance;
	}

	public User getUser(int userId) {
		if(userIdCache.containsKey(userId)) {
			return userIdCache.get(userId);
		} else {
			List<?> list = HibernateDAO.getList(User.class, Restrictions.eq("USER_ID", userId));
			if(list.size() > 0) {
				User user = (User) list.get(0);
				updateCache(user);
				return user;
			}
		}
		return null;
	}
	
	public User save(User user) {
		try {
			user = (User) HibernateDAO.save(user);
			updateCache(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User saveOrUpdate(User user) {
		try {
			user = (User) HibernateDAO.saveOrUpdate(user);
			updateCache(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private void updateCache(User user) {
		userIdCache.put(user.getUserId(), user);
		userNameCache.put(user.getUserName(), user);		
	}

	public User getUser(String userName) {
		return userNameCache.get(userName);
	}
	
	public Collection<User> listUsers() {
		return userIdCache.values();
	}

	public boolean exists(String userName) {
		if(userNameCache.containsKey(userName)) {
			return true;
		}
		return false;
	}

	public boolean exists(long userId) {
		if(userIdCache.containsKey(userId)) {
			return true;
		}
		return false;
	}
}
