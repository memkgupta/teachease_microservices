package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.Module;

public interface ModuleNodeRepository extends JpaRepository<Module,String > {
}
