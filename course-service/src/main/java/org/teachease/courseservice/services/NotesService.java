package org.teachease.courseservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.NotesDTO;
import org.teachease.courseservice.dtos.authorisation.ResourceDTO;
import org.teachease.courseservice.entities.CourseEntity;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.entities.Notes;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.specificationFactories.NotesSpecificationFactory;
import org.teachease.courseservice.filtering.specificationFactories.SpecificationUtils;
import org.teachease.courseservice.repositories.NotesRepository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;
    private final Authorisation authorisation;
    private final CourseService courseService;
    private final ModuleService moduleService;

    public Notes create(NotesDTO notesDTO,String userId) {
        try{
//            CourseEntity course = courseService.
                CourseEntity course = courseService.getCourse(notesDTO.getCourseId());
                if(course == null) {
                    throw new RuntimeException("Course not found");
                }
                boolean permission = authoriseAddNotes(course.getId(),userId);
                if(!permission) {
                    throw new RuntimeException("Permission denied");
                }
            Module module = moduleService.getModule(notesDTO.getModule().getId());
                if(module==null)
                {
                    throw new RuntimeException("Module not found");
                }
            Notes notes = Notes.builder()
                    .title(notesDTO.getTitle())
                    .courseId(course.getId())
                    .description(notesDTO.getDescription())
                    .resourceURL(notesDTO.getResourceURL())
                    .module(module)
                    .isAiGenerated(notesDTO.isAiGenerated())
                    .fileSize(notesDTO.getFileSize())
                    .fileType(notesDTO.getFileType())
                    .build();
                notes= notesRepository.save(notes);
                authorisation.addResource(
                        ResourceDTO.builder()
                                .created(new Timestamp(System.currentTimeMillis()))
                                .owner(userId)
                                .resourceType("notes")
                                .resourceId(notes.getId())
                                .resourceName("notes."+notes.getId())
                                .build()
                );
                return notes;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error creating notes");
        }
    }
    public Notes update(NotesDTO notesDTO, String userId) {
        try{
            Notes notes = notesRepository.findById(notesDTO.getId()).orElse(null);
            if(notes == null) {
                throw new RuntimeException("Notes not found");
            }
            boolean permission = authoriseUpdateNotes(notes.getId(),userId);
            if(!permission) {
                throw new RuntimeException("Permission denied");
            }
            notes.setTitle(notesDTO.getTitle());
            notes.setDescription(notesDTO.getDescription());
            notes.setResourceURL(notesDTO.getResourceURL());
            if(notesDTO.getModule()!=null && notesDTO.getModule().getId()!=null) {
                Module module = moduleService.getModule(notesDTO.getModule().getId());
                if(module==null)
                {
                    throw new RuntimeException("Module not found");
                }
                notes.setModule(module);
            }
            notes.setFileSize(notesDTO.getFileSize());
            notes.setFileType(notesDTO.getFileType());
            return notesRepository.save(notes);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error updating notes");
        }
    }
    public void delete(String id, String userId) {
        try {
            Notes notes = notesRepository.findById(id).orElse(null);
            if(notes == null) {
                throw new RuntimeException("Notes not found");
            }
            boolean permission = authoriseDeleteNotes(notes.getId(),userId);
            if(!permission) {
                throw new RuntimeException("Permission denied");
            }
            notesRepository.delete(notes);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error deleting notes");
        }
    }
    public Page<Notes> getNotes(String courseId, String userId, String enrollmentId, Pageable pageable, Map<String,String> filters) {
        try{
            CourseEntity course = courseService.getCourse(courseId);
            if(course == null) {
                throw new RuntimeException("Course not found");
            }

            boolean permission = authoriseGetNotes(course.getId(),userId,enrollmentId);
            if(!permission) {
                throw new RuntimeException("Permission denied");
            }
            SpecificationFactory<Notes> specificationFactory = new NotesSpecificationFactory();
            SpecificationUtils<Notes> specificationUtils = new SpecificationUtils<>(specificationFactory);
            Specification<Notes> specification = specificationUtils.getSpecifications(filters);
            Page<Notes> notes = notesRepository.findAll(specification, pageable);
            return notes;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error getting notes");
        }
    }
    public Notes getNoteById(String id,String userId,String enrollmentId) {
        try {
            Notes notes = notesRepository.findById(id).orElse(null);
            if(notes == null) {
                throw new RuntimeException("Notes not found");
            }
            boolean permission = authoriseGetNotes(notes.getId(),userId,enrollmentId);
            if(!permission) {
                throw new RuntimeException("Permission denied");
            }
            return notes;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error getting notes");
        }
    }
    public boolean authoriseAddNotes(String courseId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("action","WRITE");
        queryMap.put("resourceName","course."+courseId);
        return authorisation.authorise(queryMap);

    }
    public boolean authoriseUpdateNotes(String notesId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",notesId);
        queryMap.put("userId",userId);
        queryMap.put("action","WRITE");
        queryMap.put("resourceName","notes."+notesId);
        return authorisation.authorise(queryMap);
    }
    public boolean authoriseDeleteNotes(String notesId,String userId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",notesId);
        queryMap.put("userId",userId);
        queryMap.put("action","DELETE");
        queryMap.put("resourceName","notes."+notesId);
        return authorisation.authorise(queryMap);
    }
    public boolean authoriseGetNotes(String courseId,String userId,String enrollmentId){
        HashMap<String,String> queryMap = new HashMap<>();
        queryMap.put("resourceId",courseId);
        queryMap.put("userId",userId);
        queryMap.put("action","READ");
        queryMap.put("resourceName","course."+courseId);
        queryMap.put("extraInfo",enrollmentId);
        return authorisation.authorise(queryMap);
    }
//    public boolean
//    public void createN
}
