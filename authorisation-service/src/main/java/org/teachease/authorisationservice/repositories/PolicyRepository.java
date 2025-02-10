package org.teachease.authorisationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.authorisationservice.entities.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, String> {
    List<Policy> findByResourceNameAndAction(String resourceName, String action);
}
