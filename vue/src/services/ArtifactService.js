import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {

    createArtifact(newArtifact){
        return axios.post("/artifacts/new", newArtifact);
    },

    editArtifact(editArtifact, artifactId){
        return axios.put(`/artifacts/${artifactId}`, editArtifact);
    },

    deleteArtifact(artifactId){
        return axios.delete(`/artifacts/${artifactId}`);
    },

    getArtifact(artifactId){
        return axios.get(`/artifacts/${artifactId}`);
    },

    getAllArtifactsByStudent(studentId){
        return axios.get(`/student/${studentId}/artifacts`);
    },

    getAllArtifactsBySubject(subjectId){
        return axios.get(`/subject/${subjectId}/artifacts`);
    },
    getAllArtifactsByLesson(lessonId){
        return axios.get(`/lesson/${lessonId}/artifacts`);
    },
    
    createNewArtifactType(artifactType){
        return axios.post("/artifacts/new_type", artifactType);
    },

    getArtifactTypes(){
        return axios.get("/artifacts/types");
    }

}