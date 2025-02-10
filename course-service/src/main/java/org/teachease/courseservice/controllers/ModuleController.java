package org.teachease.courseservice.controllers;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.PageDTO;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.services.ModuleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/course/modules")
public class ModuleController {
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    @PostMapping("/add")
    public ModuleDTO addModule(@RequestBody ModuleDTO moduleDTO,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        return moduleService.addModule(moduleDTO, userId);
    }
    @GetMapping("")
    public ResponseEntity<PageDTO<ModuleDTO>> getAllModules(
            @RequestParam String courseId, @RequestParam int page, @RequestParam int limit, @RequestParam String sortOrder , @RequestParam String sortBy
    , @RequestParam @Nullable String parent, HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        Page<Module> modules = moduleService.getCourseModules(
                courseId,limit,page,sortOrder,sortBy,parent!=null?parent:null,userId
        );
        List<ModuleDTO> moduleDTOS = modules.getContent().stream().map((m)-> ModuleDTO.builder()
                .id(m.getId())
                .title(m.getTitle())
                .description(m.getDescription())
                .startDate(m.getStartDate())
                .endDate(m.getEndDate())
                .priority(m.getPriority()).build()).toList();

        PageDTO<ModuleDTO> moduleDTOs = new PageDTO<ModuleDTO>() {
            @Override
            public int getPageNumber() {
                return modules.getNumber();
            }

            @Override
            public int getPageSize() {
                return modules.getSize();
            }

            @Override
            public long getTotalElements() {
                return modules.getTotalElements();
            }

            @Override
            public List<ModuleDTO> getContent() {
                return moduleDTOS;
            }
        };
        return ResponseEntity.ok(moduleDTOs);
    }
    @PutMapping("/update")
    public ModuleDTO updateModule(@RequestBody ModuleDTO moduleDTO,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        return moduleService.updateModule(moduleDTO,userId);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteModule(@RequestBody ModuleDTO moduleDTO,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        moduleService.deleteModule(moduleDTO,userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable String id,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        return ResponseEntity.ok(moduleService.getModuleById(id,userId));
    }
}
