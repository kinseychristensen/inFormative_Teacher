<template>

    
   
          <h1 class="page-title">Class Roster</h1>
       
          <p class="description">Group rosters can be edited on the group roster page.</p>

      
  
    <div class="form">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else>
      <form v-on:submit.prevent="addStudent" class="add-student-container">
       
        <label for="school-id" class="id-label">Student Id Number:</label> 
        <input type="number" class="school-id" v-model="newStudent.schoolId"/>  
        
        <label for="first-name" class="first-name-label">Student First Name:</label> 
        <input id="first-name" v-model="newStudent.firstName"/>

        <label for="last-name" class="last-name-label">Student Last Name:</label> 
        <input id="last-name" v-model="newStudent.lastName"/>
                                    
        <button id="submit-button" class="button-link">Add Student</button>

        <div class="roster-display"> 

            <div v-if="isRemoving" class="student-flex" >
            <div v-for="student in students" v-bind:key="student.id" class="student-title" @click="removeStudent(student.schoolId)">
                    {{student.schoolId}} - {{ student.firstName }} {{ student.lastName }} 
            </div></div>

                <div v-else class="student-flex">
            <router-link v-for="student in students" v-bind:key="student.id"
                v-bind:to="{name: 'student', params: {studentId: student.id}}" class="student-title">
                    {{student.schoolId}} - {{ student.firstName }} {{ student.lastName }} 
                </router-link></div>


            </div>
      
<button id="finalize" class="button-link" @click="editRoster">Save Changes</button>


<div id="remove-student" class="button-link" @click="toggleRemoving" v-if="this.students.length != 0">{{removingMsg}}</div>



      </form></div></div>

     
 
    </template>
    
    <script>
    
   import { getTransitionRawChildren } from 'vue';
import StudentService from '../services/StudentService';

    
    export default {
      name: 'RosterView',
     
    data() {
      return {
        newStudent: {
          firstName: "",
          lastName: "",
          schoolId: 0,
          id: 0,
        },
        isLoading: false,
        students: [],
        classId: 1,
        isRemoving: false,
        removingMsg: 'Remove Students From Class'
        
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

    addStudent(){
       
        this.students.push({
            firstName: this.newStudent.firstName, 
            lastName: this.newStudent.lastName,
            schoolId: this.newStudent.schoolId,
            id: 0,
        });
        
    },  

    toggleRemoving(){
      this.isRemoving = !this.isRemoving;
      if(this.isRemoving){
          this.removingMsg = "Cancel Removing Students";
      }else {
        this.removingMsg = "Click To Remove Students From Class";
      }
    },

    removeStudent(schoolId){
      const shouldRemove = confirm("Are you sure you want to remove this student?");

      if(shouldRemove){
        let updatedRoster = [];
        for(let i = 0; i<this.students.length; i++){
          if (this.students[i].schoolId != schoolId) {
            updatedRoster.push(this.students[i]);
          }
        }
        this.students = updatedRoster;  
      }
    },


        async editRoster(){
          try {
            this.isLoading=true;
            const response = await StudentService.editClassRoster(this.students, this.classId);
            this.students = response.data;
          }catch (error) {
            this.handleError(error, 'editing');
          }finally {
            this.isLoading = false;
          }
        },

      async retrieveStudents(){
    try {
      this.isLoading = true;
      const response = await StudentService.getClassRoster(this.classId);
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
        const classId = parseInt(this.$route.params.classId);
        this.classId = classId;
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



@media screen and (max-width: 600px) {
      .add-student-container{
       
        display: grid;
        grid-template-columns: 1fr 2fr;
        grid-template-areas: 
        "id-label school-id"
        "first-name-label first-name "
        "last-name-label last-name "
        ". submit"
        "roster roster"
        " remove-student remove-student"
        ". finalize"
       
        ;
        gap: 15px;
      }
      #remove-student{
        grid-area: remove-student;
        width: 100vw;
      }
    }

    @media screen and (min-width: 601px) {
      .add-student-container{
       
        display: grid;
        grid-template-columns: 200px 250px 1fr;
        grid-template-areas: 
        "id-label school-id remove-student"
        "first-name-label first-name roster"
        "last-name-label last-name roster"
        ". submit  roster"
        ". finalize roster"
        ". . roster"
        ;
        gap: 15px;
      }
    }


      #finalize{
        grid-area: finalize;
      }
      .id-label {
        grid-area: id-label;
      }
      .first-name-label{
        grid-area: first-name-label;
      }
      .last-name-label{
        grid-area: last-name-label;
      }
      .school-id{
        grid-area: school-id
      }
      #first-name {
        grid-area: first-name;
      }
      #last-name{
        grid-area: last-name;
      }
     
      #submit-button {
        grid-area: submit;
        text-align: center;
      }
      #remove-student{
        grid-area: remove-student;
      }
      .roster-display{
        grid-area: roster;
      }
      .student-flex{
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
  min-width: 75px;
  max-width: 150px;
  background-color: #93caef;
  
}




    </style>