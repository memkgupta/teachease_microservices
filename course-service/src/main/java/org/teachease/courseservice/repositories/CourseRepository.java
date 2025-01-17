package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.CourseEntity;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity,String> {
    List<CourseEntity> findAllByTeacherId(String teacherId);
}
