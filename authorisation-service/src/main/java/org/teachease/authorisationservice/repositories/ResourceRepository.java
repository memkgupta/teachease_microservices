package org.teachease.authorisationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.authorisationservice.entities.ResourceEntity;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<ResourceEntity, String> {

    Optional<ResourceEntity> findByResourceId(String resourceId);
}
