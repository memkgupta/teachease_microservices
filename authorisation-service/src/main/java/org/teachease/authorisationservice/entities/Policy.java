package org.teachease.authorisationservice.entities;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.teachease.authorisationservice.config.JsonNodeConverter;
import org.teachease.authorisationservice.dtos.PolicyDTO;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String action;
    private String description;


    private String conditions;
  private String resourceName;

    public PolicyDTO dto() {
        return PolicyDTO.builder()
                .id(this.id)
                .name(this.name)
                .action(this.action)
                .description(this.description)
                .resourceName(this.resourceName)
                .build();
    }
}
