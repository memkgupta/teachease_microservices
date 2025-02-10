package org.teachease.authorisationservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.authorisationservice.dtos.RelationDTO;
import org.teachease.authorisationservice.services.RelationService;

@RestController
@RequestMapping("/api/v1/authorisation/relations")
public class RelationController {
    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @PostMapping("/create")
    public ResponseEntity<RelationDTO> createRelation(@RequestBody RelationDTO relationDTO) {
        System.out.println(relationDTO);
        return ResponseEntity.ok().body(relationService.createRelation(relationDTO,relationDTO.getResource()));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteRelation(@RequestBody RelationDTO relationDTO) {
        relationService.deleteRelation(relationDTO);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/update")
    public ResponseEntity<RelationDTO> updateRelation(@RequestBody RelationDTO relationDTO) {
        return ResponseEntity.ok(relationService.updateRelation(relationDTO).dto());
    }


}
