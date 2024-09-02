<template>

<div v-if="scores === []">TThere are no scored artifacts for this student.</div>
    <div class="score-flex">
      <div class="score-item" v-for="score in scores" v-bind:key="score.artifactId" id="sub-box">
       
      <a id="artifact"><ArtifactDetails :artifactId="score.artifactId"/></a>
      <a id="score">Score: {{ score.score }}</a>
      <a id="comments">Comments: {{ score.comments }}</a>
      <a id="waived" v-if="score.waived">This artifact is waived.</a>
   </div> </div>
    
    
    
    </template>
    
    <script>

    import ScoreService from '../services/ScoreService';
import ArtifactDetails from './ArtifactDetails.vue';


    export default {
    props: ['subjectId', 'studentId'],
    components: {
        ArtifactDetails,
      },
    data (){
        return {
            isLoading: true,
            scores: [],
        };
    },
    

    methods: {
        async retrieveScores(){
        try {
          this.isLoading = true;
          const response = await ScoreService.getStudentScoresBySubjectId(this.subjectId, this.studentId)
          this.scores = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
        }, 
    },
    
    created(){
        this.retrieveScores();
    
    }
    
    }
    
    </script>
    <style>
    
    .score-flex{
        border-radius: 15px;
        margin: 10px;
        display: flexbox;
       
      
    }
.score-item{
    display: grid;
    grid-template-columns: 3fr 1fr;
    grid-template-areas: 
    "artifact score  "
    "comments waived  "
    ;
    max-width: 1000px;
   padding: 3px;
  
       
    }

    .score-item:nth-child(odd){
        background-color: white;
    }
    .score-item:nth-child(even){
        background-color:  #d9d9d9ff;
    }
#artifact{
    grid-area: artifact;
}
#score{
    grid-area: score;
}
#comments{
    grid-area: comments;
}
#waived{
    grid-area: waived;
}
 
@media screen and (max-width: 600px) {
    .score-item{
        max-width: 80vw;
    }
}

    
    
    </style>