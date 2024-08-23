package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.models.SchoolGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSchoolGroupDao implements SchoolGroupDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcSchoolGroupDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

@Override
public SchoolGroup getGroupDetails(int groupId) {
    try {
        String sql = "SELECT * FROM school_group where group_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, groupId);

        if(rs.next()){
            return mapRowToGroup(rs);
        }else {
            return null;
        }
    }catch (DataAccessException e) {
        throw new DaoException("Error retrieving student details", e);
    }
}

@Override
public List<SchoolGroup> getCurrentGroups (int classId){
    try {
        String sql = "SELECT * FROM school_group WHERE class_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, classId);
        List<SchoolGroup> groups = new ArrayList<>();
        while(rs.next()){
           groups.add(mapRowToGroup(rs));
        }
        return groups;
    }catch (DataAccessException e) {
        throw new DaoException("Error retrieving group details", e);
    }
}

@Override
public int createGroup(SchoolGroup newGroup) {
    int groupId = 0;
    try {
        String sql = "INSERT INTO school_group (group_name, description, class_id, subject_id, color) \n" +
                "\tVALUES (?, ?, ?, ?, ?)\n" +
                "\tRETURNING group_id;";

        groupId = jdbcTemplate.queryForObject(sql, int.class, newGroup.getGroupName(),
                newGroup.getDescription(), newGroup.getClassId(), newGroup.getSubjectId(), newGroup.getColor());
        return groupId;
    } catch (DataAccessException e) {
        throw new DaoException("Error creating group", e);

    }
}

@Override
public boolean deleteGroup(int groupId){
        try {
            String sql = "DELETE FROM student_to_group WHERE group_id = ?";
            jdbcTemplate.update(sql, groupId);
            sql = "DELETE FROM school_group WHERE group_id = ?";
            jdbcTemplate.update(sql, groupId);

            return true;
        }catch(DataAccessException e) {
            throw new DaoException("Error retrieving updating class details", e);
        }
}

@Override
public SchoolGroup editGroup(SchoolGroup editGroup) {
    try {
        String sql = "UPDATE school_group \n" +
                "\tSET group_name = ?, description = ?, class_id=?, subject_id = ?, color = ?\n" +
                "\tWHERE group_id = ?;";
        jdbcTemplate.update(sql, editGroup.getGroupName(), editGroup.getDescription(),
                editGroup.getClassId(), editGroup.getSubjectId(), editGroup.getColor(), editGroup.getGroupId());
        sql = "SELECT * FROM school_group WHERE group_id = ?;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, editGroup.getGroupId());

        if(rs.next()){
            return mapRowToGroup(rs);
        }else {
            return null;
        }
    }catch (DataAccessException e) {
        throw new DaoException("Error retrieving updating class details", e);
    }
}





    private SchoolGroup mapRowToGroup(SqlRowSet rs){
        SchoolGroup schoolGroup = new SchoolGroup();
        schoolGroup.setGroupId(rs.getInt("group_id"));
        schoolGroup.setGroupName(rs.getString("group_name"));
        schoolGroup.setDescription(rs.getString("description"));
        schoolGroup.setClassId(rs.getInt("class_id"));
        schoolGroup.setColor(rs.getInt("color"));
        schoolGroup.setSubjectId(rs.getInt("subject_id"));
        return schoolGroup;

    }
}



