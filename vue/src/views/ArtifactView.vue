<template>

    <div class="home-container">
        <NavTool class="home-nav-tool"/>
        <div class = "home-title-view">
          <h1 class="page-title">{{subject.code}} Artifacts</h1>
          <p class="logged-in-title">{{subject.description}}</p>
        </div>
        <Logo class="home-logo"/>
    
    <div class="class-container">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
        
      <div id="sort-by">Sort By</div>
      <div id="make-art-link">Make a New Artifact</div>
      <div id="class-mast-link">View Class Mastery</div>
      <div id="artifact-grid">List of Artifacts
        <div id="artifact-loop" v-for="artifact in artifacts" v-bind:key="artifact.id">
        <div id="artifact-details">artifact details: 
          <div id="lesson-deets">{{ artifact.lessonId }}</div>
          <div id="artifact-title-desc">{{ artifact.description }}</div>
          <div id="artifact-type">{{ artifact.artifactType }}</div>
          <div id="artifact-date">{{ artifact.assignmentDate }}</div>
          <div id="artifact-trends">{{  artifact.trends}}</div>
          <div id="artifact-comments">{{ artifact.comments }}</div>

        </div>
        <div id="score-buttons">Score buttons</div>
      </div>
    </div>
     







    </div>
    </div>
  </div>
    </template>
    
    <script>
    import Logo from '../components/Logo.vue';
    import NavTool from '@/components/NavTool.vue';
    import SubjectService from '../services/SubjectService';
    import ArtifactService from '../services/ArtifactService';
    
    export default {
      name: 'ArtifactView',
      components: {
        NavTool,
        Logo
    },
    data() {
      return {
        artifacts: [],
        classId: 0,
        subjectId: 0,
        isLoading: true,
        subject: {},
       
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
      this.retrieveArtifacts();
    }
    }, 
   
    async retrieveArtifacts(){
    try {
      this.isLoading = true;
      const response = await ArtifactService.getAllArtifactsBySubject(this.subjectId);
      this.artifacts = response.data;
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
      this.retrieveSubject();
      
    }
    }
    
    
    </script>
    
    <style scoped>
    .home-container {
        display: grid;
        grid-template-columns: 250px 1fr 1fr;
        grid-template-areas: 
          "nav title logo"
          "nav class class"
          ". class class"
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
      }
      .class-field{
        display: grid;
        grid-template-columns: 2fr 1fr 1fr;
        grid-template-areas: 
        "sort-by make-art-link class-mast-link"
        "artifact-grid artifact-grid artifact-grid"
        ;
      }
      #sort-by{
        grid-area: sort-by;
      }
      #make-art-link{
        grid-area: make-art-link;
      }
      #class-mast-link{
        grid-area: class-mast-link;
      }
      #artifact-grid{
        grid-area: artifact-grid;
        display: flex-box;
        gap: 15px;
      }
      #artifact-loop{
        display: grid;
        grid-template-columns: 3fr 1fr;
        grid-template-areas: 
        "artifact-details score-buttons"
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
        grid-template-columns: 2fr 1fr;
        grid-template-areas: 
        "lesson-deets artifact-date"
        "artifact-title-desc artifact-title-desc"
        ". artifact-type "
        "artifact-trends artifact-trends"
        "artifact-comments artifact-comments"
      }
      #score-buttons{
        grid-area: score-buttons;
      }

      #lesson-deets{
        grid-area: lesson-deets;
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
    </style>