package com.answerdigital.irn.dto;

public class Patient extends ResponseDTO {
	
	private Boolean active;
	private Name[] name;
	private Telecom[] telecom;
	private String gender;
	private String birthDate;
	private Address[] address;
	private MaritalStatus maritalStatus;
	
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
    public Name[] getName() {
    	return name;
	}
	public void setName(Name[] name) {
		this.name = name;
	}
	
	public Telecom[] getTelecom() {
		return telecom;
	}
	public void setTelecom(Telecom[] telecom) {
		this.telecom = telecom;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public Address[] getAddress() {
		return address;
	}
	public void setAddress(Address[] address) {
		this.address = address;
	}
	
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

}
