<template>

    <div class="report-container">
     
          <h1 class="page-title">{{subject.code}} Reports for {{ schoolClass.className}}</h1>
          <p class="logged-in-title">Want to request a new report type?  Contact us!</p>
      
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field">

      <div>
      
        <label for="lesson-select" id="lesson-select-label">Filter By Indicator:</label>
          <select id="lesson-select" v-model="searchingLesson">Search Indicators
      <option v-bind:value="0">All indicators </option>
      <option v-for="lesson in lessonsList" v-bind:key="lesson.id" v-bind:value="lesson.id">
        {{ lesson.topic_id }} - {{ lesson.code }}: {{ lesson.description }}
      </option>

     </select>
     </div><div>

        <label for="group-sel" id="group-sel-label">Filter By Group:</label>
    <select id="group-sel" v-model="groupSel">
      <option v-bind:value="0">All Students</option>
      <option v-for="group in groups" v-bind:key="group.groupId" v-bind:value="group.groupId">
        {{ group.groupName }} : {{ group.description }}</option>
    </select>
        

      </div>
      
    </div>
    </div>
    
    </template>
    
    <script>
    
  
    import SubjectService from '../services/SubjectService';
    import ClassService from '../services/ClassService';
    import GroupService from '../services/GroupService';
    
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
        groups: [],
        groupSel: 0,
        searchingLesson: 0,
        lessonsList: [],
      
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
          this.retrieveGroups();
        }
        }, 
        async retrieveGroups(){
      try {
      this.isLoading = true;
      const response = await GroupService.getCurrentGroups(this.classId);
      this.groups = response.data;
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
    this.isLoading = false;   
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