package com.example.service;

import com.example.dao.AssignmentDao;
import com.example.exceptions.AssignmentPastDueException;
import com.example.models.Assignment;
import com.example.models.Person;
import com.example.utils.LoggingSingleton;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

public class AssignmentService {

    @Autowired
    private AssignmentDao ad;

    public AssignmentService(){
        System.out.println("Spring is calling the Assignment Service no arg");
    }

    public AssignmentService(AssignmentDao ad){
        this.ad = ad;
    }

    //To create an assignment the only actual user input that we need is an id,
    //the student who its assigned to, and the due date
    public Assignment createNewAssignment(int id, Person s, Date due){
        //This is because we can input from default values
        Assignment a = new Assignment(id, s, 0.0, false, false, due);

        LoggingSingleton.logger.info("New assignment created: " + a.toString());

        ad.createAssignment(a);

        return a;
    }

    public List<Assignment> getStudentsAssignments(int id){
        return ad.readAllAssignmentsByStudent(id);
    }

    public void turnInAssignment(Assignment a) throws AssignmentPastDueException{
        long today = System.currentTimeMillis();
        Date turnInDate = new Date(today);
        //If todays date is after the due date, uhoh
        if(turnInDate.compareTo(a.getDue()) > 0){
            a.setPastDue(true);
            a.setDone(true);
            ad.updateAssignment(a);
            LoggingSingleton.logger.warn("Assignment: " + a.toString() + " \n Was submitted late");
            throw new AssignmentPastDueException();
        }
        a.setDone(true);
        ad.updateAssignment(a);
        LoggingSingleton.logger.info("Assignment: " + a.toString() + " \n Was submitted on time");
    }

    public void gradeAssignment(double grade, Assignment a){
        if(a.isPastDue()){
            //If the student turned in an assignment late, it gets a deduction
            grade = grade * .8;
            a.setGrade(grade);
        } else {
            a.setGrade(grade);
        }

        ad.updateAssignment(a);

        LoggingSingleton.logger.info("Assignment: " + a.toString() + " \n Was given the grade: " + grade);
    }

    public void setAd(AssignmentDao ad){
        System.out.println("Hey look at me, I am being autowired");
        this.ad = ad;
    }

}
