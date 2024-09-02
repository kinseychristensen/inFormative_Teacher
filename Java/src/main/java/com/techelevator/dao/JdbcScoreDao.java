package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.models.Artifact;
import com.techelevator.models.Score;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcScoreDao implements ScoreDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcScoreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addScores(List<Score> scores) {
        try {
                String sql = "INSERT INTO scores (student_id, artifact_id, score, comments, is_waived)" +
                        "VALUES (?,?,?,?,?);";
                for(Score score : scores) {
                    if ((score.getScore()).signum() != -1) {
                        jdbcTemplate.update(sql, score.getStudentId(), score.getArtifactId(),
                                score.getScore(), score.getComments(), score.getWaived());
                    }
                }
                }catch(DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
return true;
    }

    @Override
    public boolean editScores(List<Score> scores) {
        for(Score score : scores){
            try{
                String sql = "SELECT * FROM scores WHERE student_id = ? AND artifact_id = ?;";
                SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, score.getStudentId(), score.getArtifactId());
                Score currentScore = null;
                while(rs.next()){
                    currentScore = mapRowToScore(rs);
                }
                if(currentScore != null){
                    sql = "UPDATE scores SET score=?, comments = ?, is_waived =? WHERE student_id =? AND artifact_id = ?;";
                    jdbcTemplate.update(sql, score.getScore(), score.getComments(), score.getWaived(),
                            score.getStudentId(), score.getArtifactId());
                }else {
                    sql = "INSERT INTO scores (student_id, artifact_id, score, comments, is_waived) " +
                            "VALUES (?, ?, ?, ?, ?);";
                    jdbcTemplate.update(sql, score.getStudentId(), score.getArtifactId(), score.getScore(),
                            score.getComments(), score.getWaived());
                }

            }catch(DataAccessException e) {
            throw new DaoException("Error retrieving student details", e);
        }
        }//end for
        return false;
    }

    @Override
    public List<Score> getStudentScoresBySubjectId(int studentId, List<Artifact> artifacts) {
       List<Score> scores = new ArrayList<>();
       String sql;
       try {
           for(Artifact artifact : artifacts){
               sql = "SELECT * FROM scores WHERE student_id = ? AND artifact_id = ?";
               SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, studentId, artifact.getId());
               while(rs.next()){
                   Score score = mapRowToScore(rs);
                   scores.add(score);
               }
           }
       }catch (DataAccessException e) {
           throw new DaoException("Error retrieving class details", e);
       }
        return scores;
    }

    @Override
    public Score getTopScoreByLessonAndStudentId(int studentId, int lessonId) {
       Score score = new Score();
        String sql;
        try {

                sql = "SELECT * FROM scores\n" +
                        "JOIN artifacts\n" +
                        "ON scores.artifact_id = artifacts.artifact_id\n" +
                        "WHERE student_id = ? AND lesson = ? AND is_waived = false\n" +
                        "ORDER BY score DESC LIMIT 1;";
                SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, studentId, lessonId);
                while(rs.next()){
                    score = mapRowToScore(rs);

            }
        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return score;
    }

    @Override
    public List<Score> getScoresByArtifact(int artifactId) {
        List<Score> scores = new ArrayList<>();
        String sql;
        try {
                sql = "SELECT * FROM scores WHERE artifact_id = ?";
                SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, artifactId);
                while(rs.next()){
                    Score score = mapRowToScore(rs);
                    scores.add(score);
                }

        }catch (DataAccessException e) {
            throw new DaoException("Error retrieving class details", e);
        }
        return scores;
    }

    private Score mapRowToScore(SqlRowSet rs){
        Score score = new Score();
        score.setStudentId(rs.getInt("student_id"));
        score.setArtifactId(rs.getInt("artifact_id"));
        score.setScore(rs.getBigDecimal("score"));
        score.setComments(rs.getString("comments"));
        score.setWaived(rs.getBoolean("is_waived"));
        return score;
    }

}
