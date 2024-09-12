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
      <option v-for="group in classGroups" v-bind:key="group.groupId" v-bind:value="group.groupId">
        {{ group.groupName }} : {{ group.description }}</option>
    </select>

    <div>Reports:
      <input type="checkbox" id="mastery-checkbox" v-model="showMasteryChart"/>
      <label for="mastery-checkbox">Student Mastery Levels</label>

      <input type="checkbox" id="percent-checkbox" v-model="showPercentChart"/>
      <label for="percent-checkbox">Class Mastery Level Percentages</label>

      <input type="checkbox" id="scatter-checkbox" v-model="showScatterChart"/>
      <label for="scatter-checkbox">Student Growth by Artifact</label>

    </div>

    <button @click="toggleShowNames">{{ showNamesMsg }}</button>

      </div>

     
      <GroupMastery v-if="showMasteryChart" :lessons="lessons" :students="students" :approaching="subject.approaching" :below="subject.below"
      :mastered="subject.mastered" :not-attempted="subject.notAttempted" :proficient="subject.proficient" :showNames="showNames"/>

      <div v-for="lesson in lessons" :key="lesson.id" :value="lesson.id">
  <PercentagesCalculator 
    v-if="showPercentChart" 
    :lesson="lesson" 
    :students="students" 
    :notAttempted="subject.notAttempted" 
    :below="subject.below"
    :approaching="subject.approaching" 
    :proficient="subject.proficient" 
    :mastered="subject.mastered"
    :chartId="'myChart-' + lesson.id" 
  />
</div>


<div v-for="lesson in lessons" :key="lesson.id" :value="lesson.id">
  <ArtifactCalculator 
    v-if="showScatterChart" 
    :lesson="lesson" 
    :students="students" 
    :notAttempted="subject.notAttempted" 
    :below="subject.below"
    :approaching="subject.approaching" 
    :proficient="subject.proficient" 
    :mastered="subject.mastered"
    :chartId="'myScatterChart-' + lesson.id"
    :artifacts="artifacts" 

  />
</div>


    </div>
    </div>
 
  
    </template>
    
    <script>
    
  
    import SubjectService from '../services/SubjectService';
    import ClassService from '../services/ClassService';
    import GroupService from '../services/GroupService';
    import StudentService from '../services/StudentService';
    import GroupMastery from '../components/GroupMastery.vue';
    import PercentagesCalculator from '../components/PercentagesCalculator.vue';
    import ArtifactService from '../services/ArtifactService';
    import ArtifactCalculator from '../components/ArtifactCalculator.vue';
    import ScoreService from '../services/ScoreService';

    
    export default {
      name: 'ReportsView',
      components: {
        GroupMastery, 
        PercentagesCalculator,
        ArtifactCalculator,
      
    },
    data() {
      return {
        subject: {},
        schoolClass: {},
        isLoading: true,
        subjectId: 0,
        classId: 0,
        classGroups: [],
        groupSel: 0,
        searchingLesson: 0,
        lessonsList: [],
        classRoster: [],
        showNames: false,
        showNamesMsg: 'Show Student Names',
        showMasteryChart: false,
        showPercentChart: false,
        showScatterChart: false,
        unfilteredArtifacts: [],
        unfilteredScores: [],
      
      };

},
  computed: {
    students(){
      let students = this.classRoster;

      if(this.groupSel != 0){
        this.classGroups.forEach(group => {
          if(group.groupId === this.groupSel){
            students = group.students;
          }
        })
        }

      return students;
    },
    lessons(){
      let lessons = this.lessonsList;

      if(this.searchingLesson != 0){
        this.lessonsList.forEach(lesson => {
          if(lesson.id === this.searchingLesson){
            lessons = [lesson];
          }
        })
      }

      return lessons;
    },

artifactsWithScores(){
  let artifactsWithScores = [];
      
      this.unfilteredArtifacts.forEach(artifact => {
            let scores = [];
            this.unfilteredScores.forEach(score => {
                if(score.artifactId === artifact.id){
                  this.students.forEach(student => {
                    if(student.id === score.studentId){
                      scores.push(score);
                    }
                  })
                }
            })
            let newArtifact = {
              artifactId: artifact.artifactId, 
              description: artifact.description,
              assignmentDate: artifact.assignmentDate,
              lessonId: artifact.lessonId,
              scores: [],
              };
            newArtifact.scores = scores;
            artifactsWithScores.push(newArtifact);
          });
          return artifactsWithScores;


},

    artifacts(){
      let artifacts = this.artifactsWithScores;
      if(this.searchingLesson === 0 ){
          return artifacts;
      }else {
        let artifacts = [];
          if(this.artifactsWithScores.length > 0){
              this.artifactsWithScores.forEach(artifact => {
                if(artifact.lessonId === this.searchingLesson){
                  artifacts.push(artifact);
            }
          })
          }return artifacts;
        
  }
},
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

        toggleShowNames(){
          if(this.showNames){
            this.showNames = false;
            this.showNamesMsg =  "Show Student Names";
          }else {
            this.showNames = true;
            this.showNamesMsg = "Hide Student Names";
          }
        },
    
        async retrieveSubjectDetails() {
  try {
    this.isLoading = true;
    const subjectResponse = await SubjectService.getSubjectDetails(this.subjectId);
    this.subject = subjectResponse.data;

    const classResponse = await ClassService.getClassDetails(this.classId);
    this.schoolClass = classResponse.data;

    const groupResponse = await GroupService.getCurrentGroups(this.classId);
    this.classGroups = groupResponse.data;

    const studentResponse = await StudentService.getClassRoster(this.classId);
    this.classRoster = studentResponse.data;

    const artifactResponse = await ArtifactService.getAllArtifactsBySubject(this.subjectId);
    this.unfilteredArtifacts = artifactResponse.data;

    const scoreResponse = await ScoreService.getClassScoresBySubject(this.classId, this.subjectId);
    this.unfilteredScores = scoreResponse.data;

    // Finally, process lessons after all data is ready
   
  } catch (error) {
    this.handleError(error, 'retrieving');
  } finally {
    this.getLessons();
    this.isLoading = false;  // Set isLoading to false only when all data is fetched
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
   
      .home-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
      
      .report-container {
        margin:auto;
        max-width: 1000px;
      }
    </style>