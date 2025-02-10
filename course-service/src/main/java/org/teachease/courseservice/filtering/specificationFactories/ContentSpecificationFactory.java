package org.teachease.courseservice.filtering.specificationFactories;

import org.teachease.courseservice.entities.Content;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.SpecificationFunction;
import org.teachease.courseservice.filtering.specifications.ContentSpecification;

import java.util.HashMap;
import java.util.Map;

public class ContentSpecificationFactory implements SpecificationFactory<Content> {
    ContentSpecification specification = new ContentSpecification();
    Map<String,SpecificationFunction<Content>> SPECIFICATION_MAP = Map.of(
            "title",specification::startsWithIgnoreCase,
            "module_id",specification::moduleEquals,
            "ai_gen",specification::isTrue,
            "file_type",specification::exactMatch,
            "created_at",specification::exactMatch
    );
    @Override
    public Map<String, SpecificationFunction<Content>> getSpecificationMap() {
        return SPECIFICATION_MAP;
    }
}
