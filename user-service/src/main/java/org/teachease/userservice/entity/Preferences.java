package org.teachease.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Preferences {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Preferences(String id, String userId, String theme, boolean emailNotification, boolean smsNotification, boolean whatsappNotification) {
        this.id = id;
        this.userId = userId;
        this.theme = theme;
        this.emailNotification = emailNotification;
        this.smsNotification = smsNotification;
        this.whatsappNotification = whatsappNotification;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String theme;
    private boolean emailNotification;
    private boolean smsNotification;
    private boolean whatsappNotification;

    public Preferences() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public static Preferences getDefault(){
        Preferences preferences = new Preferences();
        preferences.setTheme("default");
        preferences.setEmailNotification(true);
        preferences.setSmsNotification(true);
        preferences.setWhatsappNotification(true);
        return preferences;
    }
}
