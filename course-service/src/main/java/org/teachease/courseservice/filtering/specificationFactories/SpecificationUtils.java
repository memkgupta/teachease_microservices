package org.teachease.courseservice.filtering.specificationFactories;

import org.springframework.data.jpa.domain.Specification;
import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.SpecificationFunction;

import java.util.Map;

public class SpecificationUtils<T> {
    SpecificationFactory<T> specificationFactory;
    public SpecificationUtils(SpecificationFactory<T> specificationFactory) {
        this.specificationFactory = specificationFactory;
    }
    private Specification<T> getSpecification(String field, String value) {
        SpecificationFunction<T> function = specificationFactory.getSpecificationMap().get(field);
        if(function == null) {
            return null;
        }
        return function.getSpecification(field, value);

    }
    public Specification<T> getSpecifications(Map<String,String> map) {

        return  map.entrySet().stream()
                .map((f)->{
                    return getSpecification(f.getKey(),f.getValue());
                })
                .filter(s->s != null)
                .reduce(Specification::and)
                .orElse(null);
    }
}
