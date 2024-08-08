package com.techelevator.controllers;

import com.techelevator.dao.ArtifactsDao;
import com.techelevator.models.Artifact;
import com.techelevator.models.ArtifactType;
import com.techelevator.security.dao.UserDao;
import com.techelevator.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ArtifactsController {

    @Autowired
    private ArtifactsDao artifactDao;
    @Autowired
    private UserDao userDao;

    private final String BASE_URL = "/artifacts";

@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(path = BASE_URL + "/new", method = RequestMethod.POST)
    public Artifact createArtifact (@RequestBody Artifact artifact, Principal principal){
    User user = userDao.getUserByUsername(principal.getName());
    int teacherId = user.getId();
    artifact.setTeacherId(teacherId);
    artifact.setAssignmentDate(LocalDate.parse(artifact.getAssignmentDateAsStr()));
    return artifactDao.createArtifact(artifact);
}

    @RequestMapping(path = BASE_URL + "/{artifactId}", method = RequestMethod.PUT)
    public Artifact editArtifact (@RequestBody Artifact artifact, @PathVariable int artifactId){
   artifact.setAssignmentDate(LocalDate.parse(artifact.getAssignmentDateAsStr()));
    return artifactDao.editArtifact(artifact);
    }

    @RequestMapping(path = BASE_URL + "/{artifactId}", method = RequestMethod.DELETE)
    public boolean deleteArtifact (@PathVariable int artifactId){
    return artifactDao.deleteArtifact(artifactId);
    }

    @RequestMapping(path = BASE_URL + "/{artifactId}", method = RequestMethod.GET)
    public Artifact getArtifact(@PathVariable int artifactId){
        return artifactDao.getArtifact(artifactId);
    }

    @RequestMapping(path = "student/{studentId}/artifacts", method = RequestMethod.GET)
    public List<Artifact> getAllArtifactsByStudent (@PathVariable int studentId){
        return artifactDao.getAllArtifactsByStudent(studentId);
    }

    @RequestMapping(path = "subject/{subjectId}/artifacts", method = RequestMethod.GET)
    public List<Artifact> getArtifactsBySubject ( @PathVariable int subjectId, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
        return artifactDao.getArtifactsBySubject(teacherId, subjectId);
    }

    @RequestMapping(path = BASE_URL + "/new_type", method = RequestMethod.POST)
    public ArtifactType createNewArtifactType(@RequestBody ArtifactType artifactType){
        return artifactDao.createNewArtifactType(artifactType);
    }

    @RequestMapping(path = BASE_URL + "/types", method = RequestMethod.GET)
    public List<ArtifactType> getArtifactTypes(){
    return artifactDao.getArtifactTypes();
    }


}
