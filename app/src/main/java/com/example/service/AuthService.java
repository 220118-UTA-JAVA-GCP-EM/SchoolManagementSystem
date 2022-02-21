package com.example.service;

import com.example.dao.PersonDao;
import com.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AuthServiceBean")
public class AuthService {

    @Autowired
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
