package org.teachease.courseservice.filtering;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface SpecificationFunction<T> {
    public Specification<T> getSpecification(String field, String value);

}
