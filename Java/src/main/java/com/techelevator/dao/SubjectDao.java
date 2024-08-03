package com.techelevator.dao;

import com.techelevator.models.Lessons;
import com.techelevator.models.Subject;
import com.techelevator.models.Topics;
import java.util.List;

public interface SubjectDao {

     int createSubject (Subject subject, int classId);
     Subject getSubjectDetails (int subjectId);
     List<Subject> getClassSubjects (int classId);
     List<Subject> getArchivedClassSubjects (int classId);
     List<Subject> getAllSubjects ();
     boolean archiveSubject (int subjectId, int classId);
     boolean addSubject (int subjectId, int classId);



}
