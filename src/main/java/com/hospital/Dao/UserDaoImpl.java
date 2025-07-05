package com.hospital.Dao;

import org.hibernate.Session;

import com.hospital.entity.User;
import com.hospital.util.HibernateUtil;

public class UserDaoImpl implements UserDao {
	@Override
	public boolean validateUser(String username,String password) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()){
			String hql = "FROM User WHERE username = :username AND password = :password";
			User user = session.createQuery(hql,User.class)
					.setParameter("username", username)
					.setParameter("password", password)
					.uniqueResult();
			return user != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
