<template>

  
        <div class = "home-title-view">
          <h1 class="page-title">Archived Classes</h1>
          <p class="logged-in-title">To view details for an archived class or subject, click the corresponding icon.  </p>
        </div>
       
    
    <div class="archive-container" v-if="isLoading">Loading...</div>
    
    <div v-else class="archive-container">
   

      <h3>Archived Classes</h3>
      <div v-if="archivedSchoolClasses.length == 0">You have no archived classes.</div>
        <div id="archived-classes-loop" v-for="schoolClass in archivedSchoolClasses" v-bind:key="schoolClass.classId" >
  <div class="one-class">
    <router-link v-bind:to="{name: 'class-page', params:{classId: schoolClass.classId}}" class="class-title">
        {{ schoolClass.className }}</router-link>
    
</div>

<div class="subject-flex">

 
 <SubjectDisplay class="subject-display" :classId="schoolClass.classId"/>
 
 <ArchivedSubjectDisplay class="subject-display" :classId="schoolClass.classId"/>

</div>
</div>

<h3>Archived Subjects in Currently Active Classes</h3>
<div id="current-classes-loop" v-for="schoolClass in currentSchoolClasses" v-bind:key="schoolClass.classId" >
  <div class="one-class">
    <router-link v-bind:to="{name: 'class-page', params:{classId: schoolClass.classId}}" class="class-title">
        {{ schoolClass.className }}</router-link>
   
</div>

<div class="subject-flex">
 
 <ArchivedSubjectDisplay class="subject-display" :classId="schoolClass.classId"/>

</div>
</div>



    </div>
   
    
    </template>
    
    <script>
  
    import ClassService from '../services/ClassService';
    import SubjectDisplay from '../components/SubjectDisplay.vue';
    import ArchivedSubjectDisplay from '../components/ArchivedSubjectDisplay.vue';
    
    export default {
      name: 'ArchiveView',
      components: {
     SubjectDisplay, 
     ArchivedSubjectDisplay
    },
    data() {
      return {
        currentSchoolClasses: [],
        archivedSchoolClasses: [],
        isLoading: true,
       
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
    
      async retrieveArchivedClasses(){
        try {
          this.isLoading = true;
          const response = await ClassService.getArchivedClasses();
          this.archivedSchoolClasses = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.retrieveCurrentClasses();
        }
        }, 
      
        async retrieveCurrentClasses(){
        try {
          this.isLoading = true;
          const response = await ClassService.getCurrentClasses();
          this.currentSchoolClasses = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
        }, 
      
    
    },
    created(){
    
     this.retrieveArchivedClasses();
      
    }
    }
    
    
    </script>
    
    <style scoped>
    .home-title-view{
      text-align: center;
    }
   
    
      .archive-container {
        grid-area: class;
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
  width: 400px;
}

#current-classes-loop{
  background-color: rgb(243, 238, 206);
  padding: 15px;
  border-radius: 25px;
  margin: 20px;
  border-style: double;
  border-color: black;
}
#archived-classes-loop{

  padding: 15px;
  border-radius: 25px;
  margin: 20px;
  border-style: double;
  border-color: black;
}

.subject-flex{
display: flex;
flex-wrap: wrap;
margin-top: 10px;
justify-content: flex-start;
}


    </style>