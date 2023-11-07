
package com.synchrony.userinfomanagementapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.synchrony.userinfomanagement.entity.Image;
import com.synchrony.userinfomanagement.entity.UserInformation;
import com.synchrony.userinfomanagement.entity.UserProfile;
import com.synchrony.userinfomanagement.entity.UserRegistration;
import com.synchrony.userinfomanagement.exception.NoDetailsFoundException;
import com.synchrony.userinfomanagement.repository.UserInformationRepository;
import com.synchrony.userinfomanagement.repository.UserProfileRepository;
import com.synchrony.userinfomanagement.repository.UserRegistrationRepository;
import com.synchrony.userinfomanagement.service.ImageService;
import com.synchrony.userinfomanagement.service.UserManagementService;

import jakarta.persistence.EntityNotFoundException;

class UserManagementServiceTest {

	private UserManagementService userManagementService;

	@Mock
	private UserRegistrationRepository userRegistrationRepository;

	@Mock
	private UserInformationRepository userInformationRepository;

	@Mock
	private UserProfileRepository userProfileRepository;

	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userManagementService = new UserManagementService();
		ReflectionTestUtils.setField(userManagementService, "userRegistrationRepository", userRegistrationRepository);
		ReflectionTestUtils.setField(userManagementService, "userInformationRepository", userInformationRepository);
		ReflectionTestUtils.setField(userManagementService, "userProfileRepository", userProfileRepository);
		
	}

	@Test
	void testSaveUserDetails() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setUserId(1L);
		userRegistration.setUserName("John");
		//userRegistration.setLastName("Doe");
		//userRegistration.setEmail("johndoe@example.com");
		userRegistration.setPassword("password");

		Mockito.when(userRegistrationRepository.save(userRegistration)).thenReturn(userRegistration);

		Long userId = userManagementService.saveUserDetails(userRegistration);

		assertNotNull(userId);
		assertEquals(1L, userId);
	}

	@Test
	void testGetUserDetails() {
		UserInformation userInformation = new UserInformation();
		userInformation.setAddress("123 Main St");
		userInformation.setCity("Anytown");
		userInformation.setState("CA");
		userInformation.setZipCode("12345");

		Mockito.when(userInformationRepository.getById(1L)).thenReturn(userInformation);

		UserInformation result = userManagementService.getUserDetails(1L);

		assertNotNull(result);
		assertEquals(userInformation, result);
	}

	@Test
	void testGetUserRegistrationDetails() {
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setUserId(1L);
		userRegistration.setUserName("John");
		userRegistration.setPassword("password");

		Mockito.when(userRegistrationRepository.findById(1L)).thenReturn(userRegistration);

		UserRegistration result = userManagementService.getUserRegistrationDetails(1L);

		assertNotNull(result);
		assertEquals(userRegistration, result);
	}

	@Test
	void testRetreiveUserProfile() {
		UserProfile userProfile = getUserInformation();

		Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile));

		UserProfile result = userManagementService.retreiveUserProfile(1L);

		assertNotNull(result);
		assertEquals(userProfile, result);
	}

	private UserProfile getUserInformation() {
		UserProfile userProfile = new UserProfile();
		UserInformation userInformation = new UserInformation();
		userProfile.setUserId(1L);
		userInformation.setFirstName("John");
		userInformation.setLastName("Doe");
		userProfile.setUserInformation(userInformation);
		return userProfile;
	}

	@Test
	void testAssociateUserProfileWithImages() {
		UserProfile userProfile = getUserInformation();

		Mockito.when(userProfileRepository.save(userProfile)).thenReturn(userProfile);

		UserProfile result = userManagementService.associateUserProfileWithImages(userProfile);

		assertNotNull(result);
		assertEquals(userProfile, result);
	}

	@Test
	void testAssociateUserProfileWithImagesWithUserId() {
	UserProfile userProfile = getUserInformation();

		List<Image> images = new ArrayList<>();
		Image image1 = new Image();
		image1.setId("imageId");
		image1.setImageUrl("http://example.com/image1.jpg");
		Image image2 = new Image();
		image2.setId("imageId");
		image2.setImageUrl("http://example.com/image2.jpg");
		images.add(image1);
		images.add(image2);

		Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfile));
		Mockito.when(userProfileRepository.save(userProfile)).thenReturn(userProfile);

		UserProfile result = userManagementService.associateUserProfileWithImages(images, 1L);

		assertNotNull(result);
		assertEquals(userProfile, result);
		assertEquals(images, result.getImages());
	}

	@Test
	void testGetUserDetailsWithInvalidUserId() {
		Mockito.when(userInformationRepository.getById(1L)).thenThrow(new EntityNotFoundException());

		assertThrows(EntityNotFoundException.class, () -> {
			userManagementService.getUserDetails(1L);
		});
	}


	@Test
	void testRetreiveUserProfileWithInvalidUserId() {
		Mockito.when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(NoDetailsFoundException.class, () -> {
			userManagementService.retreiveUserProfile(1L);
		});
	}

}