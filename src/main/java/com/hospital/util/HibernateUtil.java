package com.hospital.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionfactory;
		static {
			try {
				sessionfactory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable ex) {
				System.out.println("Session factory creation failed : "+ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		
		public static SessionFactory getSessionFactory() {
			return sessionfactory;
		}
}
