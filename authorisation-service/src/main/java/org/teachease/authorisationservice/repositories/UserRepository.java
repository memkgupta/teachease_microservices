package org.teachease.authorisationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.authorisationservice.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserId(String userId);
}
