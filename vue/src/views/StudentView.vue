<template>

   
        <div class = "home-title-view">
          <h1 class="page-title">{{student.firstName}} {{ student.lastName }}</h1>
          <p class="logged-in-title">Student Id: {{ student.schoolId }}</p>
        </div>
   
    <div class="class-container">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
      <button id="show-student-button" @click="showStudents"> {{ showingMsg }}</button>
      <div v-if="showingStudents">
        <StudentNamesSearch/>
      </div>
     
        <div class="class-loop" v-for="schoolClass in schoolClasses" v-bind:key="schoolClass.classId"
        v-bind:value="schoolClass.classId">
         <div class="class-title">{{ schoolClass.className }} - {{ schoolClass.description }}</div>
        
         <SubjectDisplayforStudent :classId="schoolClass.classId" :studentId="studentId"/></div>
       
       
      
    </div>
    </div>
    
    </template>
    
    <script>
  
    import StudentService from '../services/StudentService.js';
    import ClassService from '../services/ClassService.js';
    import SubjectDisplayforStudent from '../components/SubjectDisplayforStudent.vue';
    import StudentNamesSearch from '../components/StudentNamesSearch.vue';

    
    export default {
      name: 'StudentView',
      components: {
        SubjectDisplayforStudent,
        StudentNamesSearch,
      },
   
    data() {
      return {
        studentId: 0,
        student: {},
        schoolClasses: [],
        isLoading: true,
        teacherName: "",
        showingStudents: false,
        showingMsg: 'Switch Students',

       
      };
    },
    methods: {
      handleError(error, verb) {
          if (error.response) {
            this.$store.commit('SET_NOTIFICATION',
              "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Request could not be created.");
          }
        },

        showStudents(){
          if(this.showingStudents){
          this.showingStudents = false;
          this.showingMsg = "Switch Student";
          }else {
            this.showingStudents = true;
            this.showingMsg = "Cancel";
          }
        },
    
      async retrieveStudent(){
        try {
          this.isLoading = true;
          console.log("TEST");
          const response = await StudentService.getStudentDetails(this.studentId);
          this.student = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.retrieveClasses();
        }
        }, 
      
        async retrieveClasses(){
        try {
        
          const response = await ClassService.getClassesByStudent(this.studentId);
          this.schoolClasses = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
        }, 
    
    },
    created(){
    
     
      this.isLoading = true;
      this.studentId = parseInt(this.$route.params.studentId);
      this.retrieveStudent();
      
    }
    }
    
    
    </script>
    
    <style scoped>
    
      .home-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
      
      .class-field {
       max-width: 1000px;
       margin: auto;
      }

      .class-title {
  background-color: #d9d9d9ff;
  color: black;
  text-align: center;
  margin: 10px;

  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  grid-area: class-title;
}
#show-student-button{
        margin-left: auto;
  width: 170px;
  padding: 2px;
  height: 30px;
  border: black;
  background-color: rgb(241, 214, 11);
  color: black;
  border-radius: 5px;
  font-size: 15px;
  cursor: pointer;
  transition: background-color .3s ease;
  font-weight: bold; 
  margin: 10px;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif
}
    </style>