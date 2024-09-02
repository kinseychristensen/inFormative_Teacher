import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {

addScores(scores){
    return axios.post("/scores", scores);
},
editScores(scores){
    return axios.put("/scores", scores);
},
getStudentScoresBySubjectId(subjectId, studentId){
    return axios.get(`/student/${studentId}/scores/${subjectId}`);
},
getClassScoresBySubject(classId, subjectId){
    return axios.get(`/class/${classId}/subject/${subjectId}/scores`);
},

getScoresByArtifact(artifactId){
    return axios.get(`/scores/${artifactId}`);
},
getTopScore(studentId, lessonId){
    return axios.get(`/scores/student/${studentId}/lesson/${lessonId}`);
}
}