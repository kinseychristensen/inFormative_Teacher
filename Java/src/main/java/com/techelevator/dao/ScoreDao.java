package com.techelevator.dao;

import com.techelevator.models.Artifact;
import com.techelevator.models.Score;

import java.util.List;

public interface ScoreDao {

    public boolean addScores (List<Score> scores);

    public boolean editScores (List<Score> scores);

    public List<Score> getStudentScoresBySubjectId (int studentId, List<Artifact> artifacts);

    public List<Score> getScoresByArtifact (int artifactId);

}
