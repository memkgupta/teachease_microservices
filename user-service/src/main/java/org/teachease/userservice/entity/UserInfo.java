package org.teachease.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "user_info")
public class UserInfo {
    public UserInfo() {
    }

    public UserInfo(String id, String userId, String name, Role role, String email, String phone, String profilePic) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("name")
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonProperty("email")
    @NotNull
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("profile_pic")
    private String profilePic;
    @PrePersist
    public void setDefaults() {
        if (role == null) {
            role = Role.USER;  // Set default if role is not set
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}

enum Role {
    TEACHER, STUDENT,ADMIN,USER
}