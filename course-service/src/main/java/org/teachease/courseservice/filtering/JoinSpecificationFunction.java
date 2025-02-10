package org.teachease.courseservice.filtering;

import org.springframework.data.jpa.domain.Specification;
@FunctionalInterface
public interface JoinSpecificationFunction <T> extends SpecificationFunction{
    public <Q> Specification<T> getSpecification(String field, String value, String mappedColumn, Class<Q> clazz);
}
