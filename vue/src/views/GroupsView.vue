<template>

    <div class="home-container">
        <NavTool class="home-nav-tool"/>
        <div class = "home-title-view">
          <h1 class="page-title">Groups View Page</h1>
          <p class="logged-in-title">description here</p>
        </div>
        <Logo class="home-logo"/>

    <div class="groups-container">
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="groups-field">
        <div id="sub-loop-groups" v-for="subject in subjectsWithGroups" v-bind:key="subject.id" v-bind:value="subject.id">
          <div id="sub-details-groups">{{ subject.code }} : {{ subject.description }} ID: {{ subject.id }}
</div>
          <div id="groups-loop" v-for="schoolGroup in subject.groups" v-bind:key="schoolGroup.groupId" v-bind:value="schoolGroup.groupId">
            <div id="a-group">{{schoolGroup.groupName  }} : {{ schoolGroup.description }}
          </div>
        </div></div>


        This page is loaded.
    
        
    </div>
    </div>
    </div>
    </template>
    
    <script>
    import Logo from '../components/Logo.vue';
    import NavTool from '@/components/NavTool.vue';
    import GroupService from '../services/GroupService';
    import StudentService from '../services/StudentService';
    import SubjectService from '../services/SubjectService';
    
    export default {
      name: 'GroupsView',
      components: {
        NavTool,
        Logo
    },

    computed: {

      subjectsWithGroups() {
        let subjects = this.subjects;

        this.subjects.forEach(subject => {
      
          let myGroups = [];
          this.CurrentGroups.forEach(curGroup => {
            if(curGroup.subjectId === subject.id){
              myGroups.push(curGroup);
            }
          })
          subject.groups = myGroups;
        })
        return subjects;
      },





    },

    data() {
      return {
        CurrentGroups: [],
        isLoading: true,
        classId: 0,
        subjects: [{
          id: 0,
          code: "",
          description: "",
          topics: [],
          groups: [{}],
        }],
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
    
      async retrieveGroups(){
        try {
          this.isLoading = true;
          const response = await GroupService.getCurrentGroups(this.classId);
          this.CurrentGroups = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.getSubjectDetails();
        }
        }, 
        async getSubjectDetails(){
        try {
          this.isLoading = true;
          const response = await SubjectService.getClassSubjects(this.classId);
          this.subjects = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
        }, 
      
    
    },
    created(){
      
      this.classId = parseInt(this.$route.params.classId);
     
      this.isLoading = false;
      this.retrieveGroups();
      
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
      
      .groups-container {
        grid-area: class;
        display: flex;
      }
      #sub-loop-groups{
        display: grid;
        grid-template-columns: 1fr;
        grid-template-areas: 
        "subject"
        "groups"
          ;
      }
      #sub-details-groups{
        grid-area: subject;
        background-color: #d9d9d9ff;
        color: black;
        text-align: center;
        margin: 10px;
        padding: 10px;
        border-radius: 15px;
        text-decoration: none;
      }

      #group-loops{
        grid-area: groups;
        display: flex;
        flex-wrap: wrap;
        margin: 10px;
      }
      #a-group{
        color: black;
  text-align: center;
  margin: 20px;
  padding: 20px;
  border-radius: 15px;
  text-decoration: none;
  min-width: 100px;
  min-height: 100px;
  font-size: larger;
  background-color: #dd7e6bff;
}

      
    </style>