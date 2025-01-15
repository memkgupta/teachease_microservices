package org.teachease.userservice.services;

import org.springframework.stereotype.Service;
import org.teachease.userservice.dtos.PreferenceDTO;
import org.teachease.userservice.entity.Preferences;
import org.teachease.userservice.repositories.PreferenceRepository;

@Service
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final UserService userService;

    public PreferenceService(PreferenceRepository preferenceRepository, UserService userService) {
        this.preferenceRepository = preferenceRepository;
        this.userService = userService;
    }

    public PreferenceDTO getPreference(String userId) {
        try{

            Preferences preferences = preferenceRepository.findByUserId(userId);
            if(preferences == null){
            return PreferenceDTO.fromPreference(Preferences.getDefault());
            }
            return PreferenceDTO.fromPreference(preferences);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error");
        }
    }
    public PreferenceDTO updatePreference(String userId, PreferenceDTO preferenceDTO) {
        Preferences preferences = preferenceRepository.findByUserId(userId);
        preferences.setTheme(preferenceDTO.getTheme());
        preferences.setEmailNotification(preferenceDTO.isEmailNotification());
        preferences.setWhatsappNotification(preferenceDTO.isWhatsappNotification());
        preferences.setSmsNotification(preferenceDTO.isSmsNotification());
        preferenceRepository.save(preferences);
        return PreferenceDTO.fromPreference(preferences);
    }

}
