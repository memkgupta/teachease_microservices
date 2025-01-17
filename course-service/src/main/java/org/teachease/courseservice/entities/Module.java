package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import org.teachease.courseservice.dtos.ModuleDTO;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "next_module")
    private Module nextModule;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prev_module")
    private Module prevModule;
    private int priority;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;
    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes;


    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
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

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public Module getNextModule() {
        return nextModule;
    }

    public void setNextModule(Module nextModule) {
        this.nextModule = nextModule;
    }

    public Module getPrevModule() {
        return prevModule;
    }

    public void setPrevModule(Module prevModule) {
        this.prevModule = prevModule;
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
    public ModuleDTO getPartialDTO() {

        ModuleDTO partialDTO = new ModuleDTO();

        partialDTO.setTitle(title);
        partialDTO.setDescription(description);
        partialDTO.setId(id);
        partialDTO.setPriority(priority);
        partialDTO.setStartDate(startDate);
        partialDTO.setEndDate(endDate);
        partialDTO.setCreatedAt(createdAt);
        ModuleDTO headDTO = new ModuleDTO();
        headDTO.setNext(partialDTO);
        Module head = this;
//        Module temp = this.nextModule;
        while (head != null) {
           ModuleDTO nextDTO = new ModuleDTO();
           nextDTO.setId(head.getId());
           nextDTO.setTitle(head.getTitle());
           nextDTO.setDescription(head.getDescription());
           nextDTO.setPriority(head.getPriority());
           nextDTO.setStartDate(head.getStartDate());
           nextDTO.setEndDate(head.getEndDate());
           nextDTO.setCreatedAt(head.getCreatedAt());
           nextDTO.setPrevious(partialDTO);
           partialDTO.setNext(nextDTO);
           partialDTO = partialDTO.getNext();
            head = head.getNextModule();
        }
        return headDTO.getNext();
    }
}
