package com.techelevator.dao;

import com.techelevator.models.SchoolGroup;
import java.util.List;

public interface SchoolGroupDao {

    public SchoolGroup getGroupDetails (int groupId);

    public List<SchoolGroup> getCurrentGroups (int classId);

    public int createGroup(SchoolGroup newGroup);

    public SchoolGroup editGroup(SchoolGroup editGroup);
    public boolean deleteGroup(int groupId);










}
