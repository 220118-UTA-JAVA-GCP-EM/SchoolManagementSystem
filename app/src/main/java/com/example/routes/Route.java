package com.example.routes;

import io.javalin.Javalin;

public abstract class Route {

    public static void establishRoutes(Javalin app, Route ... routes){
        for(int i=0; i<routes.length; i++){
            Route r = routes[i];
            r.registerLocalRoutes(app);
        }
    }

    abstract public void registerLocalRoutes(Javalin app);

}
