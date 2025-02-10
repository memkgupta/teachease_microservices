package org.teachease.courseservice.services;

import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.CourseDTO;
import org.teachease.courseservice.dtos.authorisation.RelationDTO;
import org.teachease.courseservice.dtos.authorisation.ResourceDTO;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.entities.CourseEntity;
import org.teachease.courseservice.entities.ModuleList;
import org.teachease.courseservice.entities.TestDTO;
import org.teachease.courseservice.errorhandler.errors.InternalServerError;
import org.teachease.courseservice.errorhandler.errors.ResourceNotFoundException;
import org.teachease.courseservice.errorhandler.errors.UnauthorisedException;
import org.teachease.courseservice.repositories.CourseRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class CourseService {
    private final Authorisation authorisation;
    private final CourseRepository courseRepository;


    public CourseService(Authorisation authorisation, CourseRepository courseRepository) {
        this.authorisation = authorisation;
        this.courseRepository = courseRepository;

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
authorisation.addRelation(RelationDTO.builder()
                .created(new Timestamp(System.currentTimeMillis()))
                .description("Course")
                .name("OWNER")
        .resource(ResourceDTO.builder()
                .created(new Timestamp(System.currentTimeMillis()))
                .owner(userId)
                .resourceType("course")
                .resourceId(course.getId())
                .resourceName("course")
                .build())
                .userId(userId)
        .build());
          return course.courseDTO();
      }
      catch(Exception e){
          e.printStackTrace();
          throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()),new Timestamp(System.currentTimeMillis()));
      }
    }
    public CourseDTO updateCourse(CourseDTO courseDTO,String userId) {
        try{
            CourseEntity course = courseRepository.findById(courseDTO.getId()).orElse(null);
            if(course==null){
                throw new ResourceNotFoundException("Course",courseDTO.getId(),new Timestamp(System.currentTimeMillis()));
            }
           boolean permission = authoriseWriteCourse(course.getId(),userId);
            if(!permission){
                throw new UnauthorisedException("Course", course.getId(), "WRITE");
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
            throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()),new Timestamp(System.currentTimeMillis()));
        }
    }
    public boolean deleteCourse(String courseId,String userId) {
        try{
            CourseEntity course = courseRepository.findById(courseId).orElse(null);
            if(course==null){
                throw new ResourceNotFoundException("Course",courseId,new Timestamp(System.currentTimeMillis()));
            }
            boolean permission = authoriseDeleteCourse(course.getId(),userId);
            if(!permission){
                throw new UnauthorisedException("Course", course.getId(), "DELETE");
            }
            courseRepository.delete(course);
            authorisation.deleteResource(ResourceDTO.builder().resourceId(course.getId()).build());
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()),new Timestamp(System.currentTimeMillis()));
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
            throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()),new Timestamp(System.currentTimeMillis()));
        }
    }
    public CourseEntity getCourse(String courseId) {
        try{
            CourseEntity course = courseRepository.findById(courseId).orElse(null);

            return course;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()),new Timestamp(System.currentTimeMillis()));
        }
    }
    public CourseDTO getCourse(String courseId,String enrollmentId,String userId) {
        try{
            CourseEntity course = courseRepository.findById(courseId).orElse(null);
            if(course==null){
                throw new ResourceNotFoundException("Course",courseId,new Timestamp(System.currentTimeMillis()));
            }
          boolean permission = authoriseReadCourse(courseId,userId,enrollmentId);
            if(!permission)
            {
                throw new UnauthorisedException("Course", courseId, "READ");
            }
         return course.courseDTO();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()),new Timestamp(System.currentTimeMillis()));
        }
    }
    public boolean authoriseReadCourse(String courseId,String userId,String enrollmentId) {
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("extraInfo",enrollmentId);
        queryMap.put("action","READ");
        queryMap.put("userId",userId);
        queryMap.put("resourceName","course."+courseId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }
    public boolean authoriseWriteCourse(String courseId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("action","WRITE");
        queryMap.put("resourceName","course."+courseId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }
    public boolean authoriseDeleteCourse(String courseId,String userId) {
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("resourceName","course."+courseId);
        queryMap.put("action","DELETE");
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }

}
