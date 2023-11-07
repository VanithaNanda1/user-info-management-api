package com.synchrony.userinfomanagement.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_image")
public class Image implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long ImageId;
	
	@JsonProperty("id")
	private String Id;
	
	@JsonProperty("link")
	private String imageUrl;
	@JsonProperty("deletehash")
	private String deletehash;
	
	public Long getImageId() {
		return ImageId;
	}
	public void setImageId(Long imageId) {
		ImageId = imageId;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDeletehash() {
		return deletehash;
	}
	public void setDeletehash(String deletehash) {
		this.deletehash = deletehash;
	}
	public void setId(long l) {
	}

	
}
