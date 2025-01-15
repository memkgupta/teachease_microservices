package org.teachease.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.userservice.entity.Preferences;

public interface PreferenceRepository extends JpaRepository<Preferences,String> {
    Preferences findByUserId(String userId);
}
