package org.teachease.courseservice.filtering.specificationFactories;

import org.teachease.courseservice.entities.Assignment;
import org.teachease.courseservice.entities.Notes;
import org.teachease.courseservice.filtering.SpecificationFactory;
import org.teachease.courseservice.filtering.SpecificationFunction;
import org.teachease.courseservice.filtering.specifications.NotesSpecification;

import java.util.Map;

public class NotesSpecificationFactory implements SpecificationFactory<Notes> {
    NotesSpecification notesSpecification = new NotesSpecification();
    private Map<String, SpecificationFunction<Notes>> SPECIFICATION_MAP =
            Map.of(
                    "title",notesSpecification::startsWithIgnoreCase,
                    "module_id",notesSpecification::moduleEquals,
                    "ai_gen",notesSpecification::isTrue,
                    "file_type",notesSpecification::fileTypeEquals
            );

    @Override
    public Map<String, SpecificationFunction<Notes>> getSpecificationMap() {
        return SPECIFICATION_MAP;
    }
}
