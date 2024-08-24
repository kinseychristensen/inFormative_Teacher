package com.techelevator.controllers;

import com.techelevator.dao.SchoolGroupDao;
import com.techelevator.dao.StudentDao;
import com.techelevator.models.SchoolGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class SchoolGroupController {
    @Autowired
    private SchoolGroupDao schoolGroupDao;
    @Autowired
    private StudentDao studentDao;
    private final String BASE_URL = "/group";

    @RequestMapping(path = BASE_URL + "/{groupId}", method = RequestMethod.GET)
    public SchoolGroup getGroupDetails(@PathVariable int groupId) {
        SchoolGroup thisGroup = schoolGroupDao.getGroupDetails(groupId);
        thisGroup.setStudents(studentDao.getGroupRoster(groupId));
        return thisGroup;
    }

    @RequestMapping(path = BASE_URL + "/class/{classId}", method = RequestMethod.GET)
    public List<SchoolGroup> getCurrentGroups(@PathVariable int classId) {
        List<SchoolGroup> groups = schoolGroupDao.getCurrentGroups(classId);
        for(SchoolGroup group : groups){
            group.setStudents(studentDao.getGroupRoster(group.getGroupId()));
        }
        return groups;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = BASE_URL + "/new", method = RequestMethod.POST)
    public int createGroup(@RequestBody SchoolGroup newGroup) {
        return schoolGroupDao.createGroup(newGroup);
    }

    @RequestMapping(path = BASE_URL + "/{groupId}", method = RequestMethod.PUT)
    public SchoolGroup editGroup(@RequestBody SchoolGroup editGroup) {
        return schoolGroupDao.editGroup(editGroup);
    }

    @RequestMapping(path = BASE_URL + "/{groupId}", method = RequestMethod.DELETE)
    public boolean deleteGroup(@PathVariable int groupId) {

        return schoolGroupDao.deleteGroup(groupId);
    }
}
