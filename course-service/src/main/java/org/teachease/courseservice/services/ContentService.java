package org.teachease.courseservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.ContentDTO;
import org.teachease.courseservice.entities.Content;
import org.teachease.courseservice.entities.ContentTypeEnum;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.errorhandler.errors.InternalServerError;
import org.teachease.courseservice.errorhandler.errors.ResourceNotFoundException;
import org.teachease.courseservice.errorhandler.errors.UnauthorisedException;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.specificationFactories.ContentSpecificationFactory;
import org.teachease.courseservice.filtering.specificationFactories.SpecificationUtils;
import org.teachease.courseservice.filtering.specifications.ContentSpecification;
import org.teachease.courseservice.repositories.ContentRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final Authorisation authorisation;
    private final ModuleService moduleService;

    public Content create(String userId,ContentDTO contentDTO) {
        try {
            boolean permission = authoriseCourseOwner(contentDTO.getCourseId(), userId);
            if(!permission) {
                throw new UnauthorisedException("Course",contentDTO.getCourseId(),"WRITE");
            }
            Content content = Content.builder()
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .description(contentDTO.getDescription())
                    .resourceUrl(contentDTO.getResourceUrl())
                    .courseId(contentDTO.getCourseId())
                    .resourceType(ContentTypeEnum.valueOf(contentDTO.getResourceType()))
                    .isAiGenerated(contentDTO.isAiGenerated())
                    .fileSize(contentDTO.getFileSize())
                    .fileType(contentDTO.getFileType())
                    .scheduledAt(contentDTO.getScheduledAt())
                    .title(contentDTO.getTitle())
                    .description(contentDTO.getDescription())
                    .hidden(contentDTO.isHidden())
                    .build();
            if(contentDTO.getModule()!=null) {
                Module module = moduleService.getModule(contentDTO.getModule().getId());
                if(module==null) {
                    throw new ResourceNotFoundException("Module",contentDTO.getModule().getId(),new Timestamp(System.currentTimeMillis()));
                }
                content.setModule(module);
            }
            return contentRepository.save(content);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    public Content update(String userId,ContentDTO contentDTO) {
        try{
            Content content = contentRepository.findById(contentDTO.getId()).orElse(null);
            if(content==null) {
                throw new ResourceNotFoundException("Content",contentDTO.getId(),new Timestamp(System.currentTimeMillis()));
            }
            boolean permission = authoriseCourseOwner(contentDTO.getCourseId(), userId);
            if(!permission) {
                throw new UnauthorisedException("Course",contentDTO.getCourseId(),"WRITE");
            }
            if(contentDTO.getTitle()!=null&&!contentDTO.getTitle().isEmpty()&&!contentDTO.getTitle().equals(content.getTitle())) {
                content.setTitle(contentDTO.getTitle());
            }
            if(contentDTO.getDescription()!=null&&!contentDTO.getDescription().equals(content.getDescription())) {
                content.setDescription(contentDTO.getDescription());
            }
            if(contentDTO.getResourceUrl()!=null&&!contentDTO.getResourceUrl().equals(content.getResourceUrl())) {
                content.setResourceUrl(contentDTO.getResourceUrl());
            }
            if(contentDTO.isAiGenerated()){
                content.setAiGenerated(true);
            }
            if(contentDTO.getFileSize()!=null&&!contentDTO.getFileSize().equals(content.getFileSize())) {
                content.setFileSize(contentDTO.getFileSize());
            }
            if(contentDTO.getFileType()!=null&&!contentDTO.getFileType().equals(content.getFileType())) {
                content.setFileType(contentDTO.getFileType());
            }
            if(contentDTO.getScheduledAt()!=null&&!contentDTO.getScheduledAt().equals(content.getScheduledAt())) {
                content.setScheduledAt(contentDTO.getScheduledAt());
            }
            if(contentDTO.getModule()!=null&&!contentDTO.getModule().equals(content.getModule())) {
              Module module = moduleService.getModule(contentDTO.getModule().getId());
              if(module==null) {
                  throw new ResourceNotFoundException("Module",contentDTO.getModule().getId(),new Timestamp(System.currentTimeMillis()));
              }
              content.setModule(module);
            }
            return contentRepository.save(content);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), getStackTrace(e),new Timestamp(System.currentTimeMillis()));
        }
    }
    public Content getById(String id) {
        try{
          Content content = contentRepository.findById(id).orElse(null);
          if(content==null) {
              throw new RuntimeException("Content not found");
          }
          return content;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    public Content getById(String id,String userId,String enrollmentId) {

        try{
        Content content = contentRepository.findById(id).orElse(null);
        if(content==null) {
            throw new ResourceNotFoundException("Content",id,new Timestamp(System.currentTimeMillis()));
        }
        boolean permission = authoriseReadContent(content.getCourseId(),userId,enrollmentId);
        if(!permission) {
            throw new UnauthorisedException("Course",content.getCourseId(),"READ");
        }
        return content;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), getStackTrace(e),new Timestamp(System.currentTimeMillis()));
        }
    }
    public Page<Content> getContents(String courseId, String userId, String enrollmentId, Pageable pageable, Map<String,String> filters) {
        try {
            boolean permission = authoriseReadContent(courseId,userId,enrollmentId);
            if(!permission) {
                throw new UnauthorisedException("Course",courseId,"READ");
            }
//            ContentSpecification specification = new ContentSpecification();
            SpecificationFactory<Content> factory = new ContentSpecificationFactory();
            SpecificationUtils<Content> utils = new SpecificationUtils<>(factory);
//            Specification<Content>
            Page<Content> contentPage = contentRepository.findAll(utils.getSpecifications(filters),pageable);
            return contentPage;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(e.getMessage(), getStackTrace(e),new Timestamp(System.currentTimeMillis()));
        }
    }
    public boolean deleteContent(String id,String userId){
        try{
            Content content = contentRepository.findById(id).orElse(null);
            boolean permission = authoriseCourseOwner(content.getCourseId(),userId);
            if(!permission) {
                throw new UnauthorisedException("Course",content.getCourseId(),"OWNER");
            }
            if(content==null) {
                throw new ResourceNotFoundException("Content",id,new Timestamp(System.currentTimeMillis()));
            }
            contentRepository.delete(content);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    private static String getStackTrace(Exception e) {
        return Arrays.toString(e.getStackTrace());
    }



    public boolean authoriseCourseOwner(String courseId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("action","WRITE");
        queryMap.put("resourceName","course."+courseId);
        return authorisation.authorise(queryMap);
    }
    public boolean authoriseReadContent(String courseId,String userId,String enrollmentId){
        HashMap<String,String> queryMap=new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("enrollmentId",enrollmentId);
        queryMap.put("action","READ");
        queryMap.put("resourceName","course."+courseId);
        return authorisation.authorise(queryMap);
    }
}
