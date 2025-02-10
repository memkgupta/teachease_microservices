package org.teachease.courseservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.teachease.courseservice.entities.ModuleList;

import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    public CourseDTO() {
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
