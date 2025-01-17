package org.teachease.courseservice.entities;

import jakarta.persistence.*;

@Entity
@Table
public class TestQuestionSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private TestQuestion question;
    private String solution;
    private String solType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestQuestion getQuestion() {
        return question;
    }

    public void setQuestion(TestQuestion question) {
        this.question = question;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolType() {
        return solType;
    }

    public void setSolType(String solType) {
        this.solType = solType;
    }
}
