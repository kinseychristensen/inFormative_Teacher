<template>

<div class="loading" v-if="isLoading">Loading...</div>

<div v-else>

  <div class="roster-display"> 

      <router-link v-for="student in students" v-bind:key="student.id"
          v-bind:to="{name: 'student', params: {studentId: student.id}}" class="student-title">
             {{ student.lastName }},  {{ student.firstName }} ({{ student.schoolId }})    </router-link></div>

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
function: {

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

.roster-display{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 5px;
    margin-top: 5px;
}
.student-title{
    border-radius: 5px;
    padding: 10px;
    width: 200px;
    text-decoration: none;
    color: black;
}
.student-title:nth-child(even){
        background-color: #a4c2f4ff;
    }
    .student-title:nth-child(odd){
        background-color:  #d9d9d9ff;
    }

</style>