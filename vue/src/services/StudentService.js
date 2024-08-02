import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {

    getStudentDetails(studentId){
        return axios.get(`/student/${studentId}`);
    },

    getClassRoster(classId){
        return axios.get(`/class/${classId}/roster`);
    }, 

    getGroupRoster(groupId){
        return axios.get(`/group/${groupId}/roster`);
    }, 

    getAllStudents(){
        return axios.get("/studnet/all");
    },

    editClassRoster(students, classId){
        return axios.put(`/class/${classId}/roster`, students);
    },

    editGroupRoster(students, groupId){
        return axios.put(`/group/${groupId}/roster`, students);
    }




}