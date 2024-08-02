import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {

    createSubject(newSubject, classId){
        return axios.post(`/subject/class/${classId}/new`, newSubject);
    },

    getSubjectDetails(subjectId){
        return axios.get(`/subject/${subjectId}`);
    },

    getClassSubjects(classId){
        return axios.get(`/subject/class/${classId}`);
    },

    getArchivedClassSubjects(classId){
        return axios.get(`/subject/class/${classId}/archived-subjects`);
    },

    getAllSubjects(){
        return axios.get("/subject/all");
    },

    archiveSubject(subjectId, classId){
        return axios.put(`/subject/${subjectId}/${classId}/archive`)
    }, 
    addSubject(subjectId, classId){
        return axios.put(`/subject/${subjectId}/${classId}/add`)
    }

}