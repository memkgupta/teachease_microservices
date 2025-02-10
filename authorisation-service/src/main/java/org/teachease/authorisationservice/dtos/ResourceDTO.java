package org.teachease.authorisationservice.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teachease.authorisationservice.entities.User;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceDTO {
    private String id;
    private String resourceId;
    private String resourceName;
    private String resourceType;
    private String owner;
    private Timestamp created;

}
