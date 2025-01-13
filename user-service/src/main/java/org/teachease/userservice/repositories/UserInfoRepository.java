package org.teachease.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.userservice.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    public Optional<UserInfo> findByUserId(String userId);
}
