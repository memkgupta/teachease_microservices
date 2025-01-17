package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.ModuleList;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<ModuleList,String> {
    Optional<ModuleList> findByCourse(String courseId);
}
