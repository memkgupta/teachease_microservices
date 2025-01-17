package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment,String> {
}
