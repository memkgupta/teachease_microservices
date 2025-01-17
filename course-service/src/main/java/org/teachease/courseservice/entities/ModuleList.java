package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.ModuleListDTO;

@Entity
@Table(name = "module_linked_list")
public class ModuleList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Module head;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private CourseEntity course;

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Module getHead() {
        return head;
    }

    public void setHead(Module head) {
        this.head = head;
    }
    public ModuleListDTO toDTO() {
        ModuleListDTO dto = new ModuleListDTO();
        dto.setHead(getHead().getPartialDTO());
        return dto;
    }
    public ModuleListDTO toDTO(int limit) {
        ModuleListDTO dto = new ModuleListDTO();
        ModuleDTO moduleDTO = new ModuleDTO();

        ModuleDTO head = getHead().getPartialDTO();
        moduleDTO.setNext(head);
        int temp = limit;
        while( temp> 0) {
            head = head.getNext();
            temp--;
        }
        head.setNext(null);
        dto.setHead(moduleDTO.getNext());
        return dto;
    }
}
