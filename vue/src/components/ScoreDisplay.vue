<template>

<div v-if="scores === []">TThere are no scored artifacts for this student.</div>

<form id="sort-by">
          <select id="lesson-select" v-model="searchingLesson">Search Indicators
      <option v-bind:value="0">Filter By All indicators </option>
      <option v-for="lesson in lessonsList" v-bind:key="lesson.id" v-bind:value="lesson.id">
        {{ lesson.topic_id }} - {{ lesson.code }}: {{ lesson.description }}
      </option>

     </select>

        </form>

    <div class="score-flex">
      <div class="score-item" v-for="score in scores" v-bind:key="score.artifactId">
      
       <div  v-if="searchingLesson === 0 || score.lessonId === searchingLesson" class="score-item-flex">
      <a id="artifact"><ArtifactDetails :artifactId="score.artifactId"/></a>
      <a id="score">Score: {{ score.score }}</a>
      <a id="comments">Comments: {{ score.comments }} lesson id: {{ score.lessonId }}</a>
      <a id="waived" v-if="score.waived">This artifact is waived.</a>
      
    </div>
   </div> </div>  
    
    </template>
    
    <script>

    import ScoreService from '../services/ScoreService';
import ArtifactDetails from './ArtifactDetails.vue';
import SubjectService from '../services/SubjectService';


    export default {
    props: ['subjectId', 'studentId'],
    components: {
        ArtifactDetails,
      },
    data (){
        return {
            isLoading: true,
            subject: {},
            unfilteredScores: [],
            lessonsList: [],
        searchingLesson: 0,
        searchTerm: '',
       
        };
    },
    computed: {
        scores() {
            let scores = this.unfilteredScores;
          
              if(this.searchingLesson != 0){  

              scores = scores.filter((item)=> {
                return (item.lessonId === this.searchingLesson);
              }
            )
              }
              return scores;
            },
    },

    methods: {
        async retrieveScores(){
        try {
          this.isLoading = true;
          const response = await ScoreService.getStudentScoresBySubjectId(this.subjectId, this.studentId)
          this.unfilteredScores = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.retrieveSubject();
        }
        }, 
    
    async retrieveSubject(){
    try {
      this.isLoading = true;
      const response = await SubjectService.getSubjectDetails(this.subjectId);
      this.subject = response.data;
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
    })},
},
    
    created(){
        this.retrieveScores();
    
    }
    
    }
    
    </script>
    <style scoped>
    #lesson-select{
      width: 600px;
    }
    
    .score-flex{
        border-radius: 15px;
        display: flexbox;
        max-width: 80vw;
        align-content: center;
        margin: auto;
        margin-bottom: 5px;  
      
    }
.score-item{
    max-width: 1000px;
   padding: 3px;
   text-align: left;
   margin: auto;
   font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
   border-radius: 5px;

    }

    .score-item-flex{
    display: grid;
    grid-template-columns: 3fr 1fr;
    grid-template-areas: 
    "artifact score  "
    "comments waived  "
    ;
    }

    .score-item:nth-child(odd){
        background-color: white;
    }
    .score-item:nth-child(even){
        background-color:  #d9d9d9ff;
    }
#artifact{
    grid-area: artifact;
    text-align: left;
}
#score{
    grid-area: score;
    text-align: left;
}
#comments{
    grid-area: comments;
    text-align: left;
}
#waived{
    grid-area: waived;
    text-align: right;
}
 #lesson-select{
    max-width: 80vw;
 }
@media screen and (max-width: 600px) {
    .score-item{
        max-width: 80vw;
    }
}

    
    
    </style>