<template>


    <div class="subject-flex">
      <router-link v-for="subject in subjects" v-bind:key="subject.id" id="archive-sub-box" 
      v-bind:to="{name: 'subject-page', params: {subjectId: subject.id, classId: classId}}" >
      <a id="sub-text">{{ subject.code }}</a>
    </router-link></div>
    
    
    
    </template>
    
    <script>
    import SubjectService from '../services/SubjectService';
    
    export default {
    props: ['classId'],
        
    data (){
        return {
            isLoading: true,
            subjects: [],
     
    
        };
    },
    
    
    methods: {
    async retrieveSubjects(){
        try {
          this.isLoading = true;
          const response = await SubjectService.getArchivedClassSubjects(this.classId);
          this.subjects = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
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
    justify-content: space-around;
    gap: 15px;
    }
    
    @media screen and (min-width: 601px){
      .subject-flex{
        justify-content: space-between;
      }
    }
    
    #archive-sub-box{
      min-width: 100px;
      min-height: 100px;
      border-radius: 15px;
      text-align: center;
      text-decoration: none;
      background-color:  #d9d9d9ff;
    }
    
    #sub-text {
      vertical-align: middle;
      color: black;
      text-decoration: none;
      line-height: 100px;
      height: 100px;
      text-align: center;
    
    }
    
   
    
    
    @media screen and (min-width: 601px){
      .subject-flex{
        justify-content: space-between;
      }
      #archive-sub-box{
      min-width: 150px;
      min-height: 150px;
      font-size: larger;
    }
    }
    
    
    
    
    
    </style>