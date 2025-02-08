package org.teachease.courseservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.teachease.courseservice.entities.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment,String>, JpaSpecificationExecutor<Assignment> {
//    Page<Assignment> findAll(Specification<Assignment> specification, Pageable pageable);
}
