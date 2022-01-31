package com.example;

import com.example.controllers.AssignmentController;
import com.example.controllers.CourseController;
import com.example.controllers.PersonController;
import com.example.dao.*;
import com.example.exceptions.AssignmentPastDueException;
import com.example.models.Person;
import com.example.routes.AssignmentRoutes;
import com.example.routes.CourseRoutes;
import com.example.routes.PersonRoutes;
import com.example.routes.Route;
import com.example.service.AssignmentService;
import com.example.service.CourseService;
import com.example.service.PersonService;
import com.google.gson.Gson;
import io.javalin.Javalin;

public class JavalinDriver {

    private static PersonDao pd = new PersonDaoJDBC();
    private static PersonService ps = new PersonService(pd);
    private static PersonController pc = new PersonController(ps);

    private static CourseDao cd = new CourseDaoJDBC();
    private static CourseService cs = new CourseService(cd, pd);
    private static CourseController cc = new CourseController(cs, ps);

    private static AssignmentDao ad = new AssignmentDaoJDBC();
    private static AssignmentService as = new AssignmentService(ad);
    private static AssignmentController ac = new AssignmentController(as, cs, ps);

    public static void main(String[] args){

        //Establish our Javalin app
        Javalin app = Javalin.create(config -> config.enableCorsForAllOrigins());

        //Creating our first handler, but for the rest we are going to break the routes into controllers to handle functionality for each of our services
        app.get("/hello", (ctx) -> ctx.result("Hello we are making our first get!"));

        //To create post requests we will use gson to parse the data that we sent in the request body
        app.post("/hello", (ctx) -> {
            Gson gson = new Gson();
            SamplePost u = gson.fromJson(ctx.body(), SamplePost.class);
            System.out.println("Got this message from postman " + u.toString());
            ctx.result("Received our first message in a post");
        });

        Route person = new PersonRoutes(pc);
        Route course = new CourseRoutes(cc);
        Route assignment = new AssignmentRoutes(ac);

        Route.establishRoutes(app, person, assignment, course);

        app.error(403, (ctx) -> {
            ctx.result("The request you submitted is invalid");
        });

        app.exception(AssignmentPastDueException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result("You turned in your assignment in late");
        });

        app.start(7000);

    }
}

class SamplePost {
    String message;

    @Override
    public String toString(){
        return message;
    }
}