<template>

    <div class="scores-flex">
   
<form v-on:submit.prevent="saveScores1" id="scores-form">
    <div id="class-comments">
        <label for="cl-comment">Class Comments:</label>
        <textarea id="cl-comment" v-model="artifact.comments"></textarea>
    </div>
    <div id="class-trends">
        <label for="cl-trend">Class Trends:</label>
        <textarea id="cl-trend" v-model="artifact.trends"></textarea>
    </div>
    <div id="student-labels">
              <p>Student Name</p>
              <p>Score</p>
              <p>Comments</p>
            </div>
            <div id="student-flex">
    <div id="student-loop" v-for="studentScore in newScores" v-bind:key="studentScore.studentId">
        <div id="student-name">{{ studentScore.firstName }} {{ studentScore.lastName }}</div>
       <div id="student-score">
        <input type="number" id="score" v-model="studentScore.score"/>
        <label for="is-waived" id="label-waived">Waives this score</label>
        <input type="radio" id="is-waived" v-model="studentScore.isWaived" :value="true"/>
        <label for="is-not-waived" id="label-not-waived">Counts towards mastery</label>
        <input type="radio" id="is-not-waived" v-model="studentScore.isWaived" :value="false"/>
       
     </div>
        <textarea id="student-comments" v-model="studentScore.comments"></textarea>
        </div>
        </div>

        <button id="save-score" >Save Scores</button>
    </form>
   

</div>
    
    
    </template>
    
    <script>
    import ScoreService from '../services/ScoreService';
    import StudentService from '../services/StudentService';
    import ArtifactService from '../services/ArtifactService';
    
    export default {
    props: ['classId', 'artifactId', 'subjectId'],
        
    data (){
        return {
            isLoading: true,
            students: [],
            scores: [],
            foundScore: false,
            newScores: [],
            artifact:{},
        
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
        toggleWaive(){
            this.studentScore

        },

        async saveScores1(){
            let editedArtifact = {id:0, artifactTypeInt:0, description: '', lessonId: 0, assignmentDateAsStr: '', trends: '', comments: ''};
            editedArtifact.id = this.artifact.id;
            editedArtifact.artifactTypeInt = this.artifact.artifactTypeInt;
            editedArtifact.description = this.artifact.description;
            editedArtifact.lessonId = this.artifact.lessonId;
            editedArtifact.assignmentDateAsStr = this.artifact.assignmentDate;
            editedArtifact.trends = this.artifact.trends;
            editedArtifact.comments = this.artifact.comments;

            try{
                this.isLoading=true;
                const response = await ArtifactService.editArtifact(editedArtifact, this.artifactId);
            }catch(error){
                this.handleError(error, 'saving');
            }finally {
                this.saveScores2();
            }
        },

        async saveScores2(){
            try{
                this.isLoading=true;
                const response = await ScoreService.editScores(this.newScores);
            }catch(error){
                this.handleError(error, 'saving');
            }finally {
              this.$router.go(0);
            }
        },


    async retrieveStudents(){
        try {
          this.isLoading = true;
          const response = await StudentService.getClassRoster(this.classId);
          this.students = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.retrieveScores();
        }
        }, 
        async retrieveScores(){
      try{
        const response = await ScoreService.getScoresByArtifact(this.artifactId);
        this.scores = response.data;
      }catch (error){
        this.handleError(error, 'retrieving');
      }finally {
        this.retrieveArtifact();
      }
    },
    async retrieveArtifact(){
    try {
      this.isLoading = true;
      const response = await ArtifactService.getArtifact(this.artifactId);
      this.artifact = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
        this.buildNewScores();
    }
    }, 

    buildNewScores() {
        
        this.students.forEach(student => {
            let newScore = {};
            newScore.score = 0;
           newScore.comments = '';
            newScore.isWaived = false;
            newScore.artifactId = this.artifactId;
            newScore.firstName = student.firstName;
            newScore.lastName = student.lastName;
           newScore.studentId = student.id;

            this.scores.forEach(score => {
                if(student.id === score.studentId){
                    newScore.score = score.score;
                    newScore.comments = score.comments;
                    newScore.isWaived = score.waived;
                }
            })
                
                this.newScores.push(newScore);
            
        })
        this.isLoading = false;
    }

    },
    
    created(){
        this.retrieveStudents();
    
    }
    
    
    }
    
    </script>
    <style>
#scores-form{
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas:
    "class-comments class-trends"
    "student-labels student-labels"
    "student-loop student-loop"
    "save-score save-score"
    ;
}

#class-comments{
  grid-area: class-comments;
  display: flex;
  flex-direction: column;
  align-items: stretch;
}
#student-labels{
  grid-area: student-labels;
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  background-color: rgb(192, 193, 222);
  gap: 3px;
}
#class-trends{
  grid-area: class-trends;
  display: flex;
  flex-direction: column;
  align-items: stretch;
}
#student-flex{
    grid-area: student-loop;
    display: flex;
    flex-direction: column;
    gap: 2px;
}
    #student-loop{
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas: 
  "student-name score student-comment"
  ;
  background-color: white;
  gap: 3px;
}
#student-name{
  grid-area: student-name;
}
#student-score{
  grid-area: score;
  display: grid;
  grid-template-columns: 2fr 1fr 2fr;
    grid-template-areas: 
    "score is-waived label-waived"
    "score is-not-waived label-not-waived"
    ;
}
#label-waived{
    grid-area: label-waived;
}
#lable-not-waived{
    grid-area: label-not-waived;
}
#score{
    grid-area: score;
}
#is-waived{
    grid-area: is-waived;
}
#is-not-waived{
    grid-area: is-not-waived;
}
#student-comment{
  grid-area: student-comment;
}
#save-scores-button{
  grid-area: save-scores-button;
}

.button-link{
  background-color: #a4c2f4ff;
  color: black;
  text-align: center;
  margin: 2px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  width: 200px;
}   
.active {
    background-color: red;
}
.inactive{
    background-color: green;
}
    </style>