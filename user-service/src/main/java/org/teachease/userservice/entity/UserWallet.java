package org.teachease.userservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_wallet")
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private Integer tokens;

    public String getId() {
        return id;
    }

    public UserWallet() {
    }

    public UserWallet(String id, String userId, Integer tokens) {
        this.id = id;
        this.userId = userId;
        this.tokens = tokens;
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

    public Integer getTokens() {
        return tokens;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }
}
