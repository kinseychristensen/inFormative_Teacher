import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {

    getClassDetails(classId){
        return axios.get(`/class/${classId}`);
    },

    getCurrentClasses(){
        return axios.get("/class");
    },

    getArchivedClasses() {
        return axios.get("/class/archive");
    },

    createClass(newClass){
        return axios.post("/class/new", newClass);
    },

    editClass(editClass, classId){
        return axios.put(`/class/${classId}`, editClass);
    }

}