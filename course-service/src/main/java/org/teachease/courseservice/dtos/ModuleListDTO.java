package org.teachease.courseservice.dtos;

public class ModuleListDTO {
    private String id;
    private ModuleDTO head;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModuleDTO getHead() {
        return head;
    }

    public void setHead(ModuleDTO head) {
        this.head = head;
    }
}
