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
          <router-link v-bind:to="{name: 'subject-page', params: {subjectId: subject.id, classId: classId}}" 
          id="sub-details-groups">{{ subject.code }} : {{ subject.description }} ID: {{ subject.id }}
          </router-link>
          <div id="groups-edit-buttons">
             <button @click="toggleAdd(subject.id)" v-if="!subject.addGroup">Add Group</button>
             <button @click="toggleAdd(subject.id)" v-else>Cancel Adding Group</button>
              <button @click="toggleRemove(subject.id)" v-if="!subject.removeGroup">Remove Group</button>
             <button @click="toggleRemove(subject.id)" v-else>Cancel Removing Group</button>
           </div>

          <div class="flex-the-groups">

        <div v-if="subject.addGroup"><CreateGroup :classId="this.classId" :subjectId="subject.id"/></div>

          <div id="groups-active" class="groups-loop" v-if="!subject.removeGroup">
              <div v-for="schoolGroup in subject.groups" v-bind:key="schoolGroup.groupId" v-bind:value="schoolGroup.groupId" >
                  <div class="a-group" :class="theClass(schoolGroup.color)">{{schoolGroup.groupName  }} : {{ schoolGroup.description }}
                    <div><GroupRoster :classId="this.classId" :groupId="schoolGroup.groupId"/></div>
                 </div>
              </div>
          </div>
          <div id="groups-deleting" class="groups-loop" v-else>
            <div v-for="schoolGroup in subject.groups" v-bind:key="schoolGroup.groupId" v-bind:value="schoolGroup.groupId">
              <div class="a-group">{{schoolGroup.groupName  }} : {{ schoolGroup.description }}
                  <button @click="deleteGroup(schoolGroup.groupId)">DELETE THIS GROUP</button>
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
    import GroupService from '../services/GroupService';
    import StudentService from '../services/StudentService';
    import SubjectService from '../services/SubjectService';
    import CreateGroup from '../components/CreateGroup.vue';
    import GroupRoster from '../components/GroupRoster.vue';

    
    export default {
      name: 'GroupsView',
      components: {
        NavTool,
        Logo,
        CreateGroup,
        GroupRoster,
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
          addGroup: false,
          removeGroup: false,
        }],
        colors: [
        {val: 1, colorName: 'Pink'},
        {val: 2, colorName: 'Red'},
        {val: 3, colorName: 'Orange'},
        {val: 4, colorName: 'Yellow'},
        {val: 5, colorName: 'Green'},
        {val: 6, colorName: 'Blue'},
        {val: 7, colorName: 'Purple'},
        {val: 8, colorName: 'Gray'},
       ]
 
      };
    }, 
    methods: {
      handleError(error, verb) {
          if (error.response) {
            this.$store.commit('SET_NOTIFICATION',
              "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " groups. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " groups. Request could not be created.");
          }
        },
    
        toggleAdd(subjectId){
          this.subjects.forEach(subject =>{
            if(subject.id === subjectId){
              subject.addGroup = !subject.addGroup;
            }
          })
        },

        theClass(colorVal){
          if(colorVal === 1)return "pink";
          else if(colorVal === 2) return "red";
          else if(colorVal === 3) return "orange";
          else if(colorVal === 4) return "yellow";
          else if(colorVal === 5) return "green";
          else if(colorVal === 6) return "blue";
          else if(colorVal === 7) return "purple";
          else return "gray";
        },

        toggleRemove(subjectId){
          this.subjects.forEach(subject =>{
            if(subject.id === subjectId){
              subject.removeGroup = !subject.removeGroup;
            }
          })
        },

        async deleteGroup(groupId){
          const shouldDelete = confirm("Are you sure you want to remove this group?");
          if(shouldDelete){
            try{
              this.isLoading = true;
              const response = await GroupService.deleteGroup(groupId);
              let itWorked = response.data;
            }catch (error) {
            this.handleError(error, 'editing');
          }finally {
            this.$router.go(0);
          }
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
 
      }
      #sub-loop-groups{
        display: grid;
        grid-template-columns: 1fr;
        grid-template-areas: 
        "subject"
        "groups"
          ;
      }
      .flex-the-groups{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
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
      #groups-edit-buttons{
        display: flex;
        flex-direction: row;
        align-self: center;
        gap: 5px;
        margin-left: 20px;
      }

      .groups-loop{
        grid-area: groups;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        margin: 10px;
      }
      .a-group{
        color: black;
  text-align: center;
  margin: 20px;
  padding: 20px;
  border-radius: 15px;
  text-decoration: none;
  min-width: 175px;
  max-width: 350px;
  min-height: 75px;

  font-size: larger;
  background-color: #dd7e6bff;
}

.pink{
  background-color: rgb(237, 82, 139);
  min-width: 50px;
}
.red{
  background-color: red;
  min-width: 50px;
}
.orange{
  background-color: rgb(255, 115, 0);
  min-width: 50px;
}
.yellow{
  background-color: rgb(246, 255, 0);
  min-width: 50px;
}
.green{
  background-color: rgb(94, 255, 0);
  min-width: 50px;
}
.blue{
  background-color: rgb(0, 136, 255);
  min-width: 50px;
}
.purple{
  background-color: rgb(149, 35, 144);
  min-width: 50px;
}
.gray{
  background-color: rgb(136, 117, 117);
  min-width: 50px;
}


      
    </style>