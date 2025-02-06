package org.teachease.courseservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.teachease.courseservice.entities.ModuleList;

import java.sql.Timestamp;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    public CourseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    private String id = null;
    private String title;
    private String description;
    private String teacherId = null;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt = null;
    private ModuleListDTO moduleList;
    public ModuleListDTO getModule() {
        return moduleList;
    }

    public void setModule(ModuleListDTO module) {
        this.moduleList = module;
    }

    public CourseDTO(String title, String description, Timestamp startDate, Timestamp endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
