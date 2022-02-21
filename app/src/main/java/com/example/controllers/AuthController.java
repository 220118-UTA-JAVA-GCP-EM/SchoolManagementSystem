package com.example.controllers;

import com.example.models.Person;
import com.example.service.AuthService;
import com.example.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AuthControllerBean")
public class AuthController {

    private AuthService as;
    private PersonService ps;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AuthController(AuthService as, PersonService ps){
        this.ps = ps;
        this.as = as;
    }

    public Handler login = (context) -> {

        LoginObject lo = mapper.readValue(context.body(), LoginObject.class);

        System.out.println(lo.email + " , " + lo.password);

        if(!as.loginUser(lo.email, lo.password)){
            context.status(403);
            context.result("Username or password is incorrect");
        }

        Person p = ps.getPersonByEmail(lo.email);

        //Lets set the session to know that the person is logged in
        context.req.getSession().setAttribute("id", ""+p.getPersonId());
        context.req.getSession().setAttribute("loggedIn", p.getEmail());

        context.header("pid", ""+p.getPersonId());
        context.header("loggedIn", p.getEmail());
        context.result(mapper.writeValueAsString(p));

    };

    public Handler verify = (context) -> {
        context.header("Access-Control-Expose-Headers", "*");

        System.out.println(context.req.getSession().getAttribute("id"));

        if(context.req.getSession().getAttribute("id") == null) {
            context.status(400);
            context.result("User not logged in");
        }
        else {
            context.header("pid", ""+context.req.getSession().getAttribute("id"));
            context.result("User was verified as logged in");
        }

    };

    public Handler logout = (context) -> {
        context.req.getSession().invalidate();
        context.status(200);
        context.result("User logged out");
    };

}

class LoginObject{
    public String email;
    public String password;
}