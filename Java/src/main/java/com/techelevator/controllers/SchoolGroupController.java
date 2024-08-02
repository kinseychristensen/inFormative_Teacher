package com.techelevator.controllers;

import com.techelevator.dao.SchoolGroupDao;
import com.techelevator.dao.StudentDao;
import com.techelevator.models.SchoolGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public SchoolGroup getGroupDetails (@PathVariable int groupId){
        SchoolGroup thisGroup = schoolGroupDao.getGroupDetails(groupId);
        thisGroup.setStudents(studentDao.getGroupRoster(groupId));
        return thisGroup;
    }

    @RequestMapping(path = BASE_URL + "/class/{classId}", method = RequestMethod.GET)
    public List<SchoolGroup> getCurrentGroups (@PathVariable int classId){
     List<SchoolGroup> currentGroups = schoolGroupDao.getCurrentGroups(classId);

    for(SchoolGroup group : currentGroups){
        group.setStudents(studentDao.getGroupRoster(group.getGroupId()));
    }
    return currentGroups;
    }

    @RequestMapping(path = BASE_URL + "/{classId}/archive", method = RequestMethod.GET)
    public List<SchoolGroup> getArchivedGroups (@PathVariable int classId){
        List<SchoolGroup> currentGroups = schoolGroupDao.getArchivedGroups((classId));

        for(SchoolGroup group : currentGroups){
            group.setStudents(studentDao.getGroupRoster(group.getGroupId()));
        }
        return currentGroups;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = BASE_URL + "/new", method = RequestMethod.POST)
    public SchoolGroup createGroup(@RequestBody SchoolGroup newGroup){
    int groupId = schoolGroupDao.createGroup(newGroup);
    newGroup.setGroupId(groupId);
    studentDao.editGroupRoster(newGroup.getStudents(), newGroup.getGroupId());
    newGroup.setStudents(studentDao.getGroupRoster(groupId));

    return newGroup;
    }

@RequestMapping(path= BASE_URL + "/{groupId}", method = RequestMethod.PUT)
    public SchoolGroup editGroup(@RequestBody SchoolGroup editGroup){
    SchoolGroup thisGroup = schoolGroupDao.editGroup(editGroup);
    studentDao.editGroupRoster(editGroup.getStudents(), thisGroup.getGroupId());
    thisGroup.setStudents(studentDao.getGroupRoster(thisGroup.getGroupId()));
return thisGroup;
}



}
