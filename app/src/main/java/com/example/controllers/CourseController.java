package com.example.controllers;

import com.example.exceptions.NotATeacherException;
import com.example.models.Course;
import com.example.models.Person;
import com.example.service.CourseService;
import com.example.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component("CourseControllerBean")
public class CourseController {

    private CourseService cs;
    private PersonService ps;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public CourseController(CourseService cs, PersonService ps){
        this.cs = cs;
        this.ps = ps;
    }


    public Handler createCourse = (context) -> {

        Course c = mapper.readValue(context.body(), Course.class);

        System.out.println(c);

        cs.createNewCourse(c.getCourseId(), c.getSubject(), c.getNumber(), c.getName());

        context.result(mapper.writeValueAsString(c));

    };

    public Handler getAllCourses = (context) -> {
        context.header("Access-Control-Expose-Headers", "*");

        context.header("name", "value");

        context.result(mapper.writeValueAsString(cs.getAllCourses()));

    };

    public Handler addTeacher = (context) -> {

        Integer teacherId = Integer.parseInt(context.queryParam("tid"));
        Integer courseId = Integer.parseInt(context.queryParam("cid"));

        Person p = ps.getPersonById(teacherId);

        Course c = cs.getCourseById(courseId);
        try{
            cs.assignTeacher(c, p);
        } catch(NotATeacherException e){
            context.status(403);
        }

        context.result(mapper.writeValueAsString(c));

    };

    public Handler addStudents = (context) -> {

        Integer courseId = Integer.parseInt(context.queryParam("cid"));

        List<Person> pList = mapper.readValue(context.body(), new TypeReference<List<Person>>(){});

        Person[] personArray = new Person[pList.size()];

        for(int i=0; i<pList.size(); i++){
            personArray[i] = pList.get(i);
        }

        System.out.println(pList);

        Course c = cs.getCourseById(courseId);

        cs.addStudents(c, personArray);

        context.result(mapper.writeValueAsString(c));
    };

    public Handler addTopic = (context) -> {

        Topic t = mapper.readValue(context.body(), Topic.class);

        Course c = cs.getCourseById(t.courseId);

        cs.addTopic(c, t.topics);

        context.result(mapper.writeValueAsString(c));

    };

}

class Topic {
    public int courseId;
    public String[] topics;
}
