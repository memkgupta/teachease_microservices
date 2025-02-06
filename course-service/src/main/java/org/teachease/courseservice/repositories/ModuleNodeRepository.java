package org.teachease.courseservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.CourseEntity;
import org.teachease.courseservice.entities.Module;

public interface ModuleNodeRepository extends JpaRepository<Module,String > {
   Page<Module> findByCourse(CourseEntity course, Pageable pageable);
   Page<Module> findByCourseAndParent(CourseEntity course, Module parent, Pageable pageable);

}
