package com.example.models;

import java.sql.Date;

public class Assignment {

    private int assignmentId;
    private Person student;
    private double grade;
    private boolean done;
    private boolean pastDue;
    private java.sql.Date due;

    public Assignment() {
    }

    public Assignment(int assignmentId, Person student, double grade, boolean done, boolean pastDue, Date due) {
        this.assignmentId = assignmentId;
        this.student = student;
        this.grade = grade;
        this.done = done;
        this.pastDue = pastDue;
        this.due = due;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isPastDue() {
        return pastDue;
    }

    public void setPastDue(boolean pastDue) {
        this.pastDue = pastDue;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", student=" + student +
                ", grade=" + grade +
                ", done=" + done +
                ", pastDue=" + pastDue +
                ", due=" + due +
                '}';
    }
}
