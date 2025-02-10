package org.teachease.courseservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.courseservice.dtos.AssignmentDTO;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.PageDTO;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.filtering.specificationFactories.AssignmentSpecificationFactory;
import org.teachease.courseservice.services.AssignmentService;
import org.teachease.courseservice.filtering.specifications.AssignmentSpecification;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Assignment assignment = assignmentService.createAssignment(assignmentDTO, userId);
        return ResponseEntity.ok(AssignmentDTO.builder()
                .id(assignment.getId())
                .createdAt(assignment.getCreatedAt())
                .assignmentResource(assignment.getAssignmentResource())
                .solutionResource(assignment.getSolutionResource())
                .module(assignment.getModule().getPartialDTO())
                .courseId(assignment.getCourseId())
                .dueDate(assignment.getDueDate())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable String id, @RequestBody AssignmentDTO assignmentDTO, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Assignment updated = assignmentService.updateAssignment(assignmentDTO, userId);
        return ResponseEntity.ok(updated.getPartialDTO());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAssignment(@PathVariable String id,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        assignmentService.deleteAssignment(id, userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("")
    public ResponseEntity<PageDTO<AssignmentDTO>> getAssignments( @RequestParam Map<String,String> filters,
                                                                    @RequestParam String courseId,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int limit,
                                                                 @RequestParam(defaultValue = "title") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortOrder,
                                                                 @RequestParam(required = false) String title,
                                                                 @RequestParam(required = false, name = "module_id") String moduleId,
                                                                 @RequestParam(required = false, name = "eid") String enrollmentId,
                                                                 @RequestParam(required = false, name = "ai_gen", defaultValue = "false") Boolean isAiGenerated,

                                                                 HttpServletRequest request) {

        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String userId = request.getHeader("X-USER-ID");

        //Specifications for dynamic query


        Page<Assignment> assignmentPage = assignmentService.getAssignments(
                courseId,userId, enrollmentId, PageRequest.of(page,limit,Sort.by(direction,sortBy)),filters
        );
       List<AssignmentDTO> assignmentDTOList = assignmentPage.get().map(
               (a)->{
                   return AssignmentDTO.builder()
                           .id(a.getId())
                           .createdAt(a.getCreatedAt())
                           .assignmentResource(a.getAssignmentResource())
                           .solutionResource(a.getSolutionResource())
                           .title(a.getTitle())
                           .description(a.getDescription())
                           .courseId(a.getCourseId())
                           .module(ModuleDTO.builder()
                                   .id(a.getModule().getId())
                                   .title(a.getModule().getTitle())
                                   .build())
                           .build();
               }
       ).toList();
       PageDTO<AssignmentDTO> pageDTO = new PageDTO<AssignmentDTO>() {
           @Override
           public int getPageNumber() {
               return assignmentPage.getNumber();
           }

           @Override
           public int getPageSize() {
               return assignmentPage.getSize();
           }

           @Override
           public long getTotalElements() {
               return assignmentPage.getTotalElements();
           }

           @Override
           public List<AssignmentDTO> getContent() {
               return assignmentDTOList;
           }
       };
       return ResponseEntity.ok(pageDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable String id, HttpServletRequest request,@RequestParam(required = false,name = "eid") String enrollmentId) {
        String userId = request.getHeader("X-USER-ID");
        Assignment assignment = assignmentService.getAssignmentById(id,userId,enrollmentId);
        return ResponseEntity.ok(assignment.getPartialDTO());
    }
}
