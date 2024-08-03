<template>

    <div class="home-container">
        <NavTool class="home-nav-tool"/>
        <div class = "home-title-view">
          <h1 class="page-title">{{subject.code}}</h1>
          <p class="logged-in-title">{{subject.description}}</p>
        </div>
        <Logo class="home-logo"/>
    
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
        <div id="archive-button" class="sub-button-link" @click="archiveSubject">Archive this Subject</div>
       
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
    </div>    
    </template>
    
    <script>
    import Logo from '../components/Logo.vue';
    import NavTool from '@/components/NavTool.vue';
    import SubjectService from '../services/SubjectService';
    
    export default {
      name: 'SubjectView',
      components: {
        NavTool,
        Logo
    },
    data() {
      return {
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
    
      async getSubjectDetails(){
        try {
          this.isLoading = true;
          console.log("TEST");
          const response = await SubjectService.getSubjectDetails(this.subjectId);
          this.subject = response.data;
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
      
      .sub-view-container {
        grid-area: class;
      }
      .sub-view-grid{
        display: grid;
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-template-areas: 
        "score-button artifact-button groups-button mastery-button"
        "syllabus syllabus syllabus syllabus"
        " . archive-button archive-button ."
        ;
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

    </style>