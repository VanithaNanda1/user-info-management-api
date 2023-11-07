package com.synchrony.userinfomanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synchrony.userinfomanagement.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
