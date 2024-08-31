<template>

<div class="class-container">
        <div class = "home-title-view">
          <h1 class="page-title">{{subject.code}} Artifacts</h1>
          <p class="logged-in-title">{{subject.description}}</p>
        </div>

    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
        
        <form id="sort-by">
          <label for="search-box" id="search-box-label">Search Assignment Names:</label>
          <textarea v-model="searchTerm" id="search-box"></textarea>
          <label for="lesson-select" id="lesson-select-label">Filter By Indicator:</label>
          <select id="lesson-select" v-model="searchingLesson">Search Indicators
      <option v-bind:value="0">All indicators </option>
      <option v-for="lesson in lessonsList" v-bind:key="lesson.id" v-bind:value="lesson.id">
        {{ lesson.topic_id }} - {{ lesson.code }}: {{ lesson.description }}
      </option>

     </select>

        </form>
 
      <router-link id="make-art-link" class="art-button-link" 
      v-bind:to="{name: 'create-artifact', params: {classId: this.classId, subjectId: this.subjectId}}">
        Make a New Artifact</router-link>
      <router-link id="class-mast-link" class="art-button-link"
      v-bind:to="{name: 'create-artifact', params: {classId: this.classId, subjectId: this.subjectId}}">
      View Class Mastery</router-link>
      
      <div id="artifact-grid">
        <div id="artifact-loop" v-for="artifact in artifacts" v-bind:key="artifact.id">
        <div id="artifact-details">
          <div id="lesson-nums">{{ artifact.topicCode }}:{{ artifact.lessonCode }}</div>  
           <div id="lesson-deets"> {{ artifact.topicDescription }}: {{ artifact.lessonDescription }}</div>
          <div id="artifact-title-desc">{{ artifact.description }}<p></p></div>
          <div id="artifact-type">{{ artifact.artifactType }}</div>
          <div id="artifact-date">{{ artifact.assignmentDate }}</div>
          <div id="artifact-comments">Comments: {{ artifact.comments }}</div>

        </div>

        <div class="art-button-link" id="score-buttons" @click="toggleScores(artifact.id)">Scores</div>
        <div class="art-button-link" id="delete-art" @click="deleteArtifact(artifact.id)">Delete this Artifact</div>
        <div id="score-show">
       

          
          <div v-if="artifact.isShowingScore">   
       <AddScore :artifactId="artifact.id" :classId="classId"  /></div>
        </div>
      </div>
    </div>

    </div>
    </div>

    </template>
    
    <script>
    import SubjectService from '../services/SubjectService';
    import ArtifactService from '../services/ArtifactService';
 import AddScore from '../components/AddScore.vue';

    
    export default {
      name: 'ArtifactView',
      components: { 
        AddScore,
    },
    data() {
      return {
  
        unfilteredArtifacts: [{
          id: 0,
          artifactTypeInt: 0,
          artifactType: '',
          description: '',
          lessonId: 0,
          topicCode: '',
          lessonCode: '',
          topicDescription: '',
          lessonDescription: '',
          assignmentDateAsStr: null,
          trends: '',
          comments: '',
          isShowingScore: true,
          
        },
        ],
        classId: 0,
        subjectId: 0,
        isLoading: true,
        subject: {},
        lessonsList: [],
        searchingLesson: 0,
        searchTerm: '',
        
      };
    },
    computed: {
        artifacts() {
            let artifacts = this.unfilteredArtifacts;

            if(this.searchTerm != ''){
                artifacts = artifacts.filter((item) => {
                    return item.description.toLowerCase().includes(this.searchTerm.toLowerCase())
                })

            }
          
              if(this.searchingLesson != 0){  

              artifacts = artifacts.filter((item)=> {
                return (item.lessonId === this.searchingLesson);
              }
            )
              }
              return artifacts;
            },
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

        toggleScores(artifactId){
          this.artifacts.forEach(artifact =>{
        if(artifact.id === artifactId){
            artifact.isShowingScore = !artifact.isShowingScore;
        }})

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
      this.retrieveArtifacts();
    }
    }, 
    async retrieveArtifacts(){
    try {
      this.isLoading = true;
      const response = await ArtifactService.getAllArtifactsBySubject(this.subjectId);
      this.unfilteredArtifacts = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.getLessons();
    }
    }, 
   
    getLessons(){
     this.subject.topics.forEach(topic => {
       topic.lessons.forEach(lesson => {
        lesson.topic_id = topic.id;
        lesson.topicCode = topic.code;
        lesson.topicDescription = topic.description;
        lesson.lessonDescription = lesson.description;
        lesson.lessonCode = lesson.code;
        this.lessonsList.push(lesson);
      })
    })
     this.unfilteredArtifacts.forEach(artifact =>{
      this.lessonsList.forEach(lesson =>{
        if(lesson.id === artifact.lessonId){
          artifact.topicCode = lesson.topicCode;
          artifact.lessonCode = lesson.lessonCode;
          artifact.topicDescription = lesson.topicDescription;
          artifact.lessonDescription = lesson.lessonDescription;
        }
      }); artifact.isShowingScore = false;
    }); 
    this.isLoading = false;
  },

    async deleteArtifact(artifactId){
      const shouldDelete = confirm("Are you sure you want to delete this artifact?  All scores from the artifact will also be deleted.");
    
      if(shouldDelete){
        try{
          this.isLoading = true;
          const response = await ArtifactService.deleteArtifact(artifactId);
          let didDelete = response.data;
        }catch (error) {
            this.handleError(error, 'editing');
          }finally {
            this.$router.go(0);
          }
      }
    
    
    
    },

    
    },
    created(){
      this.subjectId = parseInt(this.$route.params.subjectId);
      this.classId = parseInt(this.$route.params.classId);
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
      .class-field{
        display: grid;
        grid-template-columns: 2fr 1fr 250px;
        grid-template-areas: 
        "sort-by make-art-link class-mast-link"
        "artifact-grid artifact-grid artifact-grid"
        ;
      }
      #sort-by{
        grid-area: sort-by;
        display: grid;
        grid-template-columns: 1fr 3fr;
        grid-template-areas: 
        "search-box-label search-box"
        "lesson-select-label lesson-select"
        ;
      }
      #search-box-label{
        grid-area: search-box-label;
      }
      #search-box{
        grid-area: search-box;
      }
      #lesson-select-label{
        grid-area: lesson-select-label;
      }
      #lesson-select{
        grid-area: lesson-select;
      }
      #make-art-link{
        grid-area: make-art-link;
        background-color: #ffd966ff;


      }
      #class-mast-link{
        grid-area: class-mast-link;
        background-color: #93c47dff;
      }
      #artifact-grid{
        grid-area: artifact-grid;
        display: flex-box;
        gap: 15px;
      }
      #artifact-loop{
        display: grid;
        grid-template-columns: 1fr 225px;
        grid-template-areas: 
        "artifact-details score-buttons"
         "artifact-details delete-art"
        "score-show score-show"
        ;
      }
      #artifact-loop:nth-child(odd){
        background: #a4c2f4ff;
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }
      #artifact-loop:nth-child(even){
        background: rgb(221, 223, 223);
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }

      #artifact-details{
        grid-area: artifact-details;
        display: grid;
        grid-template-columns: 3fr 1fr;
        grid-template-areas: 
        "lesson-nums ."
        "lesson-deets artifact-date"
        "artifact-title-desc artifact-title-desc"
        ". artifact-type "
        "artifact-comments artifact-comments"
        ;
      }
      #score-buttons{
        grid-area: score-buttons;
        background-color:  #f6b26bff;
      }
      #delete-art{
        grid-area: delete-art;
        background-color: #dd7e6bff;
      }

      #lesson-deets{
        grid-area: lesson-deets;
      }
      #lesson-nums{
        grid-area: lesson-nums;
      }
      #artifact-title-desc{
        grid-area:artifact-title-desc;
      }
      #artifact-type{
        grid-area: artifact-type;
      }
      #artifact-date{
        grid-area: artifact-date;
      }
      #artifact-trends{
        grid-area: artifact-trends;
      }
      #artifact-comments{
        grid-area: artifact-comments;
      }
      .art-button-link{
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
#score-show{
  grid-area: score-show;
}
@media screen and (max-width: 600px){
  .class-container {
        grid-area: class;
        max-width: 100vw;

      }

      .class-field{
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-areas: 
        "make-art-link class-mast-link"
        "sort-by sort-by"
        "artifact-grid artifact-grid"
        ;

      }

      #sort-by{
        grid-area: sort-by;
        display: grid;
        max-width: 100vw;
        grid-template-columns: 1fr;
        grid-template-areas: 
        "search-box-label "
        "search-box"
        "lesson-select-label "
        "lesson-select"
        ;
      }
    
      #artifact-loop{
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-areas: 
        "artifact-details artifact-details"
        "score-buttons delete-art"
        "score-show score-show"
        ;
        max-width: 90vw;
      }
      #artifact-details{
        grid-area: artifact-details;
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-areas: 
       "artifact-date artifact-type"
        "lesson-nums ."
        "lesson-deets lesson-deets"
        "artifact-title-desc artifact-title-desc"
        "artifact-comments artifact-comments"
        ;
      }
      #sort-by{
        grid-area: sort-by;
        display: grid;
        grid-template-columns: 1fr;
        grid-template-areas: 
        "search-box-label"
        "search-box"
        "lesson-select-label"
        "lesson-select"
        ;
        max-width: 90vw;
      }
      #search-box{
        grid-area: search-box;
        max-width: 400px;
      }
      #lesson-select{
        grid-area: lesson-select;
        max-width: 400px;
      }
      .art-button-link{
  width: 150px;
  height: 50px;
}





    }





    </style>