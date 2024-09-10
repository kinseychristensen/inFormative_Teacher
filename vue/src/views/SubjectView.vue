<template>


        <div class = "home-title-view">
          <h1 class="page-title">{{subject.code}}</h1>
          <p class="logged-in-title">{{subject.description}}</p>
        </div>
     
    
    <div class="sub-view-container">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
      <div class="sub-view-grid">


       <router-link id="score-button" class="sub-button-link" 
          v-bind:to="{name: 'artifacts', params: {classId: this.classId, subjectId: this.subjectId}}">Add Scores</router-link>
        <router-link id="artifact-button" class="sub-button-link"
        v-bind:to="{name: 'create-artifact', params: {classId: this.classId, subjectId: this.subjectId}}">Make an Artifact</router-link>
        <router-link id="mastery-button" class="sub-button-link"
        v-bind:to="{name: 'reports', params: {classId: this.classId, subjectId: this.subjectId}}">View Class Mastery</router-link>
        <router-link id="groups-button" class="sub-button-link"
        v-bind:to="{name: 'groups', params: {classId: this.classId}}">Groups</router-link>
        <div id="archive-button" class="sub-button-link" v-if="subjectActive" @click="archiveSubject">Archive This Subject</div>
        <div v-if="!subjectActive" id="archive-button" class="sub-button-link" @click="reactivateSubject">Reactivate This Subject</div>
        <div v-if="!subjectActive" id="color-selector"><form>
          <label for="sub-color" id="sub-color-label">Subject Color:</label>
          <select id="sub-color" v-model="color">
            <option v-for="color in colors" v-bind:key="color.val" v-bind:value="color.val">{{color.colorName}}</option>
            
          </select>

        </form></div>
       
        <div id="syllabus">
            <div class="topic-loop" v-for="currTopic in subject.topics" v-bind:key="currTopic.id">
            {{ currTopic.code }} : {{ currTopic.description }}
           
            <div class="standard">
              
              <div v-for="currLesson in currTopic.lessons" v-bind:key="currLesson.id">
               {{ currLesson.code }} : {{ currLesson.description }} 

            </div>
            </div>
        </div>
        </div>




      </div>
    </div>
    </div>


    </template>
    
    <script>
   
    import SubjectService from '../services/SubjectService';
    
    export default {
      name: 'SubjectView',
      
    data() {
      return {
        classId: 0,
        subjectId: 0,
        isLoading: true,
        subject: {},
        archivedSubjects: [],
        color: 1,
        colors: [
        {val: 1, colorName: 'Pink'},
        {val: 2, colorName: 'Red'},
        {val: 3, colorName: 'Orange'},
        {val: 4, colorName: 'Yellow'},
        {val: 5, colorName: 'Green'},
        {val: 6, colorName: 'Blue'},
        {val: 7, colorName: 'Purple'},
        {val: 8, colorName: 'Gray'},
       ]
      };
    },
    computed: {

      subjectActive(){
        let subActive = true;
       for (let sub of this.archivedSubjects) {
        if(sub.id === this.subjectId){
          subActive = false;
        }
       }
        return subActive;
      }
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
        archiveSubject(){

const shouldArchive = confirm("Are you sure you want to archive this subject?");

if(shouldArchive){
  SubjectService.archiveSubject(this.subjectId, this.classId)
  .then(response => {
    if(response.status === 200){
      this.$store.commit('SET_NOTIFICATION', {message: 'Successfully archived this subject.', type: 'success'})
      this.$router.push({name: 'class-page', params: {classId: this.classId}});
    }
  }).catch(error => {
    this.handleError(error, 'archiving');
  });
}
},

reactivateSubject(){

const shouldArchive = confirm("Are you sure you want to reactivate this subject?");

if(shouldArchive){
  SubjectService.addSubject(this.subjectId, this.classId, this.color)
  .then(response => {
    if(response.status === 200){
      this.$store.commit('SET_NOTIFICATION', {message: 'Successfully archived this subject.', type: 'success'})
      this.$router.push({name: 'class-page', params: {classId: this.classId}});
    }
  }).catch(error => {
    this.handleError(error, 'archiving');
  });
}
},
    
      async getSubjectDetails(){
        try {
          this.isLoading = true;
          const response = await SubjectService.getSubjectDetails(this.subjectId);
          this.subject = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
         this.getArchivedSubjects();
        }
        }, 
      async getArchivedSubjects(){
        try {
          this.isLoading = true;
          const response = await SubjectService.getArchivedClassSubjects(this.classId);
          this.archivedSubjects = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        
        } 
      },
    
    },
    created(){
      this.subjectId = parseInt(this.$route.params.subjectId);
      this.classId = parseInt(this.$route.params.classId);
      this.getSubjectDetails();
      
    }
    }
    
    
    </script>
    
    <style scoped>
   
      .home-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
      
      .sub-view-container {
        grid-area: class;
      }
      .sub-view-grid{
        display: grid;
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-template-areas: 
        "score-button artifact-button groups-button mastery-button"
        "syllabus syllabus syllabus syllabus"
        " . archive-button color-selector color-selector"
        ;
      }
      #color-selector{
        grid-area: color-selector;
        padding: 10px;
      }
    #score-button{
        grid-area: score-button;
        background-color: #dd7e6bff;
      }
      #artifact-button{
        grid-area: artifact-button;
        background-color: #f6b26bff;
      }
      #groups-button{
        grid-area: groups-button;
        background-color: #ffd966ff;
      }
      #mastery-button{
        grid-area: mastery-button;
        background-color: #93c47dff;
      }
      #archive-button{
        grid-area: archive-button;
        background-color: #f6b26bff;
      }
      #syllabus{
        grid-area: syllabus;
      }
       
      .topic-loop{
        display: grid;
        grid-template-columns: 1fr 3fr;
        grid-template-areas: 
        "topic-item standard"
        ;
        gap: 5px;
        padding: 5px;
        border: 2px;
        border-color: black;

      }
      .topic-loop:nth-child(odd){
        background: #a4c2f4ff;
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }
      .topic-loop:nth-child(even){
        background: rgb(221, 223, 223);
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }

      .topic-item{
        grid-area: topic-item;
        display: flexbox;
        flex-direction: column;
        align-content: flex-start;
        gap: 5px;
      }
      .standard{
        grid-area: standard;
        display: flexbox;
        flex-direction: row;
        gap: 20px;
      }
      .sub-button-link{
  background-color: #a4c2f4ff;
  color: black;
  text-align: center;
  align-items: center;
  margin: 5px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  width: 200px;
  height: 50px;
}

@media screen and (max-width: 600px) {
  .sub-button-link{
  
    max-width: 70px;
  }
}

    </style>