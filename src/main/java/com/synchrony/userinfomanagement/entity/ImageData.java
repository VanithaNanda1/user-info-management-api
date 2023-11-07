package com.synchrony.userinfomanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageData {
	
	@JsonProperty("data")
	private Image imageData;

	public Image getImage() {
		return imageData;
	}

	public void setImage(Image imageData) {
		this.imageData = imageData;
	}

}
