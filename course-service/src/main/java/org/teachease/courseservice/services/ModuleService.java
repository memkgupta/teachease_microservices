package org.teachease.courseservice.services;

import jakarta.annotation.Nullable;
import org.bouncycastle.math.raw.Mod;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.ModuleListDTO;
import org.teachease.courseservice.entities.CourseEntity;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.entities.ModuleList;
import org.teachease.courseservice.errorhandler.errors.InternalServerError;
import org.teachease.courseservice.errorhandler.errors.ResourceNotFoundException;
import org.teachease.courseservice.errorhandler.errors.UnauthorisedException;
import org.teachease.courseservice.repositories.CourseRepository;
import org.teachease.courseservice.repositories.ModuleNodeRepository;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleNodeRepository moduleNodeRepository;
    private final CourseRepository courseRepository;
    private final Authorisation authorisation;


    public ModuleService(ModuleNodeRepository moduleNodeRepository, CourseRepository courseRepository, Authorisation authorisation) {
        this.moduleNodeRepository = moduleNodeRepository;
        this.courseRepository = courseRepository;
        this.authorisation = authorisation;
    }
    // get modules for teacher
    public Page<Module> getCourseModules(String courseId, int limit, int page, String sortOrder, String sortBy, String parentId,String userId) {

        try{

            CourseEntity course = courseRepository.findById(courseId).orElse(null);
            if(course == null) {
                throw new ResourceNotFoundException("Course",courseId,new Timestamp(System.currentTimeMillis()));
            }
            boolean permission = authoriseReadPermission(courseId,userId);
            if(!permission) {
                throw new UnauthorisedException("Course",courseId,"READ");
            }

            Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
            Page<Module> modules ;
                    if(parentId != null) {
                        Module parent = moduleNodeRepository.findById(parentId).orElse(null);
                        if(parent == null) {
                            throw new ResourceNotFoundException("Module",parentId,new Timestamp(System.currentTimeMillis()));
                        }
                        modules = moduleNodeRepository.findByCourseAndParent(course,parent ,PageRequest.of(page, limit,Sort.by(direction,sortBy)));
                    }
                    else{
                        modules = moduleNodeRepository.findByCourse(course, PageRequest.of(page, limit,Sort.by(direction,sortBy)));

                    }

            return modules;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), Arrays.toString(e.getStackTrace()), new Timestamp(System.currentTimeMillis()));
        }
    }

    public ModuleDTO addModule(ModuleDTO moduleDTO,String userId) {
       CourseEntity course = courseRepository.findById(moduleDTO.getCourseId()).orElse(null);
       if(course == null) {
           throw new RuntimeException("Course not found");
       }
       boolean permission = authoriseAddModulePermission(course.getId(),userId);
       if(!permission) {
           throw new RuntimeException("You do not have permission to perform this action");
       }
       Module parent = null;
       if(moduleDTO.getParent()!=null) {
            parent = moduleNodeRepository.findById(moduleDTO.getParent().getId()).orElse(null);
       }
      Module module = Module.builder()
              .title(moduleDTO.getTitle())
              .course(course)
              .parent(parent)
              .priority(moduleDTO.getPriority())
              .description(moduleDTO.getDescription())
              .startDate(moduleDTO.getStartDate())
              .endDate(moduleDTO.getEndDate())
              .createdAt(new Timestamp(System.currentTimeMillis()))
              .build();
      moduleNodeRepository.save(module);
      return module.getPartialDTO();
    }
    public ModuleDTO updateModule(ModuleDTO moduleDTO,String userId) {
        try{
            Module module = moduleNodeRepository.findById(moduleDTO.getId()).orElse(null);
            if(module == null) {
                throw new RuntimeException("No module found for id " + moduleDTO.getId());
            }
            boolean permission = authoriseWritePermission(module.getId(),userId);
            if(!permission) {
                throw new RuntimeException("You do not have permission to perform this action");
            }
            module.setDescription(moduleDTO.getDescription());
            module.setTitle(moduleDTO.getTitle());
            module.setEndDate(moduleDTO.getEndDate());
            module.setStartDate(moduleDTO.getStartDate());
            moduleNodeRepository.save(module);
            return module.getPartialDTO();
        }
        catch(Exception e){
e.printStackTrace();
throw new RuntimeException("Something went wrong");
        }
    }
    public ModuleDTO getModuleById(String moduleId,String userId) {
        try{
            Module module = moduleNodeRepository.findById(moduleId).orElse(null);
            if(module == null) {
                throw new RuntimeException("No module found for id " + moduleId);
            }
            boolean permission = authoriseReadPermission(module.getCourse().getId(),userId);
            if(!permission){
                throw new RuntimeException("Don't have permission to perform this action");
            }
            return module.getDTO();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    // get module for student with enrollment id
    public ModuleDTO getModuleById(String moduleId,String userId,String enrollmentId) {
        try{
            Module module = moduleNodeRepository.findById(moduleId).orElse(null);
            if(module == null) {
                throw new RuntimeException("No module found for id " + moduleId);
            }
            boolean permission = authoriseReadPermission(module.getCourse().getId(),userId,enrollmentId);
            if(!permission){
                throw new RuntimeException("Don't have permission to perform this action");
            }
            return module.getDTO();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    public boolean deleteModule(ModuleDTO moduleDTO,String userId) {
        try {

            Module module = moduleNodeRepository.findById(moduleDTO.getId()).orElse(null);
            if(module == null) {
                throw new RuntimeException("No module found for id " + moduleDTO.getId());
            }
        boolean permission = authoriseDeletePermission(module.getId(),null,userId);
         moduleNodeRepository.delete(module);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    public boolean authoriseReadPermission(String courseId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("action","READ");
        queryMap.put("userId",userId);
        queryMap.put("resourceName","course."+courseId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }
    //authorise for student
    public boolean authoriseReadPermission(String courseId,String userId,String enrollmentId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("action","READ");
        queryMap.put("userId",userId);
        queryMap.put("extraInfo",enrollmentId);
        queryMap.put("resourceName","course."+courseId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }

    public boolean authoriseWritePermission(String moduleId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",moduleId);
        queryMap.put("action","WRITE");
        queryMap.put("userId",userId);
        queryMap.put("resourceName","module."+moduleId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }
    public boolean authoriseAddModulePermission(String courseId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("action","WRITE");
        queryMap.put("userId",userId);
        queryMap.put("resourceName","course."+courseId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }
    public boolean authoriseDeletePermission(String moduleId,@Nullable String staffId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",moduleId);
        queryMap.put("action","DELETE");
        queryMap.put("userId",userId);
        queryMap.put("resourceName","module."+moduleId);
        boolean permission = authorisation.authorise(queryMap);
        return permission;
    }

    public Module getModule(String id) {
        try{
            Module module = moduleNodeRepository.findById(id).orElse(null);
            return module;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
}
