package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.models.Lessons;
import com.techelevator.models.Subject;
import com.techelevator.models.Topics;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcSubjectsDao implements  SubjectDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcSubjectsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createSubject(Subject subject, int classId) {
        try{
            String sql = "INSERT INTO subjects (code, description) \n" +
                    "\tVALUES (?, ?)\n" +
                    "\tRETURNING subject_id;";
            int subjectId = jdbcTemplate.queryForObject(sql, Integer.class, subject.getCode(), subject.getDescription());
            sql = "INSERT INTO class_to_subject (subject_id, class_id) \n" +
                    "\tVALUES (?, ?);";
            jdbcTemplate.update(sql, subjectId, classId);
            List<Topics> topicsList = subject.getTopics();
            if(topicsList != null) {
                for (Topics topics : topicsList) {
                    if(!topics.getCode().isEmpty()){
                    sql = "INSERT INTO topics (subject_id, code, description) VALUES (?, ?, ?) RETURNING topic_id;";
                    int topicId = jdbcTemplate.queryForObject(sql, Integer.class, subjectId, topics.getCode(), topics.getDescription());
                    List<Lessons> lessons = topics.getLessons();
                    if(lessons != null) {
                        for (Lessons lesson : lessons) {
                            if (!lesson.getCode().isEmpty()) {

                                sql = "INSERT INTO lessons (topic_id, code, description) \n" +
                                        "\tVALUES (?, ?, ?);";
                                jdbcTemplate.update(sql, topicId, lesson.getCode(), lesson.getDescription());
                            }
                        }
                    }
                    }
                }
            }
            return subjectId;
        }catch(DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
    }



    @Override
    public Subject getSubjectDetails(int subjectId) {
        Subject subject = new Subject();
        String sql = "";
        try {
            sql = "SELECT * FROM subjects WHERE subject_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, subjectId);
            if (rs.next()) {
                subject = mapRowToSubject(rs);
                sql = " SELECT * FROM topics WHERE subject_id = ?;";
                rs = jdbcTemplate.queryForRowSet(sql, subjectId);
                List<Topics> topics = new ArrayList<>();
                while (rs.next()) {
                    topics.add(mapRowToTopics(rs));
                }
                List<Lessons> lessons = new ArrayList<>();
                for (Topics topic : topics) {
                    sql = "SELECT * FROM lessons WHERE topic_id = ?;";
                    rs = jdbcTemplate.queryForRowSet(sql, topic.getId());
                    while (rs.next()) {
                        lessons.add(mapRowToLesson(rs));
                    }
                    topic.setLessons(lessons);
                }
                subject.setTopics(topics);

            }//end of if

                }catch(DataAccessException e){
                    throw new DaoException("Error retrieving subject details", e);
                }
                return subject;
            }

    @Override
    public List<Subject> getClassSubjects(int classId) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql= "SELECT * FROM class_to_subject WHERE class_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, classId);
            List<Integer> subjectIds = new ArrayList<>();
            while(rs.next()){
                subjectIds.add(rs.getInt("subject_id"));
            }
            for(int subjectId: subjectIds){
                subjects.add(getSubjectDetails(subjectId));
            }

        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving subject details", e);
        }

        return subjects;
    }

    @Override
    public List<Subject> getArchivedClassSubjects(int classId) {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql= "SELECT * FROM class_to_subject_archived WHERE class_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, classId);
            List<Integer> subjectIds = new ArrayList<>();
            while(rs.next()){
                subjectIds.add(rs.getInt("subject_id"));
            }
            for(int subjectId: subjectIds){
                subjects.add(getSubjectDetails(subjectId));
            }

        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving archived subject details", e);
        }

        return subjects;
    }


    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql= "SELECT * FROM subjects;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
            while(rs.next()){
                Subject newSubject = mapRowToSubject(rs);
               newSubject = getSubjectDetails(newSubject.getId());
               subjects.add(newSubject);
            }

        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving subject details", e);
        }
        return subjects;

    }

    @Override
    public boolean archiveSubject(int subjectId, int classId) {
    try{
        String sql = "DELETE FROM class_to_subject WHERE class_id = ? AND subject_id = ?;";
        jdbcTemplate.update(sql, classId, subjectId);
        sql = "INSERT INTO class_to_subject_archived (class_id, subject_id) VALUES (?, ?);";
        jdbcTemplate.update(sql, classId, subjectId);
    }catch (DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
return true;
    }

    @Override
    public boolean addSubject(int subjectId, int classId) {
        try{
            String sql = "INSERT INTO class_to_subject (class_id, subject_id) VALUES (?, ?);";
            jdbcTemplate.update(sql, classId, subjectId);
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
        return true;
    }


    private Lessons mapRowToLesson(SqlRowSet rs){
        Lessons lesson = new Lessons();
        lesson.setId(rs.getInt("lesson_id"));
        lesson.setCode(rs.getString("code"));
        lesson.setDescription(rs.getString("description"));
        return lesson;
    }


    private Topics mapRowToTopics(SqlRowSet rs){
        Topics topic = new Topics();
        topic.setCode(rs.getString("code"));
        topic.setId(rs.getInt("topic_id"));
        topic.setDescription(rs.getString("description"));
        return topic;
    }

    private Subject mapRowToSubject(SqlRowSet rs){
        Subject subject = new Subject();
        subject.setId(rs.getInt("subject_id"));
        subject.setCode(rs.getString("code"));
        subject.setDescription(rs.getString("description"));
        subject.setNotAttempted(rs.getInt("not_attempted"));
        subject.setBelow(rs.getInt("below"));
        subject.setApproaching(rs.getInt("approaching"));
        subject.setProficient(rs.getInt("proficient"));
        subject.setMastered(rs.getInt("mastered"));
        return subject;

    }


}
