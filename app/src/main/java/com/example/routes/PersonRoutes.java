package com.example.routes;

import com.example.controllers.PersonController;
import io.javalin.Javalin;

public class PersonRoutes extends Route {

    //Create person
    //Get all people
    //Get person by id
    private PersonController pc;

    public PersonRoutes(PersonController pc){
        this.pc = pc;
    }

    @Override
    public void registerLocalRoutes(Javalin app) {
        app.get("/people", pc.getAllPeople);
        app.get("/people/{id}", pc.getPersonById);
        app.post("/person", pc.createPerson);
    }

}
