package org.teachease.authorisationservice.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teachease.authorisationservice.dtos.CheckPermissionDTO;
import org.teachease.authorisationservice.services.PermissionService;

@RestController
@RequestMapping("/api/v1/authorisation/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody CheckPermissionDTO permissionDTO) {

        boolean isPermitted = permissionService.checkPermission(permissionDTO.getResourceName(),permissionDTO.getAction(),permissionDTO.getUserId(),permissionDTO.getExtraInfo());

        if(isPermitted) {
            return ResponseEntity.noContent().build();
        }
       return ResponseEntity.status(403).body(new CheckPermissionDTO());
    }
}
