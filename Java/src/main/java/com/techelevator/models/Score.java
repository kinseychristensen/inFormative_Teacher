package com.techelevator.models;

import java.math.BigDecimal;

public class Score {
    private int studentId;
    private int artifactId;
    private BigDecimal score;
    private String comments;
    private Boolean isWaived;
    private int lessonId;



    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getArtifactId() {
        return artifactId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public void setArtifactId(int artifactId) {
        this.artifactId = artifactId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getWaived() {
        return isWaived;
    }

    public void setWaived(Boolean waived) {
        isWaived = waived;
    }


}
