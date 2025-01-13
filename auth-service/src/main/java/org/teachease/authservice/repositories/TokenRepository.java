package org.teachease.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.authservice.enitites.Token;

public interface TokenRepository extends JpaRepository<Token,String> {
    Token findByToken(String token);
}
