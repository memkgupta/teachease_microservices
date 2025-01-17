package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment,String> {
}
