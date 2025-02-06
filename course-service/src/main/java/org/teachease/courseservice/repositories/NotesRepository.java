package org.teachease.courseservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.teachease.courseservice.entities.Notes;

public interface NotesRepository extends JpaRepository<Notes,String>, JpaSpecificationExecutor<Notes> {
    Page<Notes> findByCourseId(String courseId, Pageable pageable);
    Page<Notes> findByModule(Module module, Pageable pageable);


}
