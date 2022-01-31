package com.example.service;

import com.example.dao.PersonDao;
import com.example.models.Person;
import com.example.models.Type;
import com.example.utils.LoggingSingleton;

import java.util.List;
import java.util.Locale;

public class PersonService {

    //Service is what we will use to do CRUD functionality, it will be an inbetween link between our program and the database
    //Create
    //Read
    //Update
    //Delete
    //The service class is also where other business logic occurs

    private PersonDao pd;
    public PersonService(PersonDao pd){
        this.pd = pd;
    }

    public Person createNewPerson(int id, Type t, String first, String last, String password){
        String email = first + "." + last + "@school.edu";
        email = email.toLowerCase();
        Person p = new Person(id, t, first, last, email, password);
        LoggingSingleton.logger.info("Person: \n" + p.toString() + " was created");

        pd.createPerson(p);

        return p;
    }

    //Without a database we the other parts of crud are pretty useless here
    //We will conduct these other bits next week with SQL
    public List<Person> getAllPeople(){
        return pd.readAllPeople();
    }

    public Person getPersonById(int id){
        return pd.readPersonById(id);
    }

    public void updatePerson(Person p){
        pd.updatePerson(p);
    }

    public void deletePerson(Person p){
        pd.deletePerson(p);
    }

}
