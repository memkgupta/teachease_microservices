package org.teachease.authorisationservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.authorisationservice.dtos.ResourceDTO;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String resourceId;
    private String resourceName;
    private String resourceType;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "resource")
    private Set<RelationEntity> relations;
    private Timestamp created;

    public ResourceDTO dto() {
        return ResourceDTO.builder()
                .owner(this.getOwner().getId())
                .resourceType(this.getResourceType())
                .resourceId(this.getResourceId())
                .resourceName(this.getResourceName())
                .build();
    }
}
