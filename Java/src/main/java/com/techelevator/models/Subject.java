package com.techelevator.models;

import java.util.List;

public class Subject {
    private int id;
    private String code;
    private String description;
    private List<Topics> topics;
    private int classId;
    private int notAttempted;
    private int below;
    private int approaching;
    private int proficient;
    private int mastered;


    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

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

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }

    public int getNotAttempted() {
        return notAttempted;
    }

    public void setNotAttempted(int notAttempted) {
        this.notAttempted = notAttempted;
    }

    public int getBelow() {
        return below;
    }

    public void setBelow(int below) {
        this.below = below;
    }

    public int getApproaching() {
        return approaching;
    }

    public void setApproaching(int approaching) {
        this.approaching = approaching;
    }

    public int getProficient() {
        return proficient;
    }

    public void setProficient(int proficient) {
        this.proficient = proficient;
    }

    public int getMastered() {
        return mastered;
    }

    public void setMastered(int mastered) {
        this.mastered = mastered;
    }
}
