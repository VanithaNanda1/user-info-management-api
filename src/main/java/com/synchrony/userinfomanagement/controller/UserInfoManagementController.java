package com.synchrony.userinfomanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synchrony.userinfomanagement.entity.Image;
import com.synchrony.userinfomanagement.entity.UserProfile;
import com.synchrony.userinfomanagement.entity.UserRegistration;
import com.synchrony.userinfomanagement.exception.InvalidUserException;
import com.synchrony.userinfomanagement.service.ImageService;
import com.synchrony.userinfomanagement.service.UserManagementService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This class represents the controller for managing user information.
 * It contains methods for registering a user, uploading and deleting images, 
 * viewing user information with images, and associating user with images.
 */
@RestController
public class UserInfoManagementController {

	private Logger logger = LoggerFactory.getLogger(UserInfoManagementController.class);
	
	@Autowired
	private  UserManagementService userManagementService;
	
    @Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private ImageService imageService;
		
	
	/**
	 * This method registers a user with the given user registration details.
	 * @param userRegistration The user registration details to be registered.
	 * @param bindingResult The binding result of the user registration details.
	 * @return A ResponseEntity containing the ID of the registered user.
	 * @throws InvalidUserException If the user registration details are invalid.
	 */
	@PostMapping({"/registerUser"})
	public ResponseEntity<Long> registerUser(@Valid @RequestBody UserRegistration userRegistration, BindingResult bindingResult) throws InvalidUserException {
		if (bindingResult.hasErrors()) {
			throw new InvalidUserException("Invalid user registration details");
		}
		Long userId = userManagementService.saveUserDetails(userRegistration);
		return ResponseEntity.ok(userId);
	}
	
	/**
	 * This method uploads an image file for a user.
	 * @param file The image file to be uploaded.
	 * @return The uploaded image.
	 * @throws InvalidUserException If the user is not authenticated.
	 */
	@PostMapping(value={"/uploadImage"},consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Image uploadImage(@RequestPart("imageFile") MultipartFile file) throws InvalidUserException {
		//TODO : authenticate user before uploading image
		//authenticateUser(userId);
		Image image = imageService.uploadImageToImageUr(file);
		return image;
	}
	

	private void authenticateUser(long userId) throws InvalidUserException {
		UserRegistration userRegistration =  userManagementService.getUserRegistrationDetails(userId);
		
		Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRegistration.getUserName(), userRegistration.getPassword()));
		
		
		if(!authentication.isAuthenticated()) {
			throw new InvalidUserException("Invalid user credentials");
		}
	}
	
	
	/**
	 * This method deletes an image with the given image hash.
	 * @param imageHash The hash of the image to be deleted.
	 */
	@DeleteMapping({"/deleteImage/{imageHash}"})
	public void deleteImage(@PathVariable("imageHash") @NotBlank String imageHash) {
		imageService.deleteImageFromImageUr(imageHash);
	}

	/**
	 * This method retrieves the user profile with images for the given user ID.
	 * @param userId The ID of the user whose profile is to be retrieved.
	 * @return The user profile with images.
	 */
	@GetMapping({"/viewUserInfoWithImages/{userId}"})
	public UserProfile viewUserInfoWithImages(@PathVariable("userId") @NotBlank Long userId) {
		UserProfile userProfile = userManagementService.retreiveUserProfile(userId);
		return userProfile;
	}
	
	/**
	 * This method associates a list of images with the given user ID.
	 * @param images The list of images to be associated with the user.
	 * @param userId The ID of the user to associate the images with.
	 * @return The user profile with the associated images.
	 */
	@PostMapping({"/associateUserWithImages/{userId}"})
	public UserProfile associateUserWithImages(@RequestBody List<Image> images,@PathVariable("userId") @NotBlank  Long userId) {
		UserProfile userProfile = userManagementService.associateUserProfileWithImages(images,userId);
		return userProfile;
	}

	/**
	 * This method handles InvalidUserException and returns a BAD_REQUEST status.
	 */
	@ExceptionHandler(InvalidUserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handleInvalidUserException() {
		logger.error("Invalid user exception occurred");
	}

}
	
