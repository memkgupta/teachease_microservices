package org.teachease.courseservice.entities;

import jakarta.persistence.*;

import java.util.List;


public class TestQuestion {

    private String id;
    private String question;

    private TestQuestionSolution solution;

    List<String> options;

    private Test test;

    private String courseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TestQuestionSolution getSolution() {
        return solution;
    }

    public void setSolution(TestQuestionSolution solution) {
        this.solution = solution;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
