package com.example.dao;

import com.example.models.Assignment;
import com.example.models.Person;
import com.example.utils.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AssignmentDaoJDBC implements AssignmentDao{

    ConnectionUtil conUtil = ConnectionUtil.getInstance();

    @Override
    public void createAssignment(Assignment a) {

        try{
            Connection con = conUtil.getConntection();

            String sql = "call create_assignment(?, ?, ?, ?, ?)";

            con.setAutoCommit(false);

            CallableStatement cs = con.prepareCall(sql);

            System.out.println(a);


            cs.setInt(1, a.getStudent().getPersonId());
            cs.setBigDecimal(2, BigDecimal.valueOf(a.getGrade()));
            cs.setBoolean(3, a.isDone());
            cs.setBoolean(4, a.isPastDue());
            cs.setDate(5, a.getDue());


            cs.executeUpdate();

            con.setAutoCommit(true);

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Assignment> readAllAssignments() {

        List<Assignment> aList = new ArrayList<>();

        try{

            Connection con = conUtil.getConntection();

            con.setAutoCommit(false);

            String sql = "{? = call get_all_assignments()}";

            CallableStatement cs = con.prepareCall(sql);

            cs.registerOutParameter(1, Types.OTHER);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);

            while(rs.next()){
                Assignment a = new Assignment();
                a.setAssignmentId(rs.getInt(1));
                Person p = new Person();
                p.setPersonId(rs.getInt(2));
                a.setStudent(p);
                a.setGrade(rs.getDouble(3));
                a.setDone(rs.getBoolean(4));
                a.setPastDue(rs.getBoolean(5));
                a.setDue(rs.getDate(6));
                aList.add(a);
            }

            con.setAutoCommit(true);

        } catch(SQLException e){
            e.printStackTrace();
        }

        return aList;
    }

    @Override
    public List<Assignment> readAllAssignmentsByStudent(int studentId) {
        List<Assignment> aList = new ArrayList<>();

        try{
            Connection con = conUtil.getConntection();

            con.setAutoCommit(false);

            String sql = "{?=call get_assignments_by_student(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.registerOutParameter(1, Types.OTHER);

            cs.setInt(2, studentId);

            cs.execute();

            ResultSet rs = (ResultSet)cs.getObject(1);

            while(rs.next()){
                Assignment a = new Assignment();
                a.setAssignmentId(rs.getInt(1));
                Person p = new Person();
                p.setPersonId(rs.getInt(2));
                a.setStudent(p);
                a.setGrade(rs.getDouble(3));
                a.setDone(rs.getBoolean(4));
                a.setPastDue(rs.getBoolean(5));
                a.setDue(rs.getDate(6));
                aList.add(a);
            }

            con.setAutoCommit(true);


        } catch(SQLException e){
            e.printStackTrace();
        }

        return aList;
    }

    @Override
    public void updateAssignment(Assignment a) {
        try{
            Connection con = conUtil.getConntection();

            con.setAutoCommit(false);

            String sql = "call update_assignment(?, ?, ?, ?, ?, ?)";

            CallableStatement cs = con.prepareCall(sql);

            System.out.println(a);


            cs.setInt(1, a.getAssignmentId());
            cs.setInt(2, a.getStudent().getPersonId());
            cs.setDouble(3, a.getGrade());
            cs.setBoolean(4, a.isDone());
            cs.setBoolean(5, a.isPastDue());
            cs.setDate(6, a.getDue());

            cs.executeUpdate();

            con.setAutoCommit(true);

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAssignment(Assignment a) {

        try{

            Connection con = conUtil.getConntection();

            con.setAutoCommit(false);

            String sql = "call delete_assignment(?)";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, a.getAssignmentId());

            cs.executeUpdate();

            con.setAutoCommit(true);

        } catch(SQLException e){
            e.printStackTrace();
        }

    }
}
