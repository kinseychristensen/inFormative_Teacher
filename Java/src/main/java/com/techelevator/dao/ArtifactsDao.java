package com.techelevator.dao;

import com.techelevator.models.Artifact;
import com.techelevator.models.ArtifactType;

import java.util.List;

public interface ArtifactsDao {

    public Artifact createArtifact (Artifact artifact);

    public Artifact editArtifact (Artifact artifact);

    public boolean deleteArtifact (int artifactId);

    public Artifact getArtifact(int artifactId);

    public List<Artifact> getAllArtifactsByStudent (int studentId);

    public List<Artifact> getArtifactsBySubject (int teacherId, int subjectId);

    public ArtifactType createNewArtifactType (ArtifactType artifactType);

    public List<ArtifactType> getArtifactTypes ();








}
