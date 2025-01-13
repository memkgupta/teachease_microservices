package org.teachease.userservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
