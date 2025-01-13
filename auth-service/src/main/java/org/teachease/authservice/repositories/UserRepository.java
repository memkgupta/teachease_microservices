package org.teachease.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.authservice.enitites.User;

public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String email);
}