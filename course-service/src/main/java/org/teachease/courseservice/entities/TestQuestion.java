package org.teachease.courseservice.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String question;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private TestQuestionSolution solution;
    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "options")
    List<String> options;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="test_id")
    private Test test;
    @Column(nullable = false)
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
