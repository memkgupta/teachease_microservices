package org.teachease.courseservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.courseservice.dtos.CourseDTO;
import org.teachease.courseservice.services.CourseService;

@RestController
@RequestMapping("/api/v1/course")
@CrossOrigin("*")
public class CourseController {
    private final CourseService courseService;
    public CourseController(final CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping("/create")
    public CourseDTO create(@RequestBody CourseDTO courseDTO, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
//        System.out.println(request.getHeaders());
        System.out.println(userId);
        return courseService.createCourse(courseDTO, userId);
    }
    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable String id,@RequestParam String enrollment_id,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        return courseService.getCourse(id, enrollment_id, userId);
    }
    @DeleteMapping("/${id}")
    public ResponseEntity deleteCourseById(@PathVariable String id,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        courseService.deleteCourse(id, userId);
        return ResponseEntity.noContent().build();
    }
}
