package com.example.assignment;

import com.example.dao.AssignmentDao;
import com.example.exceptions.AssignmentPastDueException;
import com.example.models.Assignment;
import com.example.models.Person;
import com.example.models.Type;
import com.example.service.AssignmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AssignmentServiceTest {

    @Mock
    static AssignmentDao ad;

    @InjectMocks
    private static AssignmentService as;

    @Before
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAnAssignmentShouldReturnANewAssignment(){

        //When we call createAssignment from the AssignmentService make sure we do not actually make a call to the db
        doNothing().when(ad).createAssignment(any());

        Assignment test = as.createNewAssignment(1, new Person(), new Date(System.currentTimeMillis()));

        //Verify that the dao was called, but we intercepted the call to the db
        Mockito.verify(ad).createAssignment(any());

        assertNotNull(test);
    }

    @Test
    public void createAnAssignmentShouldSetTheCorrectAttributes(){
        //When we call createAssignment from the AssignmentService make sure we do not actually make a call to the db
        doNothing().when(ad).createAssignment(any());

        Person p = new Person(1, Type.STUDENT, "first", "last", "email", "password");

        Date d = new Date(System.currentTimeMillis());

        Assignment test = as.createNewAssignment(1, p, d);

        //Verify that the dao was called, but we intercepted the call to the db
        Mockito.verify(ad).createAssignment(any());

        assertEquals(1, test.getAssignmentId());
        assertEquals(p, test.getStudent());
        assertEquals(0.0, test.getGrade(), 0.01);
        assertFalse(test.isDone());
        assertFalse(test.isPastDue());
        assertEquals(d, test.getDue());

    }

    @Test
    public void turningInAnAssignmentOnTime() throws AssignmentPastDueException {
        Person p = new Person(1, Type.STUDENT, "first", "last", "email", "password");

        //Set the date to tomorrow
        Date d = new Date(System.currentTimeMillis()+86400000l);

        Assignment test = as.createNewAssignment(1, p, d);

        doNothing().when(ad).updateAssignment(any());

        as.turnInAssignment(test);

        Mockito.verify(ad).updateAssignment(any());

        assertTrue(test.isDone());

    }

    @Test(expected = AssignmentPastDueException.class)
    public void turningInAssignmentAfterDue()throws AssignmentPastDueException {
        Person p = new Person(1, Type.STUDENT, "first", "last", "email", "password");

        //Set the date to yesterday
        Date d = new Date(System.currentTimeMillis()-86400000l);

        Assignment test = as.createNewAssignment(1, p, d);

        doNothing().when(ad).updateAssignment(any());

        as.turnInAssignment(test);

        verify(ad).updateAssignment(any());

        assertTrue(test.isDone());
        assertTrue(test.isPastDue());
    }

    @Test
    public void gradeAssignmentThatWasTurnedInOnTime(){
        Assignment test = new Assignment(1, new Person(), 0.0, true, false, new Date(System.currentTimeMillis()));

        doNothing().when(ad).updateAssignment(any());

        as.gradeAssignment(100, test);

        verify(ad).updateAssignment(any());

        assertEquals(100.0, test.getGrade(), 0.01);
    }

    @Test
    public void gradeAssignmentThatWasPastDue(){
        Assignment test = new Assignment(1, new Person(), 0.0, true, true, new Date(System.currentTimeMillis()));

        doNothing().when(ad).updateAssignment(any());

        as.gradeAssignment(100.0, test);

        verify(ad).updateAssignment(any());

        assertEquals(80.0, test.getGrade(), 0.01);

    }

}
