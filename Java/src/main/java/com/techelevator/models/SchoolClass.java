package com.techelevator.models;

import java.util.List;

public class SchoolClass {
    private int classId;
    private String description;
    private String className;
    private boolean active;



    public int getClassId() {
        return classId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }



}
