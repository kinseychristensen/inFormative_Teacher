<template>
  <div class="home-container">
    <NavTool class="nav-tool"/>
    <div class = "home-title-view">
      <h1 class="page-title">Welcome, {{teacherName}}!</h1>
      <p class="logged-in-title">Please choose a class or subject below to get started.</p>
    </div>
    <Logo class="home-logo"/>


    <div class="classes-container">
<div class="loading" v-if="isLoading">Loading...</div>

<div v-else id="school-classes-loop" v-for="schoolClass in SchoolClasses" v-bind:key="schoolClass.classId" >
  <div class="one-class">
    <div class="top-row-one-class">
    <router-link v-bind:to="{name: 'class-page', params:{classId: schoolClass.classId}}" class="class-title">
        {{ schoolClass.className }}</router-link>
    <router-link class="groups-link" v-bind:to="{name: 'groups', params: {classId: schoolClass.classId}}">Go To Groups</router-link>
</div>



<div class="subject-flex">
 
 <SubjectDisplay class="subject-display" :classId="schoolClass.classId"/>

</div>


</div>

</div>

<p></p>
<router-link class="new-class" v-bind:to="{name: 'create-class'}">Add a New Class</router-link>
</div>




  </div>

</template>



<script>
import Logo from '../components/Logo.vue';
import NavTool from '@/components/NavTool.vue';
import ClassService from '../services/ClassService';
import AuthService from '../services/AuthService';
import SubjectDisplay from '../components/SubjectDisplay.vue';

export default {
  name: 'HomeView',
  components: {
    NavTool,
    Logo,
    SubjectDisplay,
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
      const response = await ClassService.getCurrentClasses();
      this.SchoolClasses = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.isLoading = false;
    }
    }, 
  

  getTeacher(){
    AuthService.getName().then(response =>{
      this.teacherName = response.data;
    }  );
  },

},
created(){

  this.getTeacher();
  this.isLoading = false;
  this.retrieveClasses();

}
}


</script>



<style scoped>

.home-container {
  display: grid;
  grid-template-columns: 250px 1fr 1fr;
  grid-template-areas: 
    "nav title logo"
    "nav classes classes"
    ". classes classes"
    ;
  gap: 15px;
}


.nav-tool {
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

.classes-container {
  grid-area: classes;
}

.subject-flex{
display: flex;
flex-wrap: wrap;
margin: 10px;
}

.subject-title{
  color: black;
  text-align: center;
  margin: 20px;
  padding: 20px;
  border-radius: 15px;
  text-decoration: none;
  min-width: 100px;
  min-height: 100px;
  font-size: larger;
  
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


.top-row-one-class{
  display: grid;
  align-items: space-between;
  display: grid;
  grid-template-columns: 2fr 1fr;
  grid-template-areas:
  "class-title groups-link"
  ;
  gap: 15px;
}

.groups-link{
  background-color: #a4c2f4ff;
  color: black;
  text-align: center;
  margin: 10px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  grid-area: groups-link
}

.new-class{
  background-color:  #d9d9d9ff;
  color: black;
  text-align: center;
  margin: 10px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
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
}





</style>



