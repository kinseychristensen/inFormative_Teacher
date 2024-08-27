import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  },

  getName(){
    return axios.get("/name");
  },

  getId(userName){
    return axios.get(`/id/${userName}`);
  },
  getUser(){
    return axios.get("/user");
  },

  updateUser(user, userId){
    return axios.put(`/update-user/${userId}`, user);
  },

  updatePassword(user, userId){
    return axios.put(`/update-password/${userId}`, user);
  }
}
