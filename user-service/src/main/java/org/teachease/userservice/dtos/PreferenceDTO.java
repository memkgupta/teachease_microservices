package org.teachease.userservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teachease.userservice.entity.Preferences;

public class PreferenceDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("user_id")
private String userId;
    @JsonProperty("theme")
    private String theme;
    @JsonProperty("email_notifications")
    private boolean emailNotification;
    @JsonProperty("sms_notifications")
    private boolean smsNotification;
    @JsonProperty("whatsapp_notifications")
    private boolean whatsappNotification;
    public static PreferenceDTO fromPreference(Preferences preferences) {
       PreferenceDTO preferenceDTO = new PreferenceDTO();
       preferenceDTO.emailNotification = preferences.isEmailNotification();
       preferenceDTO.smsNotification = preferences.isSmsNotification();
       preferenceDTO.whatsappNotification = preferences.isWhatsappNotification();
       preferenceDTO.id = preferences.getId();
       preferenceDTO.userId = preferences.getUserId();
       preferenceDTO.theme = preferences.getTheme();
       return preferenceDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public boolean isSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(boolean smsNotification) {
        this.smsNotification = smsNotification;
    }

    public boolean isWhatsappNotification() {
        return whatsappNotification;
    }

    public void setWhatsappNotification(boolean whatsappNotification) {
        this.whatsappNotification = whatsappNotification;
    }
}
