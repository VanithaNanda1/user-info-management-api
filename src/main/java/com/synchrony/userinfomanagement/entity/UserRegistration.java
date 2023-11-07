package com.synchrony.userinfomanagement.entity;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * This class represents the UserRegistration entity which contains the user's registration details.
 */
@Entity
@Table(name = "user_registration")
@Valid
public class UserRegistration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	@JsonIgnore
	private Long userId;

	@Column(name = "user_name")
	@NotBlank
	private String userName;

	@Column(name = "password")
	@NotBlank
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonProperty
	private UserProfile userProfile;


	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public UserRegistration() {
		super();
	}

	public UserRegistration(Long userId, String userName, String password, UserProfile userProfile) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.userProfile = userProfile;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
