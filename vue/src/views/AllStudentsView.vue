<template>

    <h1 class="page-title">All Students</h1>
 
    <p class="description">Click on a student name to go to their profile page.</p>


<div class="loading" v-if="isLoading">Loading...</div>

<div v-else>

  <div class="roster-display"> 

      <router-link v-for="student in students" v-bind:key="student.id"
          v-bind:to="{name: 'student', params: {studentId: student.id}}" class="student-title">
              {{ student.firstName }} {{ student.lastName }} 
          </router-link></div>

      </div>


</template>

<script>


import StudentService from '../services/StudentService';


export default {
name: 'AllStudentView',

data() {
return {
  isLoading: false,
  students: [],
  nextClass: [],
 
  
};
},

methods: {
handleError(error, verb) {
    if (error.response) {
      this.$store.commit('SET_NOTIFICATION',
        "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
    } else if (error.request) {
      this.$store.commit('SET_NOTIFICATION', "Error " + verb + " class roster. Server could not be reached.");
    } else {
      this.$store.commit('SET_NOTIFICATION', "Error " + verb + " class roster. Request could not be created.");
    }
  },




async retrieveStudents(){
try {
this.isLoading = true;
const response = await StudentService.getAllStudentsByTeacher();
this.students = response.data;
}catch (error) {
this.handleError(error, 'retrieving');
}finally {
this.isLoading = false;
}
}, 
},


created(){
  this.isLoading=true;
  this.retrieveStudents();

}
}




</script>

<style scoped>
.button-link{
background-color: #a4c2f4ff;
color: black;
text-align: center;
margin: 2px;
padding: 10px;
border-radius: 15px;
text-decoration: none;
width: 200px;
}


.form{
grid-area: class;
}


.roster-display{
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}
.student-title{
color: black;
text-align: center;
margin: 2px;
padding: 10px;
border-radius: 15px;
text-decoration: none;
font-size: larger;
min-width: 100px;
max-width: 200px;
background-color: #93caef;

}




</style>