package org.teachease.authorisationservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.teachease.authorisationservice.dtos.RelationDTO;

import java.sql.Timestamp;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id",nullable = false)
    private ResourceEntity resource;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    private Timestamp created;
    private String relatedResourceId;
    public RelationDTO dto(){
        RelationDTO dto = RelationDTO.builder()
                .resourceId(resource.getId())
                .userId(user.getId())
                .description(description)
                .name(name)
                .created(created)
                .id(id)
                .build();
        return dto;
    }
}
