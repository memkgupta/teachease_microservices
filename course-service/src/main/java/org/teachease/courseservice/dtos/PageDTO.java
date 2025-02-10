package org.teachease.courseservice.dtos;

import java.util.List;

public interface PageDTO<T> {
    int getPageNumber();
    int getPageSize();
    long getTotalElements();
    List<T> getContent();
}
