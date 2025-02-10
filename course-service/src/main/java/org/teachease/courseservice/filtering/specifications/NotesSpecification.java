package org.teachease.courseservice.filtering.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.teachease.courseservice.entities.Notes;
import org.teachease.courseservice.filtering.SpecificationInterface;

public class NotesSpecification implements SpecificationInterface<Notes> {
    public static Specification<Notes> titleContains(String title){
        return ((root, query, cb) ->
                cb.like(root.get("title"), "%"+title+"%"));
    }
    public static Specification<Notes> titleStartsWith(String title){
        return ((root, query, cb) ->
                cb.like(root.get("title"), title+"%"));
    }
    public Specification<Notes> moduleEquals(String field, String value){
        return ((root, query, cb) ->
        {
            Join<Notes, Module> modules = root.join("module", JoinType.INNER);
            return cb.equal(modules.get("id"), value);
        });
    }
    public Specification<Notes> fileTypeEquals(String field, String fileType){
        return ((root, query, cb) ->
                cb.like(root.get(field), fileType));
    }
    public static Specification<Notes> isAiGenerated(){
        return ((root, query, cb) ->
                cb.equal(root.get("isAiGenerated"), true));
    }
    public static Specification<Notes> courseIdEquals(String courseId){
        return ((root, query, cb) ->
                cb.equal(root.get("courseId"), courseId));
    }
}
