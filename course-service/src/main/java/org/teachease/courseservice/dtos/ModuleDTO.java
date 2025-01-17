package org.teachease.courseservice.dtos;

import org.teachease.courseservice.entities.ModuleList;
import org.teachease.courseservice.entities.TestDTO;

import java.sql.Timestamp;
import java.util.List;

public class ModuleDTO {
    private String id;
    private String title;
    private String description;
    private ModuleDTO next;
    private ModuleDTO previous;
    private int priority;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    private List<AssignmentDTO> assignments;
    private List<NotesDTO> notes;
    private List<TestDTO> tests;
    private ModuleListDTO subModules;

    public ModuleListDTO getSubModules() {
        return subModules;
    }

    public void setSubModules(ModuleListDTO subModules) {
        this.subModules = subModules;
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

    public ModuleDTO getNext() {
        return next;
    }

    public void setNext(ModuleDTO next) {
        this.next = next;
    }

    public ModuleDTO getPrevious() {
        return previous;
    }

    public void setPrevious(ModuleDTO previous) {
        this.previous = previous;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public List<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDTO> assignments) {
        this.assignments = assignments;
    }

    public List<NotesDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesDTO> notes) {
        this.notes = notes;
    }

    public List<TestDTO> getTests() {
        return tests;
    }

    public void setTests(List<TestDTO> tests) {
        this.tests = tests;
    }
}
