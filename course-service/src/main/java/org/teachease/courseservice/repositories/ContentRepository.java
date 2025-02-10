package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.teachease.courseservice.entities.Content;


public interface ContentRepository extends JpaRepository<Content,String>, JpaSpecificationExecutor<Content> {
}
