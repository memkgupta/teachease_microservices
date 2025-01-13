package org.teachease.userservice.services;

import org.springframework.stereotype.Service;
import org.teachease.userservice.dtos.UserInfoDTO;
import org.teachease.userservice.entity.UserInfo;
import org.teachease.userservice.repositories.UserInfoRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserInfoRepository userInfoRepository;

    public UserService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoDTO getUserInfoById(String userId) {
        try{
            Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);
           if(!userInfo.isPresent()){
               throw new RuntimeException("User not found");
           }
            return new UserInfoDTO(userInfo.get().getUserId(),userInfo.get().getName(),userInfo.get().getEmail(),userInfo.get().getPhone(),userInfo.get().getProfilePic());
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }

    public UserInfoDTO createOrUpdateUser(UserInfoDTO userInfoDto) {
        // Validate the input DTO
        validateUserInfoDto(userInfoDto);

        // Check if the user exists
        Optional<UserInfo> optionalUser = userInfoRepository.findByUserId(userInfoDto.getUserId());

        // Update if exists or create a new user
        UserInfo userInfo = optionalUser
                .map(existingUser -> updateUser(existingUser, userInfoDto))
                .orElseGet(() -> createUser(userInfoDto));

        // Convert the updated or created user back to DTO and return
        return convertToDto(userInfo);
    }

    // Helper method to validate input
    private void validateUserInfoDto(UserInfoDTO userInfoDto) {
        if (userInfoDto == null) {
            throw new IllegalArgumentException("User information cannot be null");
        }
        if (userInfoDto.getUserId() == null || userInfoDto.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        // Add more validations if needed, such as email, phone, etc.
    }

    // Helper method to update an existing user
    private UserInfo updateUser(UserInfo existingUser, UserInfoDTO userInfoDto) {
        // Update fields based on DTO
      existingUser.setName(userInfoDto.getName());
        existingUser.setEmail(userInfoDto.getEmail());
        existingUser.setPhone(userInfoDto.getPhone());
        existingUser.setProfilePic(userInfoDto.getProfilePic());
        // Save updated user in the repository
        return userInfoRepository.save(existingUser);
    }

    // Helper method to create a new user
    private UserInfo createUser(UserInfoDTO userInfoDto) {
        UserInfo newUser = userInfoDto.transformToUserInfo();
        return userInfoRepository.save(newUser);
    }

    // Helper method to convert UserInfo entity to DTO
    private UserInfoDTO convertToDto(UserInfo userInfo) {
        return new UserInfoDTO(
                userInfo.getUserId(),
               userInfo.getName(),

                userInfo.getEmail(),
                userInfo.getPhone(),
                userInfo.getProfilePic()
        );
    }
}
