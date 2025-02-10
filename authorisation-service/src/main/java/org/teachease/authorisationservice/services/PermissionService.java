package org.teachease.authorisationservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teachease.authorisationservice.entities.Policy;
import org.teachease.authorisationservice.entities.RelationEntity;
import org.teachease.authorisationservice.entities.ResourceEntity;
import org.teachease.authorisationservice.entities.User;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final UserService userService;
    private final ResourceService resourceService;
    private final RelationService relationService;
    private final PolicyService policyService;

    public boolean checkPermission(String resourceName, String action, String userId, String extraInfo) {
        // Split resourceName into resourceType and resourceId
        String[] parts = resourceName.split("\\.");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid resource name format. Expected format: <resourceType>.<resourceId>");
        }
        String resourceType = parts[0];
        String resourceId = parts[1];

        // Fetch policies for the resource and action
        List<Policy> policies = policyService.getPolicyByResourceNameAndAction(resourceType, action);
        if (policies == null || policies.isEmpty()) {
            return false; // No policies found, deny access
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode conditions = mapper.readTree(policies.get(0).getConditions());
            return evaluateConditions(conditions, userId, resourceId, extraInfo);
        } catch (Exception e) {
            throw new RuntimeException("Failed to evaluate conditions: " + e.getMessage(), e);
        }
    }

    private boolean evaluateConditions(JsonNode conditions, String userId, String resourceId, String extraInfo) {
        System.out.println(conditions);
        if (conditions.has("$AND")) {
            return evaluateAndOrConditions(conditions.get("$AND"), userId, resourceId, extraInfo, "$AND");
        } else if (conditions.has("$OR")) {
            return evaluateAndOrConditions(conditions.get("$OR"), userId, resourceId,extraInfo, "$OR");
        }
        return false; // No valid conditions found
    }

    private boolean evaluateAndOrConditions(JsonNode conditions, String userId,String resourceId, String extraInfo, String conditionType) {

        List<Boolean> results = new ArrayList<>();

      conditions.forEach(condition -> {
          condition.fields().forEachRemaining(field -> {
              String key = field.getKey();
              JsonNode value = field.getValue();
ResourceEntity resource = resourceService.getResource(resourceId);
              if (key.equals("$AND") || key.equals("$OR")) {
                  results.add(evaluateAndOrConditions(value, userId, resourceId,extraInfo, key));
              } else {
                  results.add(evaluateCondition(value.asText(), userId, resource, extraInfo));
              }
          });

      });




        if (conditionType.equals("$AND")) {
            return results.stream().allMatch(Boolean::booleanValue);
        } else if (conditionType.equals("$OR")) {

            return results.stream().anyMatch(Boolean::booleanValue);
        }
        return false;
    }

    private boolean evaluateCondition(String condition, String userId, ResourceEntity resource, String referenceVal) {

        if (resource == null) {
            return false; // Resource not found
        }

        List<RelationEntity> relations = relationService.getAllRelationsOfResource(resource);
        User user = userService.getOrCreateUser(userId);

        if (relations == null || relations.isEmpty()) {
            return false; // No relations found
        }
        if (condition.startsWith("Reference")) {
            String subQuery = condition.substring(10, condition.length() - 1); // Extract subquery
             subQuery.replace("{ref_id}", referenceVal).replace("{user_id}", userId); // Replace placeholders
            String[] subQueryParts = subQuery.split("#");
            if (subQueryParts.length != 2) {
                throw new IllegalArgumentException("Invalid subquery format. Expected format: <resourceType>.<resourceId>#<relation>");
            }
            System.out.println("Subquery is "+subQuery);
            String subResourceId = referenceVal;
            String relation = subQueryParts[1];
            ResourceEntity subResource = resourceService.getResource(subResourceId);
            boolean subQueryResult = evaluateSubQuery(subResource, "OWNER", userId);
            System.out.println(subResourceId+"=>"+relation);
            if (subQueryResult) {
                return relations.stream().anyMatch(r->{
                    return r.getName().equals(relation)&& r.getUser().getUserId().equals(subResourceId);
                });
            }
            else return false;
        }

        ;
        return relations.stream()
                .anyMatch(relation -> {
                    System.out.println("Seperator \n");
                    System.out.println(relation.getUser());
                    System.out.println(relation.getName());
                    System.out.println(user);
                    return relation.getName().equals(condition.split("@")[0]) && relation.getUser().getId().equals(user.getId());
                });
    }

    private boolean evaluateSubQuery(ResourceEntity resource, String relation, String userId) {

        if (resource == null) {
            return false; // Resource not found
        }

        List<RelationEntity> relations = relationService.getAllRelationsOfResource(resource);
        if (relations == null || relations.isEmpty()) {
            return false; // No relations found
        }

        return relations.stream()
                .anyMatch(relationEntity -> relationEntity.getName().equals(relation) && relationEntity.getUser().getUserId().equals(userId));
    }
}