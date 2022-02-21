package com.example.controllers;

import com.example.models.Assignment;
import com.example.models.Course;
import com.example.models.Person;
import com.example.service.AssignmentService;
import com.example.service.CourseService;
import com.example.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AssignmentControllerBean")
public class AssignmentController {

    private AssignmentService as;
    private CourseService cs;
    private PersonService ps;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AssignmentController(AssignmentService as, CourseService cs, PersonService ps){
        this.as = as;
        this.cs = cs;
        this.ps = ps;
    }

    public Handler createAssignment = (context) -> {

        Integer sid = Integer.parseInt(context.pathParam("sid"));

        Person p = ps.getPersonById(sid);

        Assignment a = mapper.readValue(context.body(), Assignment.class);

        as.createNewAssignment(1, p, a.getDue());

        context.result(mapper.writeValueAsString(a));

    };

    public Handler createAssignmentForCourse = (context) -> {

        Integer cid = Integer.parseInt(context.pathParam("cid"));

        Course c = cs.getCourseById(cid);

        System.out.println(c);

        Assignment a = mapper.readValue(context.body(), Assignment.class);

        List<Person> pList = c.getStudents();

        List<Assignment> aList = new ArrayList<>();

        pList.forEach(student -> aList.add(as.createNewAssignment(0, student, a.getDue())));

        context.result(mapper.writeValueAsString(aList));

    };

    public Handler getAssignmentById = (context) -> {

        Integer sid = Integer.parseInt(context.pathParam("id"));

        context.result(mapper.writeValueAsString(as.getStudentsAssignments(sid)));

    };

    public Handler submitAssignment = (context) -> {

        Assignment a = mapper.readValue(context.body(), Assignment.class);

        as.turnInAssignment(a);

        context.result("Assignment was turned in successfully");
    };

    public Handler gradeAssignment = (context) -> {

        Double grade = Double.parseDouble(context.queryParam("grade"));

        Assignment a = mapper.readValue(context.body(), Assignment.class);

        as.gradeAssignment(grade, a);

        context.result("Assignment was graded successfully");

    };

}
