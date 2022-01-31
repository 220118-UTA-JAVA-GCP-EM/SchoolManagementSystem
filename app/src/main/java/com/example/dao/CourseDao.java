package com.example.dao;

import com.example.models.Course;
import com.example.models.Person;

import java.util.List;

public interface CourseDao {

    public void createCourse(Course c);
    public void createTopic(Course c, String topic);
    public void createStudentCourseRelation(Course c, Person s);

    public List<Course> readAllCourses();
    public List<Person> readStudentList(int courseID);
    public List<String> readTopicsList(int courseID);
    public Course readCourseById(int courseID);

    public void updateCourse(Course c);

    public void deleteCourse(Course c);


}
