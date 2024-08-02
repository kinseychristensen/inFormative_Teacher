import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:9000"
});

export default {

getGroupDetails(groupId){
    return axios.get(`/group/${groupId}`);
},

getCurrentGroups(classId){
    return axios.get(`/group/class/${classId}`);
},

getArchivedGroups(classId) {
    return axios.get(`/group/class/${classId}`);
},

createGroup(newGroup){
    return axios.post("/group/new", newGroup);
},
editGroup(editGroup, groupId){
    return axios.put(`/group/${groupId}`, editGroup);
}

}