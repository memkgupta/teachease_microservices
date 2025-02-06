package org.teachease.courseservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.teachease.courseservice.entities.ModuleList;
import org.teachease.courseservice.entities.TestDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleDTO {
    private String id;
    private String title;
    private String description;

    private String courseId;
private ModuleDTO parent;
    private int priority;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    private List<AssignmentDTO> assignments = new ArrayList<AssignmentDTO>();
    private List<NotesDTO> notes = new ArrayList<>();
    private List<TestDTO> tests = new ArrayList<>();
    private List<ModuleDTO> subModules = new ArrayList<>();



}
