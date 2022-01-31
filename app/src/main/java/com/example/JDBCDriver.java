package com.example;

import com.example.dao.*;
import com.example.models.Assignment;
import com.example.models.Course;
import com.example.models.Person;
import com.example.models.Type;

import java.sql.Date;

public class JDBCDriver {

    public static void main(String[] args){

        PersonDao pd = new PersonDaoJDBC();
        CourseDao cd = new CourseDaoJDBC();
        AssignmentDao ad = new AssignmentDaoJDBC();

        Person t = new Person(9, Type.TEACHER, "Test", "Teacher", "email", "password");
        Person s = new Person(10, Type.STUDENT, "Test", "Student", "email", "password");

        //pd.createPerson(t);
        //pd.createPerson(s);

        System.out.println(pd.readPersonById(9));
        System.out.println(pd.readPersonById(10));

        System.out.println(pd.readAllPeople());

        t.setFirst("Changed");
        t.setLast("Name");
        t.setEmail("changed.email@school.com");

        pd.updatePerson(t);

        System.out.println(pd.readPersonById(9));

        //pd.deletePerson(t);
        //pd.deletePerson(s);

        Course art = new Course(3, "Art", 103, "Art Class");

        //cd.createCourse(art);

        //cd.createTopic(art, "Painting Tress");

        //cd.createStudentCourseRelation(art, s);

        System.out.println(cd.readAllCourses());

        System.out.println(cd.readStudentList(2));

        System.out.println(cd.readTopicsList(2));

        art.setTeacher(t);
        //art.setName("different name, no teacher test");

        cd.updateCourse(art);

        //cd.deleteCourse(art);

        java.sql.Date d = new java.sql.Date(1641403113000l);

        Assignment a = new Assignment(1, s, 0.0, false, false, d);

        //ad.createAssignment(a);

        System.out.println(ad.readAllAssignments());

        System.out.println(ad.readAllAssignmentsByStudent(10));

        a.setDone(true);
        a.setGrade(90.0);

        ad.updateAssignment(a);

        ad.deleteAssignment(a);
    }

}
