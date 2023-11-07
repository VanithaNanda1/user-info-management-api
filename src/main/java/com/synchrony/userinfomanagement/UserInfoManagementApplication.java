/**
 * This class is the entry point of the UserInfoManagement application.
 * It uses Spring Boot to start the application.
 */
package com.synchrony.userinfomanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserInfoManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserInfoManagementApplication.class, args);
	}

}
