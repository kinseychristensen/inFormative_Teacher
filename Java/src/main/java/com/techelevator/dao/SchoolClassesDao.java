package com.techelevator.dao;

import com.techelevator.models.SchoolClass;
import java.util.List;

public interface SchoolClassesDao {

    public SchoolClass getClassDetails(int classId);

    public List<SchoolClass> getCurrentClasses(int teacherId);

    public List<SchoolClass> getArchivedClasses(int teacherId);

    public int createClass(SchoolClass newClass, int teacherId);

    public SchoolClass editClass(SchoolClass editClass);





}
