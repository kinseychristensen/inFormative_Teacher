<template>


    <div class="subject-flex">
      <button v-for="subject in subjects" v-bind:key="subject.id" id="sub-box" :class="theClass(subject.color)"
       >
      <div class="sub-text">{{ subject.code }}</div>
      <div class="sub-text">{{ subject.description }}</div>
      <div class="buttons">
        <button class="btn" id="show-scores-btn" @click="toggleScoresOn">{{scoreMessage}}</button>
        <button class="btn" id="show-mastery-btn" @click="toggleMasteryOn">{{masteryMessage}}</button>
      </div>
      <div id="score-display"  v-if="showScores"> 
        <ScoreDisplay :studentId="studentId" :subjectId="subject.id"/>
      </div>
      <div v-if="showMastery">
        <MasteryDisplay :subjectId ="subject.id" :studentId="studentId"/>
    </div>
    </button>
</div>
    
    
    
    </template>
    
    <script>
    import SubjectService from '../services/SubjectService';
    import ScoreDisplay from './ScoreDisplay.vue';
    import MasteryDisplay from './MasteryDisplay.vue';

   
    
    export default {
    props: ['classId', 'studentId'],
    components: {
        ScoreDisplay,
        MasteryDisplay,
      },
        
    data (){
        return {
            isLoading: true,
            subjects: [],
            showScores: false,
            scoreMessage: 'Show Scores',
            masteryMessage: 'Show Mastery Levels',
            showMastery: false,
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
    async retrieveSubjects(){
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

        async retrieveScores(){
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

            toggleScoresOn(){
                if(!this.showScores){
                this.showScores = true;
                this.showMastery = false;
                this.scoreMessage = "Hide Scores";
                this.masteryMessage = "Show Mastery Levels";
                }else {
                    this.showScores = false;
                    this.scoreMessage = "Show Scores";
                  
                }
            },

            toggleMasteryOn(){
                if(!this.showMastery){
                this.showScores = false;
                this.showMastery = true;
                this.masteryMessage = "Hide Mastery Levels";
                this.scoreMessage = "Show Scores";
            }else {
                this.showMastery = false;
                this.masteryMessage = "Show Mastery Levels";
               
            }
        },

    
    },
    
    created(){
        this.retrieveSubjects();
    
    }
    
    
    }
    
    </script>
    <style scoped>
    
    
    .subject-flex{
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
  
    }
    
    #sub-box{
        vertical-align: middle;
        text-align: center;
      min-height: 100px;
        width: 90vw;
      border-radius: 15px;
      text-align: center;
      text-decoration: none;
      margin: auto;
    }
    
    .sub-text {
      vertical-align: middle;
      color: black;
      text-decoration: none;
      text-align: center;
      font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif
    
    }
    
    .pink{
      background-color: #f3c0bd;
    }
    .red{
      background-color: #ee8f8d;
    }
    .orange{
      background-color: #f1ac8d;
    }
    .yellow{
      background-color:#f7d18a;
    }
    .green{
      background-color: #b2c8a5;
    }
    .blue{
      background-color: #93caef;
    }
    .purple{
      background-color: #d0aee6;
    }
    .gray{
      background-color: #bfc5c9;
    }
    .btn{
        margin-left: auto;
  width: 170px;
  padding: 2px;
  height: 30px;
  border: black;
  background-color: rgb(241, 214, 11);
  color: black;
  border-radius: 5px;
  font-size: 15px;
  cursor: pointer;
  transition: background-color .3s ease;
  font-weight: bold; 
  margin: 10px;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif

}
    
    
    
    @media screen and (min-width: 601px){
      .subject-flex{
        justify-content: space-between;
      }
      #sub-box{
      min-width: 150px;
      min-height: 150px;
      font-size: larger;
    }
    }
    
    
    
    
    
    </style>