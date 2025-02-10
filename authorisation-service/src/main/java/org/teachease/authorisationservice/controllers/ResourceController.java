package org.teachease.authorisationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.authorisationservice.dtos.ResourceDTO;
import org.teachease.authorisationservice.services.ResourceService;

@RestController
@RequestMapping("/api/v1/authorisation/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping("/create")
    public ResponseEntity<ResourceDTO> createResource(@RequestBody final ResourceDTO resourceDTO) {
        return ResponseEntity.ok().body(resourceService.createResource(resourceDTO).dto());
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteResource(@RequestBody final ResourceDTO resourceDTO) {
        resourceService.deleteResource(resourceDTO.getId());
        return ResponseEntity.noContent().build();
    }
}
