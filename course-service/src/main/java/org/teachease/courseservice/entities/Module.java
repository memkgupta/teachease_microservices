package org.teachease.courseservice.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.courseservice.dtos.ModuleDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    @ManyToOne(fetch = FetchType.LAZY)
    private Module parent;
    private int priority;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdAt;
    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Module> subModules;
    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;
    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes;

    public ModuleDTO getPartialDTO() {

        ModuleDTO partialDTO = new ModuleDTO();
        partialDTO.setTitle(title);
        partialDTO.setDescription(description);
        partialDTO.setId(id);
        partialDTO.setPriority(priority);
        partialDTO.setStartDate(startDate);
        partialDTO.setEndDate(endDate);
        partialDTO.setCreatedAt(createdAt);

        return partialDTO;
    }
    public ModuleDTO getDTO() {
        ModuleDTO moduleDTO = ModuleDTO.builder()
                .title(title)
                .description(description)
                .id(id)
                .priority(priority)
                .startDate(startDate)
                .parent(ModuleDTO.builder().title(getParent().title).id(getParent().id).build())
                .endDate(endDate)
                .createdAt(createdAt)
                .subModules(getSubModules().stream().map(Module::getPartialDTO).toList())
                .assignments(getAssignments().stream().map(Assignment::toPartialDTO).toList())
                .notes(getNotes().stream().map(Notes::getPartialDTO).toList())

                .build();
        return moduleDTO;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       Module node = (Module) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
