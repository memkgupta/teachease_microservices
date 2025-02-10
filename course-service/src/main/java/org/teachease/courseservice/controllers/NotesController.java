package org.teachease.courseservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.NotesDTO;
import org.teachease.courseservice.dtos.PageDTO;
import org.teachease.courseservice.entities.Notes;
import org.teachease.courseservice.services.NotesService;
import org.teachease.courseservice.filtering.specifications.NotesSpecification;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/")
    public ResponseEntity<NotesDTO> addNotes(@RequestBody NotesDTO notesDTO,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Notes notes = notesService.create(notesDTO, userId);
        return ResponseEntity.ok(notes.getPartialDTO());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<NotesDTO> updateNotes(@PathVariable String id, @RequestBody NotesDTO notesDTO,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Notes updated = notesService.update(notesDTO, userId);
        return ResponseEntity.ok(updated.getPartialDTO());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNotes(@PathVariable String id,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        notesService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/")
    public ResponseEntity<PageDTO<NotesDTO>> getAllNotes(@RequestParam Map<String,String> filters,
                                                         @RequestParam String courseId,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int limit ,
                                                         @RequestParam(defaultValue = "title") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortOrder,
                                                         @RequestParam (required = false) String title,
                                                         @RequestParam (required = false,name = "module_id") String moduleId,
                                                         @RequestParam(required = false,name = "eid") String enrollmentId,
                                                         @RequestParam(required = false,name = "ai_gen",defaultValue = "false") Boolean isAiGenerated,
                                                         @RequestParam(required = false,name = "file_type") String fileType,
                                                         HttpServletRequest request
    ) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String userId = request.getHeader("X-USER-ID");

        //Specifications for dynamic query

        Page<Notes> notesPage = notesService.getNotes(
                courseId,userId, enrollmentId,PageRequest.of(page,limit,Sort.by(direction,sortBy)),filters
        );
        List<NotesDTO> notes = notesPage.get().map((n)->{
            return NotesDTO.builder()
                    .id(n.getId())
                    .title(n.getTitle())
                    .description(n.getDescription())
                    .resourceURL(n.getResourceURL())
                    .isAiGenerated(n.isAiGenerated())
                    .fileType(n.getFileType())
                    .module(
                           ModuleDTO.builder()
                                   .id(n.getModule().getId())
                                   .build()
                    )
                    .build();
        }).toList();
        PageDTO<NotesDTO> pageDTO = new PageDTO<NotesDTO>() {
            @Override
            public int getPageNumber() {
                return notesPage.getNumber();
            }

            @Override
            public int getPageSize() {
                return notesPage.getSize();
            }

            @Override
            public long getTotalElements() {
                return notesPage.getTotalElements();
            }

            @Override
            public List<NotesDTO> getContent() {
                return notes;
            }
        };
        return ResponseEntity.ok(pageDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NotesDTO> getNotes(@PathVariable String id,@RequestParam(required = false,name = "eid") String eid,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Notes notes = notesService.getNoteById(id,userId,eid);
        return ResponseEntity.ok(NotesDTO.builder()
                        .resourceURL(notes.getResourceURL())
                        .id(notes.getId())
                        .isAiGenerated(notes.isAiGenerated())
                        .id(notes.getId())
                        .module(notes.getModule().getPartialDTO())
                        .title(notes.getTitle())
                        .description(notes.getDescription())
                        .fileSize(notes.getFileSize())
                        .fileType(notes.getFileType())
                .build());
    }
}

