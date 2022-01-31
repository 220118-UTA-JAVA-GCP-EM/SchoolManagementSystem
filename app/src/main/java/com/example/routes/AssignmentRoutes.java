package com.example.routes;

import com.example.controllers.AssignmentController;
import io.javalin.Javalin;

public class AssignmentRoutes extends Route{

    //Create Assignment
    //Grade Assignment
    //Turn in assignment
    //Get assignments by a student

    private AssignmentController ac;

    public AssignmentRoutes(AssignmentController ac) {

        this.ac = ac;

    }


    @Override
    public void registerLocalRoutes(Javalin app) {
        app.get("/assignments/{id}", ac.getAssignmentById);
        app.post("/assignment/{sid}", ac.createAssignment);
        app.post("/assignments/{cid}", ac.createAssignmentForCourse);
        app.put("/assignment/submit", ac.submitAssignment);
        app.put("/assignment/grade", ac.gradeAssignment);

    }
}
