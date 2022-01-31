package com.example.routes;

import com.example.controllers.CourseController;
import io.javalin.Javalin;

public class CourseRoutes extends Route{

    private CourseController cc;

    public CourseRoutes(CourseController cc){
        this.cc = cc;
    }

    //Get all courses
    //Create course
    //Assign Teacher
    //Add Students
    //Add Topic

    @Override
    public void registerLocalRoutes(Javalin app) {
        app.get("/courses", cc.getAllCourses);
        app.post("/course", cc.createCourse);
        app.put("/course/teacher", cc.addTeacher);
        app.put("/course/students", cc.addStudents);
        app.post("/course/topic", cc.addTopic);
    }

}
