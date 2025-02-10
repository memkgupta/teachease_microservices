package org.teachease.authorisationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.teachease.authorisationservice.entities.RelationEntity;
import org.teachease.authorisationservice.entities.ResourceEntity;

import javax.management.relation.Relation;
import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<RelationEntity,String> {
    List<RelationEntity> findByResource(ResourceEntity resource);

    Optional<RelationEntity> findByResourceAndName(ResourceEntity resource,String name);
}
