package org.teachease.authorisationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teachease.authorisationservice.dtos.ResourceDTO;
import org.teachease.authorisationservice.entities.Policy;
import org.teachease.authorisationservice.entities.ResourceEntity;
import org.teachease.authorisationservice.entities.User;
import org.teachease.authorisationservice.repositories.PolicyRepository;
import org.teachease.authorisationservice.repositories.ResourceRepository;
import org.teachease.authorisationservice.repositories.UserRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PolicyRepository policyRepository;

    public ResourceEntity createResource(ResourceDTO resourceDTO) {
        Optional<User> userOptional = userRepository.findById(resourceDTO.getOwner());
        User user = userOptional.orElse(
        userService.createUser(resourceDTO.getOwner())
        );
        Optional<ResourceEntity> resourceEntityOptional = resourceRepository.findByResourceId(resourceDTO.getResourceId());
       try{
           if(resourceEntityOptional.isPresent()) {
               throw new RuntimeException("Resource already exists!");
           }
           else{
               ResourceEntity resourceEntity = ResourceEntity.builder()
                       .owner(user)
                       .resourceId(resourceDTO.getResourceId())
                       .resourceType(resourceDTO.getResourceType())
                       .resourceName(resourceDTO.getResourceName())
                       .created(new Timestamp(System.currentTimeMillis()))
                       .build();

               return resourceRepository.save(resourceEntity);

           }

       }
       catch(Exception e){
           e.printStackTrace();
           throw new RuntimeException("Internal Server Error");
       }

    }
    public ResourceEntity getResource(String resourceId) {
        Optional<ResourceEntity> resourceEntityOptional = resourceRepository.findByResourceId(resourceId);
        if(resourceEntityOptional.isPresent()) {
            return resourceEntityOptional.get();
        }
        else{
           return null;
        }
    }
    public boolean deleteResource(String resourceId) {
        Optional<ResourceEntity> resourceEntityOptional = resourceRepository.findByResourceId(resourceId);
      if(resourceEntityOptional.isPresent()) {
          resourceRepository.delete(resourceEntityOptional.get());
      }
      throw new RuntimeException("Resource not found!");
    }

}
