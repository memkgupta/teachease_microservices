package org.teachease.userservice.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.teachease.userservice.entity.UserInfo;

@JsonFilter("UserFilter")
public class UserInfoDTO {
    public UserInfoDTO(String userId, String name, String email, String phone, String profilePic) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    public UserInfoDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("profile_pic")
    private String profilePic;

    public UserInfo transformToUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setProfilePic(profilePic);
        return userInfo;
    }
}
