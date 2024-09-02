package com.techelevator.dao;

import com.techelevator.models.Student;
import java.util.List;

public interface StudentDao {

  public List<Student> getAllStudents();

  public Student getStudentDetails(int studentId);

  public Student createStudent(Student newStudent);

  public List<Student> getClassRoster (int classId);

  public List<Student> getGroupRoster (int groupId);

  public List<Student> editClassRoster (List<Student> roster, int classId);

  public List<Student> editGroupRoster (List<Student> roster, int groupId);

  public List<Student> getAllStudentsByTeacher(int teacherId);








  }
