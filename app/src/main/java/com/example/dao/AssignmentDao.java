package com.example.dao;

import com.example.models.Assignment;

import java.util.List;

public interface AssignmentDao {

    public void createAssignment(Assignment a);

    public List<Assignment> readAllAssignments();
    public List<Assignment> readAllAssignmentsByStudent(int studentId);

    public void updateAssignment(Assignment a);

    public void deleteAssignment(Assignment a);

}
