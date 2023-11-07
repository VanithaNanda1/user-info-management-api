package com.synchrony.userinfomanagement.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_information_id")
	@JsonIgnore
	private long userInformationId;

	@JsonProperty("first_name")
	@Column(name = "first_name")
	private String firstName;

	@JsonProperty("last_name")
	@Column(name = "last_name")
	private String lastName;

	@JsonProperty("email")
	@Column(name = "email")
	private String email;

	@JsonProperty("phone_number")
	@Column(name = "phone_number")
	private String phoneNumber;

	@JsonProperty("address")
	@Column(name = "address")
	private String address;

	@JsonProperty("city")
	@Column(name = "city")
	private String city;

	@JsonProperty("state")
	@Column(name = "state")
	private String state;

	@JsonProperty("zip_code")
	@Column(name = "zip_code")
	private String zipCode;

	@JsonProperty("country")
	@Column(name = "country")
	private String country;

	public long getUserInformationId() {
		return userInformationId;
	}

	public void setUserInformationId(long userInformationId) {
		this.userInformationId = userInformationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}