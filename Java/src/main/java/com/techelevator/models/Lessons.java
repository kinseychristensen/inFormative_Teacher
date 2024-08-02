package com.techelevator.models;

import java.util.List;

public class Lessons {
    private int id;
    private String code;
    private String description;
   private int topicId;



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

    public int getTopic_id() {
        return topicId;
    }

    public void setTopic_id(int topicId) {
        this.topicId = topicId;
    }
}
