package com.techelevator.controllers;

import com.techelevator.dao.StudentDao;
import com.techelevator.models.Student;
import com.techelevator.security.dao.UserDao;
import com.techelevator.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class StudentController {

    @Autowired
    private StudentDao studentDao;
    private final String BASE_URL = "/student";
    @Autowired
    private UserDao userDao;

@RequestMapping(path = BASE_URL + "/{studentId}", method = RequestMethod.GET)
    public Student getStudentDetails(@PathVariable int studentId){
        return studentDao.getStudentDetails(studentId);
    }

    @RequestMapping(value = "/class/{classId}/roster", method = RequestMethod.GET)
    public List<Student> getClassRoster (@PathVariable int classId){
        return studentDao.getClassRoster(classId);
    }
@RequestMapping(path = "/group/{groupId}/roster", method = RequestMethod.GET)
    public List<Student> getGroupRoster (@PathVariable int groupId){
        return studentDao.getGroupRoster(groupId);
    }

    @RequestMapping(value = "/class/{classId}/roster", method = RequestMethod.PUT)
    public List<Student> editClassRoster (@RequestBody List<Student> students, @PathVariable int classId){
        return studentDao.editClassRoster(students,classId);
    }
    @RequestMapping(path = "/group/{groupId}/roster", method = RequestMethod.PUT)
    public List<Student> editGroupRoster (@RequestBody List<Student> students, @PathVariable int groupId){
        return studentDao.editGroupRoster(students, groupId);
    }

    @RequestMapping(path = BASE_URL + "/all", method = RequestMethod.GET)
    public List<Student> getAllStudents(){
        return studentDao.getAllStudents();
    }

    @RequestMapping(path = BASE_URL + "/all/teacher", method = RequestMethod.GET)
    public List<Student> getAllStudentsByTeacher(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int teacherId = user.getId();
        return studentDao. getAllStudentsByTeacher(teacherId);
    }




}
