package com.example.service;

import com.example.dao.PersonDao;
import com.example.models.Person;

public class AuthService {

    private PersonDao pd;

    public AuthService(PersonDao pd){
        this.pd = pd;
    }

    public boolean loginUser(String email, String password){
        Person login = pd.readPersonByEmail(email);

        if(login == null || !login.getPassword().equals(password)){
            return false;
        }

        return true;
    }

}
