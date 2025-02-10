package org.teachease.courseservice.filtering.specificationFactories;

import org.springframework.data.jpa.domain.Specification;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.SpecificationFunction;
import org.teachease.courseservice.filtering.SpecificationInterface;
import org.teachease.courseservice.filtering.specifications.AssignmentSpecification;

import java.util.Map;

public class AssignmentSpecificationFactory implements SpecificationFactory<Assignment> {

    private AssignmentSpecification assignmentSpecification = new AssignmentSpecification();
    private Map<String, SpecificationFunction<Assignment>> SPECIFICATION_MAP =
            Map.of(
                    "title",assignmentSpecification::startsWithIgnoreCase,
                    "module_id",assignmentSpecification::moduleEquals,
                        "ai_gen",assignmentSpecification::isTrue
            );
    private  Specification<Assignment> getSpecification(String field,String value) {
        SpecificationFunction<Assignment> function = SPECIFICATION_MAP.get(field);
        if(function == null) {
            return null;
        }
        return function.getSpecification(field, value);

    }
    public Specification<Assignment> getSpecifications(Map<String,String> map) {

        return  map.entrySet().stream()
                .map((f)->{
                    return getSpecification(f.getKey(),f.getValue());
                })
                .filter(s->s != null)
                .reduce(Specification::and)
                .orElse(null);
    }

    @Override
    public Map<String, SpecificationFunction<Assignment>> getSpecificationMap() {
        return SPECIFICATION_MAP;
    }
}
