package com.example.exceptions;

public class AssignmentPastDueException extends Exception {

    public AssignmentPastDueException(){
        super("The assignment was turned in after its due date.");
    }

}
