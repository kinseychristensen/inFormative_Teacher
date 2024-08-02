package com.techelevator.dao;

import com.techelevator.models.Lessons;
import com.techelevator.models.Subject;
import com.techelevator.models.Topics;
import java.util.List;

public interface SubjectDao {

    public int createSubject (Subject subject, int classId);
    public Subject getSubjectDetails (int subjectId);
    public List<Subject> getClassSubjects (int classId);
    public List<Subject> getArchivedClassSubjects (int classId);
    public List<Subject> getAllSubjects ();
    public boolean archiveSubject (int subjectId, int classId);
    public boolean addSubject (int subjectId, int classId);
    public boolean createLessons (List<Lessons> lessons, int topicId);
    public int createTopic(Topics topic, int subjectId);


}
