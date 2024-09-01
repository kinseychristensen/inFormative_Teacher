<template>


    <div class="subject-flex">
      <button v-for="subject in subjects" v-bind:key="subject.id" id="sub-box" :class="theClass(subject.color)"
       >
      <a id="sub-text">{{ subject.code }}</a>
      <div id="score-display"> 
        <ScoreDisplay :studentId="studentId" :subjectId="subject.id"/>
      </div>
    </button>
</div>
    
    
    
    </template>
    
    <script>
    import SubjectService from '../services/SubjectService';
    import ScoreDisplay from './ScoreDisplay.vue';
   
    
    export default {
    props: ['classId', 'studentId'],
    components: {
        ScoreDisplay,
      },
        
    data (){
        return {
            isLoading: true,
            subjects: [],
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
    
    },
    
    created(){
        this.retrieveSubjects();
    
    }
    
    
    }
    
    </script>
    <style>
    
    
    .subject-flex{
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    }
    
    #sub-box{
    
      min-height: 100px;
        width: 90vw;
      border-radius: 15px;
      text-align: center;
      text-decoration: none;
      margin: auto;
    }
    
    #sub-text {
      vertical-align: middle;
      color: black;
      text-decoration: none;
      line-height: 100px;
      height: 100px;
      text-align: center;
    
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