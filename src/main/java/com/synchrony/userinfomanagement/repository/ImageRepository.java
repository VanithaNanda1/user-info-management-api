package com.synchrony.userinfomanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synchrony.userinfomanagement.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
}
