package com.example.routes;

import com.example.controllers.AuthController;
import io.javalin.Javalin;

public class AuthRoutes extends Route{

    private AuthController ac;

    public AuthRoutes(AuthController ac){
        this.ac = ac;
    }

    @Override
    public void registerLocalRoutes(Javalin app) {
        app.post("/login", ac.login);
        app.get("/verify", ac.verify);
        app.get("/logout", ac.logout);
    }
}
