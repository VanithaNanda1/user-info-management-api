package com.synchrony.userinfomanagement.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synchrony.userinfomanagement.entity.Image;
import com.synchrony.userinfomanagement.entity.UserProfile;
import com.synchrony.userinfomanagement.entity.UserRegistration;
import com.synchrony.userinfomanagement.exception.NoDetailsFoundException;
import com.synchrony.userinfomanagement.repository.UserProfileRepository;
import com.synchrony.userinfomanagement.repository.UserRegistrationRepository;


	/**
	 * This class provides services for managing user information such as registration, profile, and images.
	 */
	@Component
	public class UserManagementService {
	private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	/**
	 * Saves user registration details and returns the user ID.
	 * @param userRegistration The user registration details to be saved.
	 * @return The user ID of the saved user registration details.
	 */
	public Long saveUserDetails(UserRegistration userRegistration) {
		UserRegistration userRegistrationResponse = userRegistrationRepository.save(userRegistration);
		return userRegistrationResponse.getUserId();
	}
	
	/**
	 * Retrieves user registration details for the given user ID.
	 * @param userId The user ID for which to retrieve user registration details.
	 * @return The user registration details for the given user ID.
	 */
	public UserRegistration getUserRegistrationDetails(long userId) {
		UserRegistration userRegistration = userRegistrationRepository.findById(userId);
		return userRegistration;
	}

	/**
	 * Retrieves user profile for the given user ID.
	 * @param userId The user ID for which to retrieve user profile.
	 * @return The user profile for the given user ID.
	 */
	public UserProfile retreiveUserProfile(long userId) {

		try {
			return userProfileRepository.findById(userId).orElseThrow();
		} catch (NoSuchElementException ex) {
			logger.error("User profile not found for user ID: {}", userId);
			throw new NoDetailsFoundException("User profile not found for user ID: " + userId);
		}

		
	}

	/**
	 * Associates user profile with images for the given user ID.
	 * @param images The images to associate with the user profile.
	 * @param userId The user ID for which to associate images with the user profile.
	 * @return The updated user profile.
	 */
	public UserProfile associateUserProfileWithImages(List<Image> images, Long userId) {
		
		UserProfile userProfile= null;
		try {
			userProfile=  userProfileRepository.findById(userId).orElseThrow();
		} catch (NoSuchElementException ex) {
			logger.error("User profile not found for user ID: {}", userId);
			throw new NoDetailsFoundException("User profile not found for user ID: " + userId);
		}
		userProfile.setImages(images);
		
		userProfileRepository.save(userProfile);
		return userProfile;
	}

}
