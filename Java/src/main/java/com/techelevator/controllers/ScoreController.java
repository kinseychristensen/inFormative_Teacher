package com.techelevator.controllers;

import com.techelevator.dao.ArtifactsDao;
import com.techelevator.dao.ScoreDao;
import com.techelevator.dao.StudentDao;
import com.techelevator.dao.SubjectDao;
import com.techelevator.models.Artifact;
import com.techelevator.models.Score;
import com.techelevator.models.Student;
import com.techelevator.security.dao.UserDao;
import com.techelevator.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ScoreController {


    @Autowired
    private ScoreDao scoreDao;
    @Autowired
    private ArtifactsDao artifactsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StudentDao studentDao;
    private final String BASE_URL = "/scores";

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = BASE_URL, method = RequestMethod.POST)
    public boolean addScores (@RequestBody List<Score> scores){
        return scoreDao.addScores(scores);
    }

    @RequestMapping(path = BASE_URL, method = RequestMethod.PUT)
    public boolean editScores (@RequestBody List<Score> scores){
        return scoreDao.editScores(scores);
    }

    @RequestMapping(path = "/student/{studentId}/scores/{subjectId}", method = RequestMethod.GET)
    public List<Score> getStudentScoresBySubjectId (@PathVariable int studentId, @PathVariable int subjectId, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
       List <Artifact> artifacts = artifactsDao.getArtifactsBySubject(teacherId, subjectId);

        return scoreDao.getStudentScoresBySubjectId(studentId, artifacts);
    }

    @RequestMapping(path = "/class/{classId}/subject/{subjectId}/scores", method = RequestMethod.GET)
    public List<Score> getClassScoresBySubject(@PathVariable int classId, @PathVariable int subjectId, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
        List <Artifact> artifacts = artifactsDao.getArtifactsBySubject(teacherId, subjectId);
        List<Student> students = studentDao.getClassRoster(classId);
        List<Score> scores = new ArrayList<>();
        for(Student student : students){
           List<Score> studentScores = scoreDao.getStudentScoresBySubjectId(student.getId(), artifacts);
            scores.addAll(studentScores);
        }
return scores;
    }

    @RequestMapping(path = BASE_URL + "/{artifactId}", method = RequestMethod.GET)
    public List<Score> getScoresByArtifact (@PathVariable int artifactId){
        return scoreDao.getScoresByArtifact(artifactId);
    }




}
