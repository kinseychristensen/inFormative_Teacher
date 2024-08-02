<template>


<div class="subject-flex">
  <router-link v-for="subject in subjects" v-bind:key="subject.id"
  v-bind:to="{name: 'subject-page', params: {subjectId: subject.id}}" class="subject-title">
  {{ subject.code }}
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
    this.retrieveSubjects();

}


}

</script>
<style>

.subject-title{
  color: black;
  text-align: center;
  margin: 2px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  font-size: larger;
  min-width: 75px;
  max-width: 150px;
  min-height: 75px;
  max-height: 150px;
  gap: 5px;
  
}

.subject-title:nth-child(4n-3){
  background-color: #dd7e6bff;
}
.subject-title:nth-child(4n-2){
  background-color: #f6b26bff;
}
.subject-title:nth-child(4n-1){
  background-color: #ffd966ff;
}
.subject-title:nth-child(4n-0){
  background-color: #93c47dff;
}
.subject-flex{
display: flex;
flex-wrap: wrap;
gap: 5px;
}


</style>