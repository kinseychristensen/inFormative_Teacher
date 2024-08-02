package com.techelevator.models;

import java.time.LocalDate;
import java.util.List;

public class Artifact {
    private int id;
    private int artifactTypeInt;
    private String artifactType;
    private String description;
    private int lessonId;
    private int teacherId;
    private String assignmentDateAsStr;
    private LocalDate assignmentDate;
    private String trends;
    private String comments;

    public int getId() {
        return id;
    }

    public String getAssignmentDateAsStr() {
        return assignmentDateAsStr;
    }

    public void setAssignmentDateAsStr(String assignmentDateAsStr) {
        this.assignmentDateAsStr = assignmentDateAsStr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtifactTypeInt() {
        return artifactTypeInt;
    }

    public void setArtifactTypeInt(int artifactTypeInt) {
        this.artifactTypeInt = artifactTypeInt;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getTrends() {
        return trends;
    }

    public void setTrends(String trends) {
        this.trends = trends;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
