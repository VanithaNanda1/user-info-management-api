package com.synchrony.userinfomanagement.service;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.synchrony.userinfomanagement.entity.Image;
import com.synchrony.userinfomanagement.entity.ImageData;
import com.synchrony.userinfomanagement.exception.UserImageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides methods to upload and delete images to/from ImgUr using RestTemplate.
 */
@Service
public class ImageService {
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

	@Autowired
	private RestTemplate imgUrRestTemplate;

	@Value(value = "${imgUrHostUrl}")
	private String imgUrHostUrl;

	public Image uploadImageToImageUr(MultipartFile file) {
		ResponseEntity<ImageData> imageResponse = null;
		try {
			String encodedStringMultipart = Base64.getEncoder().encodeToString(file.getBytes());
			imageResponse = imgUrRestTemplate.postForEntity(URI.create(imgUrHostUrl), encodedStringMultipart,
					ImageData.class);
		} catch (RestClientException e) {
			logger.error("Error uploading image to ImgUr", e);
			throw new UserImageException("Error uploading image to ImgUr");

		} catch (IOException e) {
			logger.error("Error reading image file", e);
			throw new UserImageException("Error uploading image to ImgUr");
		}
		if (imageResponse != null && imageResponse.getBody() != null) {
			return imageResponse.getBody().getImage();
		} else {
			return null;
		}

	}

	public void deleteImageFromImageUr(String imageHash) {
		try{
			imgUrRestTemplate.delete(imgUrHostUrl + "/" + imageHash);
		}catch (RestClientException e) {
			logger.error("Error deleting image from ImgUr", e);
			throw new UserImageException("Error deleting image from ImgUr");

		}
		
	}
}
