package com.example.controllers;

import com.example.models.Person;
import com.example.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PersonControllerBean")
public class PersonController {

    private PersonService ps;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PersonController(PersonService ps){
        this.ps = ps;
    }

    public Handler createPerson = (context) -> {

        Person p = mapper.readValue(context.body(), Person.class);

        System.out.println(p);

        ps.createNewPerson(p.getPersonId(), p.getType(), p.getFirst(), p.getLast(), p.getPassword());

        context.result(mapper.writeValueAsString(p));

    };

    public Handler getAllPeople = (context) -> {

        context.result(mapper.writeValueAsString(ps.getAllPeople()));
    };

    public Handler getPersonById = (context) -> {

        Integer id = Integer.parseInt(context.pathParam("id"));

        context.result(mapper.writeValueAsString(ps.getPersonById(id)));

    };
}
