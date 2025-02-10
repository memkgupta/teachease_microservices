package org.teachease.courseservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.teachease.courseservice.dtos.authorisation.RelationDTO;
import org.teachease.courseservice.dtos.authorisation.ResourceDTO;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Authorisation {
    private final RestTemplate restTemplate = new RestTemplate();

    public void addRelation(RelationDTO relation) {
        try{
            HttpHeaders headers = new HttpHeaders();
            String url = "http://localhost:9090/relations/create";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RelationDTO> request = new HttpEntity<>(relation, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to add relation");
        }
    }
    public void deleteRelation(RelationDTO relation) {
        try {

            HttpHeaders headers = new HttpHeaders();
            String url = "http://localhost:8001/api/v1/authorisation/relations/delete";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RelationDTO> request = new HttpEntity<>(relation, headers);
            restTemplate.delete(url, request);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to delete relation");
        }
    }
    public void updateRelation(RelationDTO relation) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String url = "http://localhost:8001/api/v1/authorisation/relations/update";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RelationDTO> request = new HttpEntity<>(relation, headers);
           restTemplate.patchForObject(url, request, Map.class);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to update relation");
        }
    }
    public void addResource(ResourceDTO resource) {
        try{
            HttpHeaders headers = new HttpHeaders();
            String url = "http://localhost:8001/api/v1/authorisation/resources/create";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ResourceDTO> request = new HttpEntity<>(resource, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to add resource");
        }
    }
    public void deleteResource(ResourceDTO resource) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String url = "http://localhost:8001/api/v1/authorisation/resources/delete";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ResourceDTO> request = new HttpEntity<>(resource, headers);
            restTemplate.delete(url, request);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to delete resource");
        }
    }
    public boolean authorise(HashMap<String, String> map) {
        try{
            HttpHeaders headers = new HttpHeaders();
            String url = "http://localhost:8001/api/v1/authorisation/permissions/check";
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<HashMap> request = new HttpEntity<>(map, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
//            System.out.println();
            return response.getStatusCode() == HttpStatus.NO_CONTENT;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to authorise");
        }
    }
}
