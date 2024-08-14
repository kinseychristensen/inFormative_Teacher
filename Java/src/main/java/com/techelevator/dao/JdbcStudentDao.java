package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.models.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class JdbcStudentDao implements  StudentDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT * FROM students ORDER BY last_name, first_name;";
            SqlRowSet rs  = jdbcTemplate.queryForRowSet(sql);

            while (rs.next()){
                students.add(mapRowToStudent(rs));
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        } return students;
    }

    @Override
    public Student getStudentDetails(int studentId) {
        try {
            String sql = "SELECT student_id, first_name, last_name, school_id \n" +
                    "FROM students \n" +
                    "WHERE student_id=?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, studentId);

            if (rs.next()){
                return mapRowToStudent(rs);
            }else {
                return null;
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
    }

    @Override
    public Student createStudent(Student newStudent) {
        try {

            String sql = "INSERT INTO students (last_name, first_name, school_id) VALUES (?, ?, ?) RETURNING student_id;";

           int newId;
            newId = jdbcTemplate.queryForObject(sql, Integer.class, newStudent.getLastName(), newStudent.getFirstName(), newStudent.getSchoolId());
            sql = "SELECT * from students where student_id = ?;";

            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, newId);

            if (rs.next()){
                return mapRowToStudent(rs);
            }else {
                return null;
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error creating student", e);
        }
    }

    @Override
    public List<Student> getClassRoster (int classId){
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT students.student_id, students.last_name, students.first_name, students.school_id \n" +
                    "FROM students \n" +
                    "JOIN student_to_class\n" +
                    "ON students.student_id = student_to_class.student_id\n" +
                    "WHERE class_id = ? " +
                    "ORDER BY students.last_name, students.first_name;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, classId);

            while (rs.next()){
                Student thisStudent = mapRowToStudent(rs);
                students.add(thisStudent);
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error updating student details", e);
        }
        return students;
    }
@Override
    public List<Student> getGroupRoster (int groupId) {
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT students.student_id, students.last_name, students.first_name, students.school_id \n" +
                    "FROM students \n" +
                    "JOIN student_to_group\n" +
                    "ON students.student_id = student_to_group.student_id\n" +
                    "WHERE group_id = ?" +
                    "ORDER BY students.last_name, students.first_name;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, groupId);

            while (rs.next()){
                Student thisStudent = mapRowToStudent(rs);
                students.add(thisStudent);
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error updating student details", e);
        }
        return students;
    }

    @Override
    public List<Student> editClassRoster (List<Student> roster, int classId){


            try {
                String sql = "DELETE FROM student_to_class where class_id = ?;";
                jdbcTemplate.update(sql, classId);
                List<Student> newRoster = new ArrayList<>();
                for (Student student : roster) {
                    sql = "SELECT * FROM students WHERE school_id = ? AND last_name = ? AND first_name = ?;";
                    SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, student.getSchoolId(), student.getLastName(),
                            student.getFirstName());
                    if (!rs.next()) {
                        student = createStudent(student);
                    } else {
                        student = mapRowToStudent(rs);
                    }
                    if (!(newRoster.contains(student))) {
                        newRoster.add(student);
                    }
                }
                for (Student student : newRoster) {
                    sql = "INSERT INTO student_to_class(student_id, class_id) VALUES (?, ?);";
                    jdbcTemplate.update(sql, student.getId(), classId);
                }
            }catch (DataAccessException e) {
            throw new DaoException("Error updating class roster", e);
        }
        return getClassRoster(classId);
    }

@Override
    public List<Student> editGroupRoster (List<Student> roster, int groupId){

        try {
            String sql = "DELETE from student_to_group where group_id = ?;";
            jdbcTemplate.update(sql, groupId);
List<Student> newRoster = new ArrayList<>();
            for (Student student : roster) {

                sql = "SELECT * FROM students WHERE school_id = ? AND last_name = ? AND first_name = ?;";
                SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, student.getSchoolId(), student.getLastName(),
                        student.getFirstName());
                if (!rs.next()) {
                    student = createStudent(student);
                } else {
                    student = mapRowToStudent(rs);
                    if (!(newRoster.contains(student))) {
                        newRoster.add(student);
                    }
                }
            }
                for (Student student : newRoster) {
                    sql = "INSERT INTO student_to_group (student_id, class_id) VALUES (?, ?);";
                    jdbcTemplate.update(sql, student.getId(), groupId);
                }
        }catch (DataAccessException e) {
            throw new DaoException("Error updating student details", e);
        }
        return getGroupRoster(groupId);
    }

    private Student mapRowToStudent(SqlRowSet rs){
        Student student = new Student();
        student.setId(rs.getInt("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setSchoolId(rs.getInt("school_id"));
        return student;
    }


}
