package com.hospital.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(name = "room_number")
	private String roomNumber;
	
	private LocalDate dob;
	private String gender;
	private String address;
	private String phone;
	
	@Column(name = "disease")
	private String disease;

	
	@Column(name = "is_treated")
	private boolean isTreated;

	public Patient() {
		super();
	}

	public Patient(String name, String roomNumber, LocalDate dob, String gender, String address, String phone,
			boolean isTreated,String disease) {
		super();
		this.name = name;
		this.roomNumber = roomNumber;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.isTreated = isTreated;
		this.disease = disease;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public boolean isTreated() {
		return isTreated;
	}

	public void setTreated(boolean isTreated) {
		this.isTreated = isTreated;
	}
	
	 public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	@Override
	    public String toString() {
	        return name + " (Room " + roomNumber + ")";
	    }
	
}
