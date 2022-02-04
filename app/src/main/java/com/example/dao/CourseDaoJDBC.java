package com.example.dao;

import com.example.models.Course;
import com.example.models.Person;
import com.example.models.Type;
import com.example.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoJDBC implements CourseDao {

    ConnectionUtil conUtil = ConnectionUtil.getInstance();

    //We will use prepared statements to interface with the db
    @Override
    public void createCourse(Course c) {
        try{

            Connection con = conUtil.getConntection();

            String sql = "INSERT INTO courses(subject, course_number, name, teacher)" +
                    "values(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getSubject());
            ps.setInt(2, c.getNumber());
            ps.setString(3, c.getName());
            ps.setNull(4, Types.INTEGER);

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void createTopic(Course c, String topic) {

        try{

            Connection con = conUtil.getConntection();

            String sql = "INSERT INTO topics(topic_text, course)" +
                    "values(?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, topic);
            ps.setInt(2, c.getCourseId());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void createStudentCourseRelation(Course c, Person s) {
        try{

            Connection con = conUtil.getConntection();

            String sql = "INSERT INTO student_course_junction(student_id, course_id)" +
                    "values(?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,s.getPersonId());
            ps.setInt(2, c.getCourseId());


            System.out.println("Executing update");
            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> readAllCourses() {

        List<Course> cList = new ArrayList<>();

        try{

            Connection con = conUtil.getConntection();

            String sql = "SELECT * FROM courses";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Course c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                if(rs.getInt(5) > 0){
                    Person t = new Person();
                    t.setPersonId(rs.getInt(5));
                    c.setTeacher(t);
                }
                cList.add(c);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return cList;
    }



    @Override
    public List<Person> readStudentList(int courseID) {

        List<Person> sList = new ArrayList<>();

        try{
            Connection con = conUtil.getConntection();

            String sql = "select * from student_course_junction scj inner join people p on p.id = scj.student_id where scj.course_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, courseID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Person s = new Person(rs.getInt(1), Type.values()[rs.getInt(4)-1], rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8));
                sList.add(s);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return sList;
    }

    @Override
    public List<String> readTopicsList(int courseID) {
        List<String> tList = new ArrayList<>();

        try{

            Connection con = conUtil.getConntection();

            String sql = "SELECT * FROM topics WHERE course=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, courseID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tList.add(rs.getString(2));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return tList;
    }

    @Override
    public Course readCourseById(int courseID) {

        Course c = new Course();

        try{
            Connection con = conUtil.getConntection();

            String sql = "SELECT * FROM courses WHERE course_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, courseID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                c = new Course(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                if(rs.getInt(5) > 0){
                    Person t = new Person();
                    t.setPersonId(rs.getInt(5));
                    c.setTeacher(t);
                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return c;
    }


    @Override
    public void updateCourse(Course c) {

        try{

            Connection con = conUtil.getConntection();

            String sql = "UPDATE courses SET " +
                    "subject=?, " +
                    "course_number=?, " +
                    "name=?, " +
                    "teacher=? WHERE course_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getSubject());
            ps.setInt(2, c.getNumber());
            ps.setString(3, c.getName());
            if(c.getTeacher() != null){
                ps.setInt(4, c.getTeacher().getPersonId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, c.getCourseId());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCourse(Course c) {

        try{

            Connection con = conUtil.getConntection();

            String sql = "DELETE FROM courses WHERE course_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, c.getCourseId());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }

    }
}
