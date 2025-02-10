package org.teachease.courseservice.filtering.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.entities.Notes;
import org.teachease.courseservice.filtering.SpecificationInterface;

public class AssignmentSpecification implements SpecificationInterface<Assignment> {

    public static Specification<Assignment> titleStartsWith(String title) {
        return ((root, query, cb) ->
                cb.like(cb.lower(root.get("title")), title+"%"));
    }
    public Specification<Assignment> moduleEquals(String field, String value) {
        return (root, query, cb) -> {
            Join<Assignment,Module> join = root.join("module");
            return cb.equal(join.get("id"), value);
        };
    }
    public static Specification<Assignment> isAiGenerated(){
        return ((root, query, cb) ->
                cb.equal(root.get("isAiGenerated"), true));

    }
    public static Specification<Assignment> courseIdEquals(String courseId){
        return ((root, query, cb) ->
                cb.equal(root.get("courseId"), courseId));
    }
}
