package com.example.service;

import com.example.dao.CourseDao;
import com.example.dao.PersonDao;
import com.example.exceptions.NotATeacherException;
import com.example.models.Course;
import com.example.models.Person;
import com.example.models.Type;
import com.example.utils.LoggingSingleton;

import java.util.Collections;
import java.util.List;

public class CourseService {

    private CourseDao cd;
    private PersonDao pd;

    public CourseService(CourseDao cd, PersonDao pd){
        System.out.println("Spring is injecting the dependencies in my constructor");
        this.cd = cd;
        this.pd = pd;
    }

    //At our school a course is created without a teacher, we will assign the teacher later on
    //after we determine who the teacher should be, and what topics they will be teaching
    public Course createNewCourse(int id, String subject, int number, String name){
        Course c = new Course();
        c.setCourseId(id);
        c.setSubject(subject);
        c.setNumber(number);
        c.setName(name);
        cd.createCourse(c);
        LoggingSingleton.logger.info("New Course created: " + c.toString());
        return c;
    }

    public void assignTeacher(Course c, Person t) throws NotATeacherException{
        if(t.getType() != Type.TEACHER){
            LoggingSingleton.logger.warn("Person: " + t.toString() + " \n who is not a teacher was assigned to a course");
            throw new NotATeacherException();
        }
        c.setTeacher(t);
        cd.updateCourse(c);
        LoggingSingleton.logger.info("Person: " + t.toString() + " \n was assigned to the course: " + c.toString());
    }

    public void addStudents(Course c, Person ... p){

        List<Person> students = c.getStudents();

        Collections.addAll(students, p);

        students.forEach(student -> cd.createStudentCourseRelation(c, student));

        c.setStudents(students);
    }

    public void addTopic(Course c, String ... topic){
        List<String> topicList = c.getTopics();

        Collections.addAll(topicList, topic);

        topicList.forEach(t -> cd.createTopic(c, t));

        c.setTopics(topicList);

        LoggingSingleton.logger.info("New topics added to course: " + c.toString());
    }

    public List<Course> getAllCourses(){
        List<Course> cList = cd.readAllCourses();

        cList.forEach(course -> {
            if(course.getTeacher() != null){
                course.setTeacher(pd.readPersonById(course.getTeacher().getPersonId()));
            }
            course.setTopics(cd.readTopicsList(course.getCourseId()));
            course.setStudents(cd.readStudentList(course.getCourseId()));
        });

        return cList;

    }

    public Course getCourseById(int id){
        Course c = cd.readCourseById(id);

        if(c.getTeacher() != null){
            c.setTeacher(pd.readPersonById(c.getTeacher().getPersonId()));
        }
        c.setTopics(cd.readTopicsList(c.getCourseId()));
        c.setStudents(cd.readStudentList(c.getCourseId()));

        return c;
    }

}
