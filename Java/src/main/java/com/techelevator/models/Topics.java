package com.techelevator.models;

import java.util.List;

public class Topics {
    private int id;
    private String code;
    private String description;
    private int subjectId;
    private List<Lessons> lessons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lessons> lessons) {
        this.lessons = lessons;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubject_id(int subjectId) {
        this.subjectId = subjectId;
    }
}
