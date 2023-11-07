package com.synchrony.userinfomanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synchrony.userinfomanagement.entity.UserInformation;

public interface UserInformationRepository extends JpaRepository<UserInformation, Long>{
}
