package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.ModuleContent;

public interface ContentRepository extends JpaRepository<ModuleContent,String> {
}
