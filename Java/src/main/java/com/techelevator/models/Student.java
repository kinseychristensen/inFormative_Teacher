package com.techelevator.models;

import java.util.List;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int schoolId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + " :" +  id;
    }
}
//TODO: Add a student school Id variable that is seperate from the database id.