package org.teachease.courseservice.filtering;

import java.util.Map;

public interface SpecificationFactory<T> {
    Map<String,SpecificationFunction<T>> getSpecificationMap();
}
