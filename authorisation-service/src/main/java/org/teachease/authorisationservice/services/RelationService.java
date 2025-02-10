package org.teachease.authorisationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teachease.authorisationservice.dtos.RelationDTO;
import org.teachease.authorisationservice.dtos.ResourceDTO;
import org.teachease.authorisationservice.entities.RelationEntity;
import org.teachease.authorisationservice.entities.ResourceEntity;
import org.teachease.authorisationservice.repositories.RelationRepository;
import org.teachease.authorisationservice.repositories.ResourceRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelationService {
    private final RelationRepository relationRepository;
    private final ResourceRepository resourceRepository;
    private final ResourceService resourceService;
    private final UserService userService;

    public RelationDTO createRelation(RelationDTO relationDTO, ResourceDTO resourceDTO) {
        RelationEntity relationEntity = new RelationEntity();
        Optional<ResourceEntity> resourceEntityOptional = resourceRepository.findByResourceId(relationDTO.getResourceId());
        ResourceEntity resourceEntity = null;
        if(resourceEntityOptional.isEmpty() && resourceDTO==null) {
            throw new RuntimeException("Resource not found");
        }
        else resourceEntity = resourceEntityOptional.orElseGet(() -> resourceService.createResource(resourceDTO));
        relationEntity.setName(relationDTO.getName());
       relationEntity.setResource(resourceEntity);
       relationEntity.setCreated(new Timestamp(System.currentTimeMillis()));
       relationEntity.setUser(userService.getOrCreateUser(relationDTO.getUserId()));
       relationEntity.setDescription(relationDTO.getDescription());
       relationRepository.save(relationEntity);
       return relationEntity.dto();
    }
    public List<RelationEntity> getAllRelationsOfResource(ResourceEntity resourceEntity) {
try{
    List<RelationEntity> relationEntities = relationRepository.findByResource(resourceEntity);

    relationEntities.stream().forEach(relationEntity -> {
        System.out.println(relationEntity.getDescription());
    });
    return relationEntities;
} catch (Exception e) {
    e.printStackTrace();
    throw new RuntimeException(e);
}

    }
    public void deleteRelation(RelationDTO relationDTO) {
        Optional<RelationEntity> relationEntityOptional = relationRepository.findById(relationDTO.getId());
        if(!relationEntityOptional.isPresent()) {
            throw new RuntimeException("Relation not found");
        }
        else {
            RelationEntity relationEntity = relationEntityOptional.get();
            relationRepository.delete(relationEntity);
        }
    }
public RelationEntity updateRelation(RelationDTO relationDTO) {
        Optional<RelationEntity> relationEntityOptional = relationRepository.findById(relationDTO.getId());
        if(!relationEntityOptional.isPresent()) {
            throw new RuntimeException("Relation not found");
        }
        else {
            RelationEntity relationEntity = relationEntityOptional.get();
            if(relationDTO.getDescription()!=null) {
                relationEntity.setDescription(relationDTO.getDescription());
            }
            if(relationDTO.getName()!=null) {
                relationEntity.setName(relationDTO.getName());
            }
            if(relationDTO.getResourceId()!=null && !relationDTO.getResourceId().equals(relationEntity.getResource().getResourceId())) {
                Optional<ResourceEntity> resource = resourceRepository.findByResourceId(relationDTO.getResourceId());
                if(resource.isPresent()) {
                    relationEntity.setResource(resource.get());
                }
                else {
                    throw new RuntimeException("Resource not found");
                }
            }
            return relationRepository.save(relationEntity);
        }
}
public RelationEntity getRelationByNameAndResource(String name,String resourceEntityId) {
        Optional<ResourceEntity> resourceEntity = resourceRepository.findById(resourceEntityId);
        if(resourceEntity.isPresent()) {
            throw new RuntimeException("Relation not found");
        }
        Optional<RelationEntity> relationEntityOptional = relationRepository.findByResourceAndName(resourceEntity.get(),name);
        if(relationEntityOptional.isPresent()) {
            return relationEntityOptional.get();
        }
        throw new RuntimeException("Relation not found");
    }
}
