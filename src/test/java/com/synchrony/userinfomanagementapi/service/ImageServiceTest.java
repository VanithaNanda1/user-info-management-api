package com.synchrony.userinfomanagementapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.synchrony.userinfomanagement.entity.Image;
import com.synchrony.userinfomanagement.entity.ImageData;
import com.synchrony.userinfomanagement.exception.UserImageException;
import com.synchrony.userinfomanagement.service.ImageService;

class ImageServiceTest {

	@Mock
	private RestTemplate imgUrRestTemplate;

	private ImageService imageService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		imageService = new ImageService();
		ReflectionTestUtils.setField(imageService, "imgUrRestTemplate", imgUrRestTemplate);
		ReflectionTestUtils.setField(imageService, "imgUrHostUrl", "http://localhost:8080");
	}

	@Test
	void testUploadImageToImageUr() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn("test".getBytes());

		ImageData imageData = new ImageData();
		Image image = new Image();
		image.setImageUrl("http://localhost:8080/test");
		imageData.setImage(image);

		ResponseEntity<ImageData> responseEntity = new ResponseEntity<>(imageData, HttpStatus.OK);
		when(imgUrRestTemplate.postForEntity(any(URI.class), any(String.class), any(Class.class)))
				.thenReturn(responseEntity);

		Image result = imageService.uploadImageToImageUr(file);

		assertNotNull(result);
		assertEquals("http://localhost:8080/test", result.getImageUrl());
	}

	@Test
	void testUploadImageToImageUrRestClientException() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn("test".getBytes());

		when(imgUrRestTemplate.postForEntity(any(URI.class), any(String.class), any(Class.class)))
				.thenThrow(new RestClientException("Error uploading image to ImgUr"));

		try {
			imageService.uploadImageToImageUr(file);
		} catch (UserImageException e) {
			assertEquals("Error uploading image to ImgUr", e.getMessage());
		}
	}

	@Test
	void testUploadImageToImageUrIOException() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenThrow(new IOException("Error reading image file"));

		try {
			imageService.uploadImageToImageUr(file);
		} catch (UserImageException e) {
			assertEquals("Error uploading image to ImgUr", e.getMessage());
		}
	}

	@Test
	void testDeleteImageFromImageUr() {
		doNothing().when(imgUrRestTemplate).delete(any(String.class));

		imageService.deleteImageFromImageUr("test");

	}


	@Test
	void testDeleteImageFromImageUrRestClientException() {
		doThrow(new RestClientException("Error deleting image from ImgUr")).when(imgUrRestTemplate).delete(any(String.class));

		try {
			imageService.deleteImageFromImageUr("test");
		} catch (UserImageException e) {
			assertEquals("Error deleting image from ImgUr", e.getMessage());
		}
	}


}