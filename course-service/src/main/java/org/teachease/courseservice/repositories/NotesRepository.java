package org.teachease.courseservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teachease.courseservice.entities.Notes;

public interface NotesRepository extends JpaRepository<Notes,String> {
}
