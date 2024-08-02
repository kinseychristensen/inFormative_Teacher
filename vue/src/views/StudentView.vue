<template>

    <div class="home-container">
        <NavTool class="home-nav-tool"/>
        <div class = "home-title-view">
          <h1 class="page-title">Student View Page</h1>
          <p class="logged-in-title">description here</p>
        </div>
        <Logo class="home-logo"/>
    </div>
    <div class="class-container">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">
        This page is loaded.
    </div>
    </div>
    
    </template>
    
    <script>
    import Logo from '../components/Logo.vue';
    import NavTool from '@/components/NavTool.vue';
    import ClassService from '../services/ClassService';
    
    export default {
      name: 'StudentView',
      components: {
        NavTool,
        Logo
    },
    data() {
      return {
        SchoolClasses: [],
        isLoading: true,
        teacherName: "",
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
    
      async retrieveClasses(){
        try {
          this.isLoading = true;
          console.log("TEST");
          const response = await ClassService.getCurrentClasses();
          this.SchoolClasses = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
        }, 
      
    
    },
    created(){
    
     
      this.isLoading = false;
      
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