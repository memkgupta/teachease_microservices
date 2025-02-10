package org.teachease.courseservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.courseservice.dtos.ContentDTO;
import org.teachease.courseservice.dtos.PageDTO;
import org.teachease.courseservice.entities.Content;
import org.teachease.courseservice.services.ContentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }
    @PostMapping
    public ResponseEntity<ContentDTO> createContent(@RequestBody ContentDTO contentDTO, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Content content = contentService.create(userId, contentDTO);
        return ResponseEntity.ok().body(content.partialDTO());
    }
   @GetMapping("/{id}")
    public ResponseEntity<ContentDTO> getContentById(
            @PathVariable String id, @RequestParam(name = "e_id") String enrollmentId, HttpServletRequest request
    ){
        String userId = request.getHeader("X-USER-ID");
        Content content = contentService.getById(id,userId,enrollmentId);
        return ResponseEntity.ok().body(content.partialDTO());
    }
    @GetMapping
    public ResponseEntity<PageDTO<ContentDTO>> getContents(
            @RequestParam Map<String,String> filters,
            @RequestParam String courseId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "e_id") String enrollmentId,
            HttpServletRequest request
    ){
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        String userId = request.getHeader("X-USER-ID");
        Page<Content> contentPage = contentService.getContents(
                courseId,userId,enrollmentId, PageRequest.of(page,limit,Sort.by(direction,sortBy)),filters
        );
        List<ContentDTO> contentDTOList = contentPage.getContent().stream().map(
                content -> {
                    return content.partialDTO();
                }
        ).toList();
        PageDTO<ContentDTO> pageDTO = new PageDTO<ContentDTO>() {
            @Override
            public int getPageNumber() {
                return contentPage.getNumber();
            }

            @Override
            public int getPageSize() {
                return contentPage.getSize();
            }

            @Override
            public long getTotalElements() {
                return contentPage.getTotalElements();
            }

            @Override
            public List<ContentDTO> getContent() {
                return contentDTOList;
            }
        };
        return ResponseEntity.ok().body(pageDTO);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ContentDTO> updateContent(@PathVariable String id, @RequestBody ContentDTO contentDTO, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Content content = contentService.update(userId,contentDTO);
        return ResponseEntity.ok().body(content.partialDTO());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteContent(@PathVariable String id, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        contentService.deleteContent(id,userId);
        return ResponseEntity.noContent().build();
    }

}
