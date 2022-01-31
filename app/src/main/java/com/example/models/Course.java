package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private int courseId;
    private String subject;
    private int number;
    private String name;
    private Person teacher;
    private List<String> topics;
    private List<Person> students;

    public Course(){
        this.topics = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public Course(int courseId, String subject, int number, String name) {
        this.courseId = courseId;
        this.subject = subject;
        this.number = number;
        this.name = name;
        this.topics = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", subject='" + subject + '\'' +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", topics=" + topics +
                ", students=" + students +
                '}';
    }
}
