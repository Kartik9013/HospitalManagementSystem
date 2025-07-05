package com.hospital.Dao;

import java.util.List;

import com.hospital.entity.Patient;

public interface PatientDao {
	boolean savePatient(Patient patient);
	Patient getPatient(int id);
	void updatePatient(Patient patient);
	void deletePatient(int id);
	List<Patient> getAllPatients();
	Patient getPatientById(int id);

}
