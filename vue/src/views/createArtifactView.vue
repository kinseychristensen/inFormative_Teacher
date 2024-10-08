<template>

       
<div class="class-container">
        <div class = "home-title-view">
          <h1 class="page-title">Create Artifact for {{ subject.code }}</h1>
          <div class="logged-in-title"><p>Artifacts are moments when you are assessing a student's progress towards mastery.  
            These can include physical evidence such as exit slips, worksheets, and practice tests.  They can also include behavioral
            observation such as small group work, class discussions, or one-to-one meetings.  Artifacts must be assinged to an individual 
            standard within the scope of the subject being taught.  If an assignment covers more than one standard, please make multiple artifacts. </p>
          <div v-if="!isValid" class="error">{{ errorMessage }}</div></div>
        </div>

    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
      <form id="ca-form-grid-1" v-on:submit.prevent="addNewArtType">

        <label for="art-type" id="art-type-label">Artifact Type</label>
        <select id="art-type" v-model="artifact.artifactTypeInt">Select Artifact Type 
          <option v-bind:value="0">Please select an artifact type, or create a new one below.</option>
          <option v-for="artType in artifactTypeList" v-bind:key="artType.code" v-bind:value="artType.code">
            {{ artType.description }}
          </option> </select>

        <label for="new-art-type" id="new-art-type-label">Other: </label>
        <input id="new-art-type" type="text" v-model="newArtTypeDesc.description"/>

        <button id="art-type-button">Save New Type</button>
      </form>



      <form id="ca-form-grid-2" v-on:submit.prevent="createArtifact">
     <label for="art-title" id="art-title-label">Title/Description</label>
     <input type="text" id="art-title" placeholder="Title goes here" v-model="artifact.description"/>
     
     <label for="art-lesson" id="art-lesson-label">Indicator</label>   
     <select id="art-lesson" v-model="artifact.lessonId">Select Indicator
      <option v-bind:value="0">Please select the indicator that this artifact assesses.</option>
      <option v-for="lesson in lessonsList" v-bind:key="lesson.id" v-bind:value="lesson.id">
        {{ lesson.topic_id }} - {{ lesson.code }}: {{ lesson.description }}
      </option>

     </select>
  
     
     <label for="art-date" id="art-date-label">Assignment Date</label>
     <input type="date" id="art-date" v-model="artifact.assignmentDateAsStr"/>
     
     <label for="art-comm" id="art-comm-label">Additional Comments</label>
     <textarea id="art-comm" v-model="artifact.comments">comments go here</textarea>
     
     <button id="art-form-button">Submit</button>
    </form>

    </div>
    </div>
    
    </template>
    
    <script>

    import SubjectService from '../services/SubjectService';
    import ArtifactService from '../services/ArtifactService';
    
    export default {
      name: 'CreateArtifactView',
  
    data() {
      return {
        artifact: {
          artifactTypeInt: 0,
          description: '',
          lessonId: 0,
          assignmentDateAsStr: null,
          comments: '',
        },
        subject: {
          code: '',
          description: '',
          notAttempted: 0,
          below: 1,
          approaching: 2,
          proficient: 3,
          mastered: 4,
          classId: 0,
          topics: [
          {
            id: 0,
          code: '',
          description: '',
          lessons: [ {
            id: 0,
          code: '',
          description: '',
          topic_id: 0,
        }]}],
        },
        artifactTypeList: [],
        classId: 0,
        subjectId: 0,
        isLoading: true,
        newArtTypeDesc: {description: '', code: 0},
        isValid: true,
        errorMessage: '',
        lessonsList: [],
      
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
        async retrieveSubject(){
    try {
      this.isLoading = true;
      const response = await SubjectService.getSubjectDetails(this.subjectId);
      this.subject = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.retrieveArtifactTypes();
    }
    }, 
    async retrieveArtifactTypes(){
    try {
      this.isLoading = true;
      const response = await ArtifactService.getArtifactTypes();
      this.artifactTypeList = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.getLessons();
    }
    }, 
    async addNewArtType(){
    try {
      this.isLoading = true;
      const response = await ArtifactService.createNewArtifactType(this.newArtTypeDesc);
      this.newArtTypeDesc = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.retrieveArtifactTypes();
      this.artifact.artifactType = this.newArtTypeDesc.code;
      this.newArtTypeDesc = {code: 0, description: ''};
    }
    }, 

    checkArtifact(){
      this.errorMessage = '';
      this.isValid = true;
      if(this.artifact.assignmentDateAsStr === null){
        this.isValid = false;
        this.errorMessage += "Please select a date.  ";
      }if(this.artifact.description === ''){
        this.isValid = false;
        this.errorMessage += "Please provide a title for the artifact.  ";
      }if(this.artifact.artifactTypeInt === 0){
        this.isValid = false;
        this.errorMessage += "Please choose an artifact type.  ";
      }if(this.artifact.lessonId === 0){
        this.isValid = false;
        this.errorMessage += "Please choose a lesson standard.  ";
      }
    },



    async createArtifact(){
      this.checkArtifact();
      if (this.isValid){
    try {
      this.isLoading = true;
      const response = await ArtifactService.createArtifact(this.artifact);
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.$router.push({name: 'artifacts', params: {classId: this.classId, subjectId: this.subjectId}});
    }
  }
    }, 
      
    getLessons(){
     this.subject.topics.forEach(topic => {
       topic.lessons.forEach(lesson => {
        lesson.topic_id = topic.code;
        this.lessonsList.push(lesson);
      })
     })
      this.isLoading = false;
    },
    
    },
    created(){
    
     this.classId = parseInt(this.$route.params.classId);
     this.subjectId = parseInt(this.$route.params.subjectId);
     this.retrieveSubject();
      
    }
    }
    
    
    </script>
    
    <style scoped>
      
      .home-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
      
      .class-container {
        grid-area: class;
        max-width: 1000px;
        margin: auto;
      }

      #ca-form-grid-1{
        display: grid;
        grid-template-columns: 200px 1fr 200px;;
        grid-template-areas: 
        "art-type-label art-type art-type"
        "new-art-type-label new-art-type art-type-button"
        ;
        margin-bottom: 20px;
        gap: 5px;
      }

      #ca-form-grid-2{
        display: grid;
        grid-template-columns: 200px 1fr;
        grid-template-areas: 
        "art-title-label  art-title"
        "art-lesson-label art-lesson"
        "art-date-label art-date"
        "art-comm-label art-comm"
        "submit submit";
       gap: 5px;
      }

      #art-title-label{
        grid-area: art-title-label;
      }
      #art-title{
        grid-area: art-title;
      }
      #art-type-label{
        grid-area: art-type-label;
      }
      #art-type{
        grid-area: art-type;
      }
      #art-date-label{
        grid-area: art-date-label;
      }
      #art-date{
        grid-area: art-date;
      }
      #art-comm-label{
        grid-area: art-comm-label;
      }
      #art-comm{
        grid-area: art-comm;
      }
      #art-form-button{
        grid-area: submit;
      }
      #art-lesson{
        grid-area: art-lesson;
      }
      #art-lesson-label{
        grid-area: art-lesson-label;
      }
      #art-type-button{
        grid-area: art-type-button;
      }
      .error{
        color: red;
        font-size:x-large;
      }

      @media screen and (max-width: 600px) {
        #ca-form-grid-2{
        display: grid;
        grid-template-columns: 1fr;
        grid-template-areas: 
        "art-title-label "
        "art-title "
        "art-lesson-label "
        "art-lesson"
        "art-date-label "
        "art-date"
        "art-comm-label " 
        " art-comm"
        "submit";
       gap: 5px;
      }

      #ca-form-grid-1{
        display: grid;
        grid-template-columns: 1fr;
        grid-template-areas: 
        "art-type-label"
        "art-type"
        "new-art-type-label"
        "new-art-type"
        "art-type-button"
        ;
        margin-bottom: 20px;
        gap: 5px;
      }

      }




    </style>