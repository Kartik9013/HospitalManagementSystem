package com.hospital.Dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hospital.entity.Patient;
import com.hospital.util.HibernateUtil;

public class PatientDaoImpl implements PatientDao{
	@Override
	public boolean savePatient(Patient patient) {
	    Transaction tx = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();
	        session.save(patient);
	        tx.commit();
	        return true;
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	        return false;
	    }
	}

	
	@Override
	public Patient getPatient(int id) {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			return session.get(Patient.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void updatePatient(Patient patient) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			tx = session.beginTransaction();
			session.update(patient);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
		}
	}
	
	@Override
	public void deletePatient(int id) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			tx = session.beginTransaction();
			Patient patient = session.get(Patient.class, id);
			if(patient != null) {
				session.delete(patient);
			}
			tx.commit();
		} catch (Exception e) {
			if(tx!=null) tx.rollback();
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Patient> getAllPatients(){
		try(Session session = HibernateUtil.getSessionFactory().openSession()){
			return session.createQuery("from Patient",Patient.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Patient getPatientById(int id) {
	    Transaction tx = null;
	    Patient patient = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        tx = session.beginTransaction();
	        patient = session.get(Patient.class, id);
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
	    }
	    return patient;
	}

}
