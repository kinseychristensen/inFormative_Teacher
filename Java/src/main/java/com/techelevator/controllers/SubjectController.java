package com.techelevator.controllers;

import com.techelevator.dao.SubjectDao;
import com.techelevator.models.Lessons;
import com.techelevator.models.Subject;
import com.techelevator.models.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class SubjectController {

    @Autowired
    private SubjectDao subjectDao;
    private final String BASE_URL = "/subject";

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path=BASE_URL + "/class/{classId}/new", method = RequestMethod.POST)
    public int createSubject (@RequestBody Subject subject, @PathVariable int classId){
        return subjectDao.createSubject(subject, classId);
    }

    @RequestMapping(path=BASE_URL + "/{subjectId}", method = RequestMethod.GET)
    public Subject getSubjectDetails (@PathVariable int subjectId){
        return subjectDao.getSubjectDetails(subjectId);
    }
    @RequestMapping(path=BASE_URL + "/class/{classId}", method = RequestMethod.GET)
    public List<Subject> getClassSubjects (@PathVariable int classId){
        return subjectDao.getClassSubjects(classId);
    }

    @RequestMapping(path=BASE_URL + "/class/{classId}/archived-subjects", method = RequestMethod.GET)
    public List<Subject> getArchivedClassSubjects (@PathVariable int classId){
        return subjectDao.getArchivedClassSubjects(classId);
    }

    @RequestMapping(path=BASE_URL + "/all", method = RequestMethod.GET)
    public List<Subject> getAllSubjects (){
        return subjectDao.getAllSubjects();
    }
    @RequestMapping(path=BASE_URL + "/{subjectId}/{classId}/archive", method = RequestMethod.PUT)
    public boolean archiveSubject (@PathVariable int subjectId, @PathVariable int classId){
        return subjectDao.archiveSubject(subjectId, classId);
    }
    @RequestMapping(path=BASE_URL + "/{subjectId}/{classId}/add/{color}", method = RequestMethod.PUT)
    public boolean addSubject (@PathVariable int subjectId, @PathVariable int classId, @PathVariable int color){
        return subjectDao.addSubject(subjectId, classId, color);
    }


}
