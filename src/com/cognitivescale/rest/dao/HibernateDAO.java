package com.cognitivescale.rest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateDAO {

	private static SessionFactory sessionFactory;

	static {
		createSessionFactory();
	}

	private static void createSessionFactory() {
		initialiseSessionFactory(new Configuration());
	}

	private static void initialiseSessionFactory(Configuration configuration) {
		configuration.configure();
		sessionFactory = configuration.buildSessionFactory(new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry());

	}

	public static SessionFactory getSessionFactory() {
		if(sessionFactory.isClosed()) {
			createSessionFactory();
		}
		return sessionFactory;
	}

	public static  Object save(Object object) throws Exception {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
			}
			throw e;
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return object;
	}

	public static  void delete(Object object) throws Exception {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
			}
			throw e;
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	public static Object update(Object object) throws Exception {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
			}
			throw e;
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return object;
	}

	public static Object saveOrUpdate(Object object) throws Exception {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(object);
			session.getTransaction().commit();
		} catch(Exception e) {
			if(session != null) {
				session.getTransaction().rollback();
			}
			throw e;
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return object;
	}

	public static List<?> getList(List<Criterion> criterions, Class<?> type) {
		Session session = null;
		List<?> list = null;
		try {
			session = getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(type);
			if(criterions != null) {
				for(Criterion criterion : criterions) {
					criteria.add(criterion);
				}
			}
			list = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return list;
	}

	public static List<?> getList(Class<?> type, Criterion criterion) {
		Session session = null;
		List<?> list = null;
		try {
			session = getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(type);
			if(criterion != null) {
				criteria.add(criterion);
			}
			list = criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return list;
	}
}
