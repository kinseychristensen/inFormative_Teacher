<template>

    <div id="create-class">
          <h1 class="page-title">Set Up A Class</h1>
        
          <p class="description">Classes are based on student rosters.  
            Subjects are based on curriculum standards.  Subjects may be cloned to additional classes, so there's no need to place multiple student 
            rosters into a single class. The class name will display on the main menu as a way to identify the course.  We recommend you use an easily 
            recognizable descriptor such as "3rd Period" or "2023-2024."</p>

       
  
    <div class="form">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else>
      <form v-on:submit.prevent="createClass" class="setup-form-container">
       
      
<label for="class-name" class="name-label">Class Name:</label> 
<input id="class-name" v-model="newClass.className"/>
       

      
<label for="class-description" class="description-label">Description:</label> 
<textarea id="class-description" v-model="newClass.description"/>
       
                                             
<button class="submit-button">Continue to Add Students</button>

      </form>



    </div>
    </div>
  </div>
    </template>
    
    <script>
   
    import ClassService from '../services/ClassService';
    
    export default {
      name: 'ClassView',
     
    data() {
      return {
        newClass: {
          className: "",
          description: "",
        },
        isLoading: false,
        
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
        async createClass(){
          try {
            this.isLoading=true;
            console.log(this.newClass);
            const response = await ClassService.createClass(this.newClass);
            this.newClass.id = response.data;
          }catch (error) {
            this.handleError(error, 'creating');
          }finally {
            this.$router.push({name: 'roster', params: {classId: this.newClass.id}});
          }
        }
      }
    }
  
    
    
    
    </script>
    
    <style scoped>
   
      
      .description{
       max-width: 1000px;
      
      }
      
    
      
      .create-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
   
      @media screen and (min-width: 601px){
        .setup-form-container{
          margin: 40px;
        display: grid;
        grid-template-columns: 200px 250px 200px;
        grid-template-areas: 
        "name-label name name"
        "description-label class-description class-description"
        ". submit ."
        ;
        gap: 15px;
      }
      #create-class {
        margin: 100px;
      }
}

@media screen and (max-width: 600px) {
  
  .setup-form-container{
        display: grid;
        grid-template-columns: 200px 250px 200px;
        grid-template-areas: 
        "name-label name name"
        "description-label class-description class-description"
        ". submit ."
        ;
        gap: 15px;
      }
}



      #class-name {
        grid-area: name;
      }
      .name-label{
        grid-area: name-label;
      }
      .description-label{
        grid-area: description-label;
      }
      #class-description{
        grid-area: class-description;
      }
     
      .submit-button {
        grid-area: submit;
        text-align: center;
      }
      .error{
        color: red;
        font-size:x-large;
      }

    </style>