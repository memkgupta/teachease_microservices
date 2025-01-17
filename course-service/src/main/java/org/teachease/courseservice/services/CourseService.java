package org.teachease.courseservice.services;

import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.CourseDTO;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.entities.CourseEntity;
import org.teachease.courseservice.entities.ModuleList;
import org.teachease.courseservice.entities.TestDTO;
import org.teachease.courseservice.repositories.CourseRepository;
import org.teachease.courseservice.repositories.ModuleRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    public CourseService(CourseRepository courseRepository, ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
    }
    public CourseDTO createCourse(CourseDTO courseDTO,String userId) {
      try{
          CourseEntity course = new CourseEntity();
          course.setTeacherId(userId);
          course.setEndDate(courseDTO.getEndDate());
          course.setStartDate(courseDTO.getStartDate());
          course.setCreatedAt(new Timestamp(System.currentTimeMillis()));
          course.setTitle(courseDTO.getTitle());
          course.setDescription(courseDTO.getDescription());
          courseRepository.save(course);
          ModuleList moduleList = new ModuleList();
          moduleList.setCourse(course);
          moduleRepository.save(moduleList);
          course.setModules(moduleList);
          courseRepository.save(course);
          return course.courseDTO();
      }
      catch(Exception e){
          e.printStackTrace();
          throw new RuntimeException("Internal Server Error");
      }
    }
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        try{
            CourseEntity course = courseRepository.findById(courseDTO.getId()).orElse(null);
            if(course==null){
                throw new RuntimeException("Course not found");
            }
            course.setEndDate(courseDTO.getEndDate());
            course.setStartDate(courseDTO.getStartDate());
            course.setTitle(courseDTO.getTitle());
            course.setDescription(courseDTO.getDescription());
            courseRepository.save(course);
            return course.courseDTO();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }
    public boolean deleteCourse(CourseDTO courseDTO) {
        try{
            CourseEntity course = courseRepository.findById(courseDTO.getId()).orElse(null);
            if(course==null){
                throw new RuntimeException("Course not found");
            }
            courseRepository.delete(course);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }
    public List<CourseDTO> getAllCourses(String userId) {
        try{
            List<CourseEntity> courses = courseRepository.findAllByTeacherId(userId);
            List<CourseDTO> courseDTOs = new ArrayList<>();
            courses.forEach(course->courseDTOs.add(course.courseDTO()));
            return courseDTOs;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }
    public CourseDTO getCourse(String courseId) {
        try{
            CourseEntity course = courseRepository.findById(courseId).orElse(null);

         return course.courseDTO();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }

}
