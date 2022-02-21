package com.example;

import com.example.controllers.AssignmentController;
import com.example.controllers.AuthController;
import com.example.controllers.CourseController;
import com.example.controllers.PersonController;
import com.example.dao.*;
import com.example.exceptions.AssignmentPastDueException;
import com.example.models.Person;
import com.example.routes.*;
import com.example.service.AssignmentService;
import com.example.service.AuthService;
import com.example.service.CourseService;
import com.example.service.PersonService;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JavalinDriver {

    /*
    private static PersonDao pd = new PersonDaoJDBC();
    private static PersonService ps = new PersonService(pd);
    private static PersonController pc = new PersonController(ps);
    */
    //private static PersonService ps;
    //private static PersonDao pd;

    /*
    private static CourseDao cd = new CourseDaoJDBC();
    private static CourseService cs = new CourseService(cd, pd);
    private static CourseController cc = new CourseController(cs, ps);
     */

    //private static CourseService cs;

    /*
    private static AssignmentDao ad = new AssignmentDaoJDBC();
    private static AssignmentService as = new AssignmentService(ad);
    private static AssignmentController ac = new AssignmentController(as, cs, ps);
     */

    //private static AssignmentService as;

    //private static AuthService aus = new AuthService(pd);
    //private static AuthService aus;

    public static void main(String[] args){

        //We need to get our beans from our beans.xml, we will be using the Application Context IOC Container
        ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");

        //Now we set our services from the application context
        //The application will get us singleton instances of our beans for us
        /*
        ps = appContext.getBean("PersonServiceBean", PersonService.class);
        cs = appContext.getBean("CourseServiceBean", CourseService.class);
        as = appContext.getBean("AssignmentServiceBean", AssignmentService.class);
        aus = appContext.getBean("AuthServiceBean", AuthService.class);
         */


        //pd = appContext.getBean("PersonDaoBean", PersonDaoJDBC.class);

        //Establish our Javalin app
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
            config.addStaticFiles("/static", Location.CLASSPATH);
        });

        //Creating our first handler, but for the rest we are going to break the routes into controllers to handle functionality for each of our services
        app.get("/hello", (ctx) -> ctx.result("Hello we are making our first get!"));

        //To create post requests we will use gson to parse the data that we sent in the request body
        /*
        app.post("/hello", (ctx) -> {
            Gson gson = new Gson();
            SamplePost u = gson.fromJson(ctx.body(), SamplePost.class);
            System.out.println("Got this message from postman " + u.toString());
            ctx.result("Received our first message in a post");
        });
        */

        PersonController pc = appContext.getBean("PersonControllerBean", PersonController.class);
        CourseController cc = appContext.getBean("CourseControllerBean", CourseController.class);
        AssignmentController ac = appContext.getBean("AssignmentControllerBean", AssignmentController.class);
        AuthController auc = appContext.getBean("AuthControllerBean", AuthController.class);

        Route person = new PersonRoutes(pc);
        Route course = new CourseRoutes(cc);
        Route assignment = new AssignmentRoutes(ac);
        Route auth = new AuthRoutes(auc);

        Route.establishRoutes(app, person, assignment, course, auth);

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