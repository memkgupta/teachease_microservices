package org.teachease.userservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.teachease.userservice.dtos.PreferenceDTO;
import org.teachease.userservice.services.PreferenceService;

@RestController
@RequestMapping("/preference")
public class PreferenceController {
    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PutMapping
    public PreferenceDTO updatePreference(@RequestBody PreferenceDTO preferenceDTO,HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        preferenceDTO.setUserId(userId);
        return preferenceService.updatePreference(userId, preferenceDTO);
    }
    @GetMapping
    public PreferenceDTO getPreference(HttpServletRequest request) {
        String userId = request.getHeader("X-USER-ID");
        return preferenceService.getPreference(userId);

    }
}
