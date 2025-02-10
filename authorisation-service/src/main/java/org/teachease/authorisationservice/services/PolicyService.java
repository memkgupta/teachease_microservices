package org.teachease.authorisationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teachease.authorisationservice.config.JsonNodeConverter;
import org.teachease.authorisationservice.dtos.PolicyDTO;
import org.teachease.authorisationservice.entities.Policy;
import org.teachease.authorisationservice.repositories.PolicyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final JsonNodeConverter jsonNodeConverter;
    public Policy createPolicy(PolicyDTO policyDTO) {
        Policy policy = Policy.builder()
                .name(policyDTO.getName())
                .description(policyDTO.getDescription())
                .conditions(policyDTO.getConditions())
                .resourceName(policyDTO.getResourceName())
                .action(policyDTO.getAction())
                .build();

        return policyRepository.save(policy);

    }

    public List<Policy> getPolicyByResourceNameAndAction(String resourceName, String action) {
        return policyRepository.findByResourceNameAndAction(resourceName,action);
    }
    public PolicyDTO updatePolicy(PolicyDTO policyDTO) {
        Policy policy = policyRepository.findById(policyDTO.getId()).get();
        if(policy==null){
            throw new RuntimeException("Policy not found");
        }
        if(policyDTO.getDescription()!=null){
            policy.setDescription(policyDTO.getDescription());
        }
        if(policyDTO.getConditions()!=null){
            policy.setConditions(policyDTO.getConditions());
        }
        if(policyDTO.getResourceName()!=null){
            policy.setResourceName(policyDTO.getResourceName());
        }
        if(policyDTO.getAction()!=null){
            policy.setAction(policyDTO.getAction());
        }
        policyRepository.save(policy);
        return policy.dto();
    }
    public boolean deletePolicy(PolicyDTO policyDTO) {
        Policy policy = policyRepository.findById(policyDTO.getId()).get();
        if(policy==null){
            throw new RuntimeException("Policy not found");
        }
        policyRepository.delete(policy);
        return true;
    }
}
