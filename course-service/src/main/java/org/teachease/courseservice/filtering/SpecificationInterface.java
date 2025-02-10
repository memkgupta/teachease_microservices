package org.teachease.courseservice.filtering;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationInterface<T> {
    default Specification<T> containsIgnoreCase(String field,String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)),"%"+value.toLowerCase()+"%"));
    }
    default Specification<T> startsWithIgnoreCase(String field, String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field),value.toLowerCase()+"%"));
    }
    default Specification<T> endsWithIgnoreCase(String field,String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field),value.toLowerCase()+"%"));
    }
    default Specification<T> startsWith(String field,String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field),value+"%"));
    }
    default Specification<T> endsWith(String field,String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field),value+"%"));
    }
    default Specification<T> exactMatch(String field,String value){
        return((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(field),value));
    }
    default Specification<T> isTrue(String field,String value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get(field)));
    }
//    default Spe
    default <Q> Specification<T> joinIdMatch(String field,String value,String mappedColumn,Class<Q> clazz){
        return ((root, query, criteriaBuilder) ->
        {
            Join<T,Q> join = root.join(field);

            return criteriaBuilder.equal(join.get(mappedColumn!=null?mappedColumn:"id"),value);
        }
                );
    }

}

