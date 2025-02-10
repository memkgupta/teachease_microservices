package org.teachease.authorisationservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teachease.authorisationservice.dtos.PolicyDTO;
import org.teachease.authorisationservice.services.PolicyService;

@RestController
@RequestMapping("/api/v1/authorisation/policy")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping("/create")
    public ResponseEntity<PolicyDTO> create(@RequestBody final PolicyDTO policyDTO) {
        return ResponseEntity.ok(policyService.createPolicy(policyDTO).dto());
    }
    @PatchMapping("/update")
    public ResponseEntity<PolicyDTO> update(@RequestBody final PolicyDTO policyDTO) {
        return ResponseEntity.ok(policyService.updatePolicy(policyDTO));
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody final PolicyDTO policyDTO) {
        policyService.deletePolicy(policyDTO);
        return ResponseEntity.noContent().build();
    }

}
