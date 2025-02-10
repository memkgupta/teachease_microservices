package org.teachease.courseservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.AssignmentDTO;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.entities.CourseEntity;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.errorhandler.errors.InternalServerError;
import org.teachease.courseservice.errorhandler.errors.ResourceNotFoundException;
import org.teachease.courseservice.errorhandler.errors.UnauthorisedException;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.specificationFactories.AssignmentSpecificationFactory;
import org.teachease.courseservice.filtering.specificationFactories.SpecificationUtils;
import org.teachease.courseservice.repositories.AssignmentRepository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final Authorisation authorisation;
    private final CourseService courseService;
    private final ModuleService moduleService;

    public Assignment createAssignment(AssignmentDTO assignmentDTO,String userId) {

        try{
            CourseEntity course = courseService.getCourse(assignmentDTO.getCourseId());
            if(course==null)
            {
                throw new ResourceNotFoundException("Course",assignmentDTO.getCourseId(),new Timestamp(System.currentTimeMillis()));
            }
            boolean permission =authoriseCourseOwner(assignmentDTO.getCourseId(),userId);
            if(!permission){
                throw new UnauthorisedException("Course",assignmentDTO.getCourseId(),"WRITE");
            }
            Assignment assignment = new Assignment();
            assignment.setCourseId(course.getId());
            if(assignmentDTO.getModule()!=null)
            {
                Module module = moduleService.getModule(assignmentDTO.getModule().getId());
                if(module==null)
                {
                    throw new ResourceNotFoundException("Module",assignmentDTO.getModule().getId(),new Timestamp(System.currentTimeMillis()));
                }
                assignment.setModule(module);
            }
            assignment.setTitle(assignmentDTO.getTitle());
            assignment.setDescription(assignmentDTO.getDescription());
            assignment.setAssignmentResource(assignmentDTO.getAssignmentResource());
            assignment.setSolutionResource(assignmentDTO.getSolutionResource());
            assignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            assignment.setDueDate(assignmentDTO.getDueDate());
            assignment.setPoints(assignmentDTO.getPoints());
            return assignmentRepository.save(assignment);
        }
        catch(Exception e){
            throw new InternalServerError(e.getMessage(),e.getStackTrace().toString(),new Timestamp(System.currentTimeMillis()));
        }
    }
   public Assignment updateAssignment(AssignmentDTO assignmentDTO, String userId) {
        try{
            Assignment assignment = assignmentRepository.findById(assignmentDTO.getId()).orElse(null);
            if(assignment==null)
            {
                throw new ResourceNotFoundException("Assignment",assignmentDTO.getId(),new Timestamp(System.currentTimeMillis()));
            }
            boolean permission = authoriseCourseOwner(assignmentDTO.getCourseId(),userId);
            if(!permission){
                throw new UnauthorisedException("Course",assignmentDTO.getCourseId(),"WRITE");
            }
            if(assignmentDTO.getAssignmentResource()!=null && !assignment.getAssignmentResource().isEmpty()){
                assignment.setAssignmentResource(assignmentDTO.getAssignmentResource());
            }
           if(assignmentDTO.getSolutionResource()!=null && !assignment.getSolutionResource().isEmpty()){
               assignment.setSolutionResource(assignmentDTO.getSolutionResource());
           }
           if(assignmentDTO.getTitle()!=null && !assignment.getTitle().isEmpty()){
               assignment.setTitle(assignmentDTO.getTitle());
           }
           if(assignmentDTO.getDescription()!=null && !assignment.getDescription().isEmpty()){
               assignment.setDescription(assignmentDTO.getDescription());
           }
           if(assignmentDTO.getDueDate()!=null){
               assignment.setDueDate(assignmentDTO.getDueDate());
           }
           if(assignmentDTO.getPoints()!=null){
               assignment.setPoints(assignmentDTO.getPoints());
           }
           if(assignmentDTO.getModule()!=null){
               Module module = moduleService.getModule(assignmentDTO.getModule().getId());
               if(module==null){
                   throw new RuntimeException("Module not found");
               }
               assignment.setModule(module);
           }
//           if(assignmentDTO.i)
           return assignmentRepository.save(assignment);

        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(),e.getStackTrace().toString(),new Timestamp(System.currentTimeMillis()));
        }
   }
   public Assignment getAssignment(String assignmentId) {
        return assignmentRepository.findById(assignmentId).orElse(null);
   }
   public Assignment getAssignmentById(String assignmentId, String userId,String enrollmentId) {
        try {
            Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
            if(assignment==null)
            {
                throw new ResourceNotFoundException("Assignment",assignmentId,new Timestamp(System.currentTimeMillis()));
            }
            boolean permission = authoriseReadCourse(assignment.getCourseId(),userId,enrollmentId);
            if(!permission){
                throw new UnauthorisedException("Course",assignment.getCourseId(),"READ");
            }
            return assignment;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(),e.getStackTrace().toString(),new Timestamp(System.currentTimeMillis()));
        }
   }
   public boolean deleteAssignment(String assignmentId, String userId) {
       try{
           Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
           if(assignment==null)
           {
               throw new ResourceNotFoundException("Assignment",assignmentId,new Timestamp(System.currentTimeMillis()));
           }
           boolean permission = authoriseCourseOwner(assignment.getCourseId(),userId);
           if(!permission){
               throw new UnauthorisedException("Course",assignment.getCourseId(),"WRITE");
           }
           assignmentRepository.deleteById(assignmentId);
           return true;
       }
       catch(Exception e){
           e.printStackTrace();
           throw new InternalServerError(e.getMessage(),e.getStackTrace().toString(),new Timestamp(System.currentTimeMillis()));
       }
   }
   public Page<Assignment> getAssignments(String courseId,String userId,String enrollmentId, Pageable pageable, Map<String,String> filters)
   {

       try {
           CourseEntity course = courseService.getCourse(courseId);
           if(course==null)
           {
               throw new ResourceNotFoundException("Course",courseId,new Timestamp(System.currentTimeMillis()));
           }
           boolean permission = authoriseReadCourse(courseId,userId,enrollmentId);
           if(!permission){
               throw new UnauthorisedException("Course",courseId,"READ");
           }
           SpecificationFactory<Assignment> specificationFactory = new AssignmentSpecificationFactory();
           SpecificationUtils<Assignment> specUtils = new SpecificationUtils<>(specificationFactory);
           Specification<Assignment> specification = specUtils.getSpecifications(filters);
           return assignmentRepository.findAll(specification,pageable);
       }
       catch (Exception e){
           e.printStackTrace();
           throw new InternalServerError(e.getMessage(),e.getStackTrace().toString(),new Timestamp(System.currentTimeMillis()));
       }
   }

    private boolean authoriseCourseOwner(String courseId,String userId){
        HashMap<String,String> queryMap=new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("action","WRITE");
        queryMap.put("resourceName","course."+courseId);
        return authorisation.authorise(queryMap);
    }
    private boolean authoriseReadCourse(String courseId,String userId,String enrollmentId){
        HashMap<String,String> queryMap=new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("enrollmentId",enrollmentId);
        queryMap.put("action","READ");
        queryMap.put("resourceName","course."+courseId);
        return authorisation.authorise(queryMap);
    }
//    private boolean authoriseWriteAssignment(String courseId,String userId){
//        HashMap<String,String> queryMap=new HashMap<>();
//        queryMap.put("resourceId",courseId);
//        queryMap.put("userId",userId);
//        queryMap.put("action","WRITE");
//        queryMap.put("resourceName","course."+courseId);
//        return authorisation.authorise(queryMap);
//    }
//    private boolean authoriseDeleteAssignment(String courseId,String userId){
//
//    }
}
