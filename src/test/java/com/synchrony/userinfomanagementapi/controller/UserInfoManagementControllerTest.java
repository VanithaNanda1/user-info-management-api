package com.synchrony.userinfomanagementapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synchrony.userinfomanagement.controller.UserInfoManagementController;
import com.synchrony.userinfomanagement.entity.Image;
import com.synchrony.userinfomanagement.entity.UserProfile;
import com.synchrony.userinfomanagement.entity.UserRegistration;
import com.synchrony.userinfomanagement.service.ImageService;
import com.synchrony.userinfomanagement.service.UserManagementService;

@ExtendWith(MockitoExtension.class)
public class UserInfoManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserManagementService userManagementService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private UserInfoManagementController userInfoManagementController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userInfoManagementController).build();
    }
    
    @Test
    public void testRegisterUser() throws Exception {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setUserName("testuser");
        userRegistration.setPassword("testpassword");
       
        when(userManagementService.saveUserDetails(any(UserRegistration.class))).thenReturn(1L);

        mockMvc.perform(post("/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRegistration)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testRegisterUserWithInvalidDetails() throws Exception {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setUserName("");
        userRegistration.setPassword("");
    
        mockMvc.perform(post("/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRegistration)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUploadImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imageFile", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());

        Image image = new Image();
        image.setDeletehash("testhash");
        image.setImageUrl("testurl");

        when(imageService.uploadImageToImageUr(any(MockMultipartFile.class))).thenReturn(image);

        mockMvc.perform(multipart("/uploadImage")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(image)));
    }

    @Test
    public void testDeleteImage() throws Exception {
        doNothing().when(imageService).deleteImageFromImageUr("testhash");

        mockMvc.perform(delete("/deleteImage/{imageHash}", "testhash"))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewUserInfoWithImages() throws Exception {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(1L);

        when(userManagementService.retreiveUserProfile(anyLong())).thenReturn(userProfile);

        mockMvc.perform(get("/viewUserInfoWithImages/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userProfile)));
    }

    @Test
    public void testAssociateUserWithImages() throws Exception {
        List<Image> images = new ArrayList<>();
        Image image1 = new Image();
        image1.setDeletehash("testhash1");
        image1.setImageUrl("testurl1");
        Image image2 = new Image();
        image2.setDeletehash("testhash2");
        image2.setImageUrl("testurl2");
        images.add(image1);
        images.add(image2);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(1L);


        when(userManagementService.associateUserProfileWithImages(any(List.class), anyLong())).thenReturn(userProfile);

        mockMvc.perform(post("/associateUserWithImages/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(images)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userProfile)));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
