<template>

    <div class="report-container">
     
          <h1 class="page-title">Reports View Page</h1>
          <p class="logged-in-title">description here</p>
      
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
        This page is loaded.
        {{ subject }}



        ClassService{{ schoolClass }}
    </div>
    </div>
    
    </template>
    
    <script>
    
  
    import SubjectService from '../services/SubjectService';
    import ClassService from '../services/ClassService';
    
    export default {
      name: 'ReportsView',
      components: {
      
    },
    data() {
      return {
        subject: {},
        schoolClass: {},
        isLoading: true,
        subjectId: 0,
        classId: 0,
      
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
    
      async retrieveSubjectDetails(){
        try {
          this.isLoading = true;
          const response = await SubjectService.getSubjectDetails(this.subjectId);
          this.subject = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.retrieveClassDetails();
        }
        }, 
        async retrieveClassDetails(){
        try {
          this.isLoading = true;
          const response = await ClassService.getClassDetails(this.classId);
          this.schoolClass = response.data;
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
      this.retrieveSubjectDetails();
      
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
    </style>