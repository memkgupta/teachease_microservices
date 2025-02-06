package org.teachease.courseservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.teachease.courseservice.dtos.ModuleDTO;
import org.teachease.courseservice.dtos.ModuleListDTO;

@Entity
@Table(name = "module_linked_list")
@Data
public class ModuleList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(cascade = {}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Module head;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    private CourseEntity course;
    @OneToOne(cascade = {}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Module tail;
    private int size;
    public ModuleListDTO toDTO() {
        ModuleListDTO dto = new ModuleListDTO();
        dto.setId(id);
        if(head != null) {
            dto.setHead(getHead().getPartialDTO());

        }
       else{
           dto.setHead(null);

        }
        return dto;
    }



}
