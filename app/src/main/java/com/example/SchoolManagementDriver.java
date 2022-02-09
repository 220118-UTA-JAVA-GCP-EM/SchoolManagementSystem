/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example;

import com.example.dao.*;
import com.example.exceptions.AssignmentPastDueException;
import com.example.exceptions.NotATeacherException;
import com.example.models.Assignment;
import com.example.models.Course;
import com.example.models.Person;
import com.example.models.Type;
import com.example.service.AssignmentService;
import com.example.service.CourseService;
import com.example.service.PersonService;

import java.sql.Date;

public class SchoolManagementDriver {

    private static AssignmentDao ad = new AssignmentDaoJDBC();
    private static AssignmentService assignmentService = new AssignmentService(ad);
    private static CourseDao cd = new CourseDaoJDBC();
    private static PersonDao pd = new PersonDaoJDBC();
    private static CourseService courseService = new CourseService(cd, pd);
    private static PersonService personService = new PersonService(pd);

    public static void main(String[] args) {

        //First we need some teachers and some students
        Person t1 = personService.createNewPerson(1, Type.TEACHER, "Bob", "Ross", "password");
        Person t2 = personService.createNewPerson(2, Type.TEACHER, "Valerie", "Frizzle", "password");

        Person s1 = personService.createNewPerson(3, Type.STUDENT, "Timmy", "Turner", "password");
        Person s2 = personService.createNewPerson(4, Type.STUDENT, "Morty", "Smith", "password");
        Person s3 = personService.createNewPerson(5, Type.STUDENT, "Jimmy", "Neutron", "password");

        //Our school needs courses for the students to take, and for the teachers to teach
        Course art = courseService.createNewCourse(1, "Art", 120, "Intro to Art");
        Course science = courseService.createNewCourse(2, "Science", 150, "Intro to Science");

        try{
            courseService.assignTeacher(art, t1);
            courseService.assignTeacher(science, s3);
        } catch(NotATeacherException e){
            e.printStackTrace();
        } finally {
            try {
                courseService.assignTeacher(science, t2);
            } catch (NotATeacherException e) {
                e.printStackTrace();
            }
        }

        //Lets add students to the courses now
        courseService.addStudents(art, s1, s3);
        courseService.addStudents(science, s1, s2, s3);

        System.out.println(art);
        System.out.println(science);

        //The courses need a list of topics to teach
        courseService.addTopic(art, "Painting trees", "Painting Mountains", "Painting Rivers");
        courseService.addTopic(science, "Hypothesis", "Testing", "Experients");

        System.out.println(art.getTopics());
        System.out.println(science.getTopics());

        //Finally we can create assignments, turn them in, and grade them
        //Lets create an assignment with a due date in the future and one in the past
        Assignment a1 = assignmentService.createNewAssignment(1, s1, new Date(1641403113000l));
        Assignment a2 = assignmentService.createNewAssignment(2, s2, new Date(1641057513000l));

        try{
            assignmentService.turnInAssignment(a1);
            assignmentService.turnInAssignment(a2);
        } catch (AssignmentPastDueException e) {
            e.printStackTrace();
        }

        assignmentService.gradeAssignment(100, a1);
        assignmentService.gradeAssignment(100, a2);

        System.out.println(a1);
        System.out.println(a2);
    }
}