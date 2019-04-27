package edu.exeter.homeworkapp;

import java.io.Serializable;
import java.util.*;

public class Homework implements Serializable {

    private String dueDate;
    private String subject;
    private String description;
    private String name;

    public Homework(String name, String date, String subject, String description){
        this.name = name;
        this.dueDate = date;
        this.subject = subject;
        this.description = description;
    }

    @Override
    public String toString() {
        return " " + name + "\n Subject: " + subject + "\n Due: " + dueDate;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSubject() { return subject; }

    public String getDescription() {
        return description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
