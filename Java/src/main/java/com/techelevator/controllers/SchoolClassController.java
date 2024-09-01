package com.techelevator.controllers;

import com.techelevator.dao.SchoolClassesDao;
import com.techelevator.dao.StudentDao;
import com.techelevator.dao.SubjectDao;
import com.techelevator.models.SchoolClass;
import com.techelevator.security.dao.UserDao;
import com.techelevator.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class SchoolClassController{

@Autowired
    private  SchoolClassesDao schoolClassesDao;
@Autowired
    private UserDao userDao;
@Autowired
    private StudentDao studentDao;
@Autowired
private SubjectDao subjectDao;

    private final String BASE_URL = "/class";


@RequestMapping(path= BASE_URL + "/{classId}", method = RequestMethod.GET)
    public SchoolClass getClassDetails(@PathVariable int classId){
    return schoolClassesDao.getClassDetails(classId);
};

    @RequestMapping(path= BASE_URL, method = RequestMethod.GET)
    public List<SchoolClass> getCurrentClasses(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
        return schoolClassesDao.getCurrentClasses(teacherId);
    };

    @RequestMapping(path= BASE_URL + "/archive", method = RequestMethod.GET)
    public List<SchoolClass> getArchivedClasses(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
        return schoolClassesDao.getCurrentClasses(teacherId);
    };
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path= BASE_URL + "/new", method = RequestMethod.POST)
    public int createClass(@Valid @RequestBody SchoolClass newClass, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
        return schoolClassesDao.createClass(newClass, teacherId);
    };

    @RequestMapping(path= BASE_URL + "/{classId}", method = RequestMethod.PUT)
    public SchoolClass editClass(@RequestBody SchoolClass editClass){
        return schoolClassesDao.editClass(editClass);
    };
    @RequestMapping(path= BASE_URL + "/student/{studentId}", method = RequestMethod.GET)
    public List<SchoolClass> getClassesByStudentId(@PathVariable int studentId){
        return schoolClassesDao.getClassesByStudentId(studentId);
    };







}
