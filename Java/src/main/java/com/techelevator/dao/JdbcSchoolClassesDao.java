package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.models.SchoolClass;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcSchoolClassesDao implements SchoolClassesDao{

    private final JdbcTemplate jdbcTemplate;
    public JdbcSchoolClassesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public SchoolClass getClassDetails(int classId) {
        try {
            String sql = "SELECT * FROM school_class WHERE class_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, classId);

            if (rs.next()){
                return mapRowToClass(rs);
            }else {
                return null;
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
    }

    @Override
    public List<SchoolClass> getClassesByStudentId(int studentId) {
        List<SchoolClass> classList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM school_class\n" +
                    "JOIN student_to_class\n" +
                    "ON school_class.class_id = student_to_class.class_id\n" +
                    "WHERE student_id = ? \n" +
                    "ORDER BY school_class.class_id;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, studentId);

            while (rs.next()){
                classList.add(mapRowToClass(rs));
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return classList;
    }

    @Override
    public List<SchoolClass> getCurrentClasses(int teacherId) {
        List<SchoolClass> classList = new ArrayList<>();
        try {
            String sql = "SELECT *\n" +
                    "FROM school_class \n" +
                    "JOIN class_to_teacher\n" +
                    "ON school_class.class_id = class_to_teacher.class_id\n" +
                    "WHERE class_to_teacher.teacher_id = ? AND school_class.is_active = true " +
                    "ORDER BY school_class.class_id;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, teacherId);

            while (rs.next()){
                classList.add(mapRowToClass(rs));
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return classList;
    }

    @Override
    public List<SchoolClass> getArchivedClasses(int teacherId) {
        List<SchoolClass> classList = new ArrayList<>();
        try {
            String sql = "SELECT * \n" +
                    "FROM school_class \n" +
                    "JOIN class_to_teacher \n" +
                    "ON school_class.class_id = class_to_teacher.class_id \n" +
                    "WHERE teacher_id = ? AND is_active = false " +
                    "ORDER BY school_class.class_id;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, teacherId);

            while (rs.next()){
                classList.add(mapRowToClass(rs));
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return classList;
    }

    @Override
    public int createClass(SchoolClass newClass, int teacherId) {
        int classId = 0;
        try {
            String sql = "INSERT INTO school_class (description, class_name) \n" +
                    "\tVALUES (?, ?)\n" +
                    "RETURNING class_id;";
            classId = jdbcTemplate.queryForObject(sql,int.class, newClass.getDescription(),
                        newClass.getClassName());

            newClass.setClassId(classId);
            sql = "INSERT INTO class_to_teacher (class_id, teacher_id)\n" +
                    "VALUES (?, ?);";
            jdbcTemplate.update(sql, newClass.getClassId(), teacherId);
            return newClass.getClassId();

        }catch (DataAccessException e) {
            throw new DaoException("Error creating class", e);
        }
    }

    @Override
    public SchoolClass editClass(SchoolClass editClass) {
        try {
            String sql = "UPDATE school_class \n" +
                    "SET description=?, class_name=?, is_active=?\n" +
                    "WHERE class_id = ?;";
            jdbcTemplate.update(sql, editClass.getDescription(), editClass.getClassName(),
                    editClass.isActive(), editClass.getClassId());

            sql = "SELECT * FROM school_class WHERE class_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, editClass.getClassId());

            if (rs.next()){
                return mapRowToClass(rs);
            }else {
                return null;
            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving updating class details", e);
        }
    }



    private SchoolClass mapRowToClass(SqlRowSet rs){
        SchoolClass schoolClass= new SchoolClass();
        schoolClass.setClassId(rs.getInt("class_id"));
        schoolClass.setClassName(rs.getString("class_name"));
        schoolClass.setDescription(rs.getString("description"));
        schoolClass.setActive(rs.getBoolean("is_active"));
        return schoolClass;
    }


}
