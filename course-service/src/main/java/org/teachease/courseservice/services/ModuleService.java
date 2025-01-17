package org.teachease.courseservice.services;

import org.springframework.stereotype.Service;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.ModuleListDTO;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.entities.ModuleList;
import org.teachease.courseservice.repositories.ModuleNodeRepository;
import org.teachease.courseservice.repositories.ModuleRepository;

import java.sql.Timestamp;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleNodeRepository moduleNodeRepository;
    public ModuleService(ModuleRepository moduleRepository, ModuleNodeRepository moduleNodeRepository) {
        this.moduleRepository = moduleRepository;

        this.moduleNodeRepository = moduleNodeRepository;
    }

    public ModuleListDTO getModules(String courseId,int limit,int page) {
        int offset = (page - 1) * limit;
        try{
            ModuleList moduleList = moduleRepository.findByCourse(courseId).orElse(null);
            if(moduleList == null) {
                throw new RuntimeException("No module found for course " + courseId);
            }
            while (offset>0){
                moduleList.setHead(moduleList.getHead().getNextModule());
                offset--;
            }
            return moduleList.toDTO(limit);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    public ModuleDTO addModule(ModuleDTO moduleDTO) {
        try {
            ModuleList moduleList = moduleRepository.findById(moduleDTO.getId()).orElse(null);
            if(moduleList == null) {
                throw new RuntimeException("No module found for id " + moduleDTO.getId());
            }
            Module module = new Module();
            module.setCourse(moduleList.getCourse());
            module.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            module.setDescription(moduleDTO.getDescription());
            module.setTitle(moduleDTO.getTitle());
            module.setPrevModule(moduleList.getHead());
            module.setEndDate(moduleDTO.getEndDate());
            module.setStartDate(moduleDTO.getStartDate());
            moduleNodeRepository.save(module);
            if(moduleList.getHead()==null){
                moduleList.setHead(module);

            }
moduleRepository.save(moduleList);
            moduleDTO.setId(module.getId());
            return moduleDTO;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
    public ModuleDTO updateModule(ModuleDTO moduleDTO) {
        try{
            Module module = moduleNodeRepository.findById(moduleDTO.getId()).orElse(null);
            if(module == null) {
                throw new RuntimeException("No module found for id " + moduleDTO.getId());
            }
            module.setDescription(moduleDTO.getDescription());
            module.setTitle(moduleDTO.getTitle());
            module.setEndDate(moduleDTO.getEndDate());
            module.setStartDate(moduleDTO.getStartDate());
            if(moduleDTO.getPrevious()!=null){
                Module prevModule = moduleNodeRepository.findById(moduleDTO.getPrevious().getId()).orElse(null);
                if(prevModule==null){
                    throw new RuntimeException("No previous module found for id " + moduleDTO.getPrevious().getId());
                }
                module.setPrevModule(prevModule);
                module.setNextModule(prevModule.getNextModule());
            }
            moduleNodeRepository.save(module);
            moduleDTO.setId(module.getId());
            moduleDTO.setDescription(moduleDTO.getDescription());
            moduleDTO.setTitle(moduleDTO.getTitle());
            moduleDTO.setEndDate(moduleDTO.getEndDate());
            moduleDTO.setStartDate(moduleDTO.getStartDate());
            return moduleDTO;
        }
        catch(Exception e){
e.printStackTrace();
throw new RuntimeException("Something went wrong");
        }
    }
    public boolean deleteModule(ModuleDTO moduleDTO) {
        try {
            Module module = moduleNodeRepository.findById(moduleDTO.getId()).orElse(null);
            if(module == null) {
                throw new RuntimeException("No module found for id " + moduleDTO.getId());
            }
            Module prevModule = module.getPrevModule();
            Module nextModule = module.getNextModule();
            if(prevModule!=null){
                prevModule.setNextModule(nextModule);
                moduleNodeRepository.save(prevModule);
            }
            if(nextModule!=null){
                nextModule.setPrevModule(prevModule);
                moduleNodeRepository.save(nextModule);
            }
            moduleNodeRepository.delete(nextModule);
           return true;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
}
