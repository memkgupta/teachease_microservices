package org.teachease.courseservice.filtering.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.teachease.courseservice.entities.Content;
import org.teachease.courseservice.entities.Module;
import org.teachease.courseservice.filtering.SpecificationFunction;
import org.teachease.courseservice.filtering.SpecificationInterface;

public class ContentSpecification implements SpecificationInterface<Content> {

    public Specification<Content> moduleEquals(String field,String value){
        return ((root, query, criteriaBuilder) ->
        {

                Join<Content, Module> join = root.join("module");
                return criteriaBuilder.equal(join.get("id"), value);


        });
    }

}
