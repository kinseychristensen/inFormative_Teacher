package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.models.Artifact;
import com.techelevator.models.ArtifactType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcArtifactsDao implements ArtifactsDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcArtifactsDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO: if vue will send date as string, we good.  otherwise may need to troubleshoot
    @Override
    public Artifact createArtifact(Artifact artifact) {

        try {
            String sql = "INSERT INTO artifacts (artifact_type, description, lesson, teacher_id, assignment_date) " +
                    "VALUES (?, ?, ?, ?, ? ) " +
                    "RETURNING artifact_id;";
            int artifact_id = jdbcTemplate.queryForObject(sql, int.class, artifact.getArtifactTypeInt(), artifact.getDescription(),
            artifact.getLessonId(), artifact.getTeacherId(), artifact.getAssignmentDate());

            return getArtifact(artifact_id);

        }catch(DataAccessException e) {
            throw new DaoException("Error creating artifact", e);
        }
    }


    @Override
    public Artifact editArtifact(Artifact artifact) {
        try {
            String sql = "\n" +
                    "UPDATE artifacts SET artifact_type = ?, description = ?, lesson = ?, assignment_date = ?, comments=?\n" +
                    "\tWHERE artifact_id = ?;";
            jdbcTemplate.update(sql, artifact.getArtifactTypeInt(), artifact.getDescription(),
                    artifact.getLessonId(),artifact.getAssignmentDate(),
                    artifact.getComments(), artifact.getId());

            return getArtifact(artifact.getId());


        }catch(DataAccessException e) {
            throw new DaoException("Error updating artifact", e);
        }
    }

    @Override
    public boolean deleteArtifact(int artifactId) {
        try {

            String sql = "DELETE FROM scores WHERE artifact_id = ?; ";
            jdbcTemplate.update(sql, artifactId);

            sql = "DELETE FROM artifacts WHERE artifact_id = ?; ";
            jdbcTemplate.update(sql, artifactId);

        }catch (DataAccessException e) {
            throw new DaoException("Error deleting artifact", e);
        }
        return true;
    }

    @Override
    public Artifact getArtifact(int artifactId) {
        Artifact artifact = new Artifact();
        try {
            String sql = "SELECT * FROM artifacts WHERE artifact_id = ?;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, artifactId);

            if(rs.next()){
             artifact = mapRowToArtifact(rs);
                sql = "SELECT description FROM artifact_type WHERE code = ?;";
                artifact.setArtifactType(jdbcTemplate.queryForObject(sql, String.class, artifact.getArtifactTypeInt()));
            }

        }catch (DataAccessException e) {
            throw new DaoException("Error creating group", e);
        }
      return artifact;
    }

    @Override
    public List<Artifact> getAllArtifactsByStudent(int studentId) {
        List<Artifact> artifacts = new ArrayList<>();
        try {
            String sql = "SELECT artifacts.artifact_id FROM artifacts\n" +
                    "JOIN scores ON artifacts.artifact_id = scores.artifact_id\n" +
                    "\tWHERE scores.student_id = ? AND scores.score >= 0" +
                    "ORDER BY artifacts.assignment_date;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, studentId);
            while (rs.next()){
                int thisArtifactId = rs.getInt("artifact_id");
                artifacts.add(getArtifact(thisArtifactId));

            }
        }catch (DataAccessException e) {
        throw new DaoException("Error retrieving class details", e);
    }
        return artifacts;
    }


    @Override
    public List<Artifact> getArtifactsBySubject(int teacherId, int subjectId) {
        List<Artifact> artifacts = new ArrayList<>();
        try {
            String sql = "select * \n" +
                    "from artifacts\n" +
                    "LEFT JOIN lessons\n" +
                    " ON artifacts.lesson = lessons.lesson_id\n" +
                    "LEFT JOIN topics\n" +
                    "ON lessons.topic_id = topics.topic_id\n" +
                    "LEFT JOIN subjects\n" +
                    "ON topics.subject_id = subjects.subject_id\n" +
                    "WHERE subjects.subject_id = ? AND artifacts.teacher_id = ?\n" +
                    "ORDER BY artifacts.assignment_date DESC;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, subjectId, teacherId);
            while (rs.next()){
                int thisArtifactId = rs.getInt("artifact_id");
                artifacts.add(getArtifact(thisArtifactId));


            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return artifacts;
    }

    @Override
    public ArtifactType createNewArtifactType(ArtifactType artifactType) {
        try {
            String sql = "INSERT INTO artifact_type (description) VALUES (?) RETURNING code;";
            int code = jdbcTemplate.queryForObject(sql, int.class, artifactType.getDescription());
            artifactType.setCode(code);
            return artifactType;
        }catch(DataAccessException e) {
            throw new DaoException("Error creating new artifact type", e);
        }
    }

    public List<ArtifactType> getArtifactTypes () {
        try {
            String sql = "SELECT * FROM artifact_type;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
            List<ArtifactType> types = new ArrayList<>();

            while (rs.next()) {
                ArtifactType thisType = new ArtifactType();
                thisType.setCode(rs.getInt("code"));
                thisType.setDescription(rs.getString("description"));
                types.add(thisType);
            }

                return types;
            }catch(DataAccessException e) {
            throw new DaoException("Error updating student details", e);
        }
    }

    @Override
    public List<Artifact> getArtifactsByLesson(int teacherId, int lessonId) {
        List<Artifact> artifacts = new ArrayList<>();
        try {
            String sql = "select * \n" +
                    "from artifacts\n" +
                    "WHERE lesson_id = ? AND teacher_id = ?\n" +
                    "ORDER BY assignment_date DESC;";
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, lessonId, teacherId);
            while (rs.next()){
                int thisArtifactId = rs.getInt("artifact_id");
                artifacts.add(getArtifact(thisArtifactId));


            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return artifacts;
    }


    private Artifact mapRowToArtifact(SqlRowSet rs){
        Artifact artifact = new Artifact();
        artifact.setId(rs.getInt("artifact_id"));
        artifact.setArtifactTypeInt(rs.getInt("artifact_type"));
        artifact.setDescription(rs.getString("description"));
        artifact.setLessonId(rs.getInt("lesson"));
        artifact.setTeacherId(rs.getInt("teacher_id"));
        artifact.setAssignmentDate(rs.getDate("assignment_date").toLocalDate());
        artifact.setComments(rs.getString("comments"));
        return artifact;

    }
}
