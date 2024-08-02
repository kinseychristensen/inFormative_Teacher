<template>

<div class="class-view-container">
    <NavTool class="home-nav-tool"/>
    <div class = "home-title-view">
      <h1 class="page-title">Class: {{ schoolClass.className }}</h1>
    </div>
    <Logo class="home-logo"/>
<div class="class-container">
<div class="loading" v-if="isLoading">Loading...</div>

<div v-else class="class-field">
  <div class="form">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else>
      <form v-on:submit.prevent="editClass" class="class-edit-container">
       
      
<label for="class-name" class="name-label">Class Name:</label> 
<input id="class-name" v-model="editedClass.className"/>
       

      
<label for="class-description" class="description-label">Description:</label> 
<textarea id="class-description" v-model="editedClass.description"></textarea>
  



<div class="class-subjects">Subjects:</div>
<div class="sub-display">
  <div v-if="isArchiving" class="subject-flex">
   
  <div v-for="subject in subjects" v-bind:key="subject.id" class="subject-title-archive" @click="archiveSubject(subject.id)">
  {{subject.code}}
  </div>
  <div>Click on a Subject to Archive.</div>
  <p></p></div>


  <div v-else class="subject-flex">
  <router-link v-for="subject in subjects" v-bind:key="subject.id"
  v-bind:to="{name: 'subject-page', params: {subjectId: subject.id}}" class="subject-title">
  {{ subject.code }}
</router-link></div>

</div>
  
        
<div class="class-roster">Students</div>
<div class="ros-display"> {{ schoolClass.students }}

  <div class="student-flex">
  <router-link v-for="student in students" v-bind:key="student.id"
  v-bind:to="{name: 'student', params: {studentId: student.id}}" class="student-title">
  {{ student.firstName }} {{ student.lastName }}
</router-link></div>




</div>

      
<div class="subject-button">
  
<p></p>
  <router-link class="button-link" v-bind:to="{name: 'create-subject'}">Add a Subject</router-link>
  <p></p>
  <div class="button-link" @click="toggleArchive">{{archiveMessage}}</div>
</div>

<div class="roster-button">
  <router-link class="button-link" v-bind:to="{name: 'roster', params: {classId: this.classId}}">Go to Edit Class Roster </router-link>
</div>

<div class="active-label">Status: </div>

<div class="active-radio">
  {{activeDisplay }} <div id="activeToggle" @click="toggleActive">{{ buttonMsg }}</div>

</div>
                                             
<button class="submit-button">Save Changes</button>


      </form>
    </div>
  </div>
</div>
</div>
</div>

</template>

<script>
import Logo from '../components/Logo.vue';
import NavTool from '@/components/NavTool.vue';
import ClassService from '../services/ClassService';
import StudentService from '../services/StudentService';
import SubjectService from '../services/SubjectService';

export default {
  name: 'ClassView',
  components: {
    NavTool,
    Logo
},
data() {
  return {
    classId: 0,
    schoolClass: {},
   subjects: [],
   students: [],
    isLoading: true,
    editedClass: {
      classId: 0,
      description: '',
      className: '',
      active: true,
    },
    buttonMsg: "",
    activeDisplay: "",
    isArchiving: false,
    archiveMessage: "Archive Subject"
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

archiveSubject(subjectId){

  const shouldArchive = confirm("Are you sure you want to archive this subject?");

  if(shouldArchive){
    SubjectService.archiveSubject(subjectId, this.classId)
    .then(response => {
      if(response.status === 200){
        this.$store.commit('SET_NOTIFICATION', {message: 'Successfully archived this subject.', type: 'success'})
        this.$router.go(0);
      }
    }).catch(error => {
      this.handleError(error, 'archiving');
    });
  }
},


toggleActive(){
this.editedClass.active = (!this.editedClass.active);
if(this.editedClass.active){
    this.buttonMsg = "Deactivate Class";
    this.activeDisplay = "Active";
  }else {
    this.buttonMsg = "Reactivate Class";
    this.activeDisplay = "Inactive";
  }
},

toggleArchive(){
this.isArchiving = (!this.isArchiving);
if(this.isArchiving){
    this.archiveMessage = "Cancel";
  }else {
    this.archiveMessage = "Archive Subject"
  }
},


editClass(){
  ClassService.editClass(this.editedClass, this.classId)
  .then(response => {
    if(response.status === 201) {
      this.retrieveClass();
    }
  }).catch(error => {
    this.handleError(error, 'editing');
  });
},


  async retrieveClass(){
    try {
      this.isLoading = true;
      const response = await ClassService.getClassDetails(this.classId);
      this.schoolClass = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.editedClass.classId = this.classId;
  this.editedClass.description = this.schoolClass.description;
  this.editedClass.className = this.schoolClass.className;
  this.editedClass.active = this.schoolClass.active;
  if(this.editedClass.active){
    this.buttonMsg = "Deactivate Class";
    this.activeDisplay = "Active";
  }else {
    this.buttonMsg = "Reactivate Class";
    this.activeDisplay = "Inactive";
  }
      this.retrieveSubjects();
    }
    }, 

    async retrieveSubjects(){
    try {
      this.isLoading = true;
      const response = await SubjectService.getClassSubjects(this.classId);
      this.subjects = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.retrieveStudents();
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
this.classId = parseInt(this.$route.params.classId)
 this.retrieveClass();
 
}
}


</script>

<style scoped>
 .class-view-container {
        display: grid;
        grid-template-columns: 200px 1fr 1fr;
        grid-template-areas: 
          "nav title logo"
          "nav class class"
          "nav class class"
          ;
        gap: 15px;
      }
  
  
  .home-nav-tool {
    grid-area: nav;
    margin-right: 20px;
  }
  
  .home-logo {
    grid-area: logo;
    justify-self: right;
  }
  
  .home-title-view {
    grid-area: title;
    justify-content: center;
    text-align: center;
  }
  
  .class-container {
    grid-area: class;
    min-width: 800px;
  }

  .class-edit-container{
        display: grid;
        padding-top: 5px;
        grid-template-columns: 150px 2fr 1fr;
        grid-template-areas: 
        "name-label name name"
        "description-label class-description class-description"
        "active-label active-radio ."
        ". submit submit"
        "subjects sub-display sub-buttons"
        "roster ros-display ros-button"
        
        ;
        gap: 15px;
      }
.sub-display{
  grid-area: sub-display;
  background-color: rgb(233, 230, 230);
  text-align: left;
  padding: 10px;
  text-decoration: none;
  border-radius: 5px;

}
.ros-display{
  grid-area: ros-display;
  background-color: rgb(233, 230, 230);
  text-align: left;
  padding: 10px;
  text-decoration: none;
  border-radius: 5px;


}
      .class-name {
        grid-area: name;
        margin-top: 2px;
      }
      .active-label{
        grid-area: active-label;
      }
      .active-radio{
        grid-area: active-radio;
        display: flex;
        
      }
      .name-label{
        grid-area: name-label;
      }
      .description-label{
        grid-area: description-label;
      }
      #class-description{
        grid-area: class-description;
        margin-top: 2px;
      }
      .class-subjects {
        grid-area: subjects;
      }
      .class-roster {
        grid-area: roster;
      }

      .button-link{
  background-color: #a4c2f4ff;
  color: black;
  text-align: center;
  margin: 2px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  width: 170px;
}
#activeToggle{
  background-color: #a4c2f4ff;
  color: black;
  text-align: center;
  margin: 2px;
  margin-left: 50px;
  padding: 5px;
  padding-left: 10px;
  padding-right: 10px;
  border-radius: 15px;
  text-decoration: none;
  min-width: 75px;
  max-width: 150px;
}

      .submit-button {
        grid-area: submit;
        text-align: center;
        background-color: #a4c2f4ff;
        color: black;
        padding: 5px;
        border-radius: 15px;
        text-decoration: none;
        max-width: 500px;
        border-color:  #a4c2f4ff;
      }
      .subject-button {
        grid-area: sub-buttons;
      }
      .roster-button {
        grid-area: ros-button;
      }
      .subject-title{
  color: black;
  text-align: center;
  margin: 2px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  font-size: larger;
  min-width: 75px;
  max-width: 150px;
  
}
.subject-title-archive{
  color: black;
  text-align: center;
  margin: 2px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  font-size: larger;
  min-width: 75px;
  max-width: 150px;
  background-color: rgb(253, 253, 253);
  
}

.subject-title:nth-child(4n-3){
  background-color: #dd7e6bff;
}
.subject-title:nth-child(4n-2){
  background-color: #f6b26bff;
}
.subject-title:nth-child(4n-1){
  background-color: #ffd966ff;
}
.subject-title:nth-child(4n-0){
  background-color: #93c47dff;
}
.subject-flex{
display: flex;
flex-wrap: wrap;
gap: 5px;
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
  
}

.student-title:nth-child(4n-3){
  background-color: #dd7e6bff;
}
.student-title:nth-child(4n-2){
  background-color: #f6b26bff;
}
.student-title:nth-child(4n-1){
  background-color: #ffd966ff;
}
.student-title:nth-child(4n-0){
  background-color: #93c47dff;
}
    </style>

