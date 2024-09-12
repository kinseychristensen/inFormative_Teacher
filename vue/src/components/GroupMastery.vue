<template>


<div v-for="lesson in lessons" v-bind:key="lesson.id" class="lessons-loop">
   <div class="lesson-title"> {{ lesson.code }} - {{ lesson.description }}</div>
   <div class="student-loop">
<div v-for="student in students" v-bind:key="student.id" class="single-student">
    <div class="student-name" v-if="showNames">{{ student.firstName }} {{ student.lastName }}</div><div class="studentId" v-if="!showNames">Id: #{{ student.schoolId }}</div><MasteryCalculator class="mastery" :approaching="approaching" :below="below" 
    :not-attempted="notAttempted" :proficient="proficient" :mastered="mastered" :student-id="student.id" :lesson-id="lesson.id" :filter="0" ></MasteryCalculator>
</div>
</div>
</div>
</template>

<script>
    

import MasteryCalculator from './MasteryCalculator.vue';

export default {
props: ['notAttempted', 'below', 'approaching', 'proficient', 'mastered', 'lessons', 'students', 'showNames'],
components: {
   MasteryCalculator
  },
data (){
    return {
        isLoading: true,
        score: {},
        level: '',
    };
},
computed: {
   
    }

}
</script>
<style scoped>
.lessons-loop{
    display: grid;
   grid-template-columns: 1fr;
  
    border-radius: 15px;
    border-style: double;
}
.lesson-title{
    justify-self: center;

}
.student-loop{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}
.single-student{
    width: 150px;
    margin: 5px;
    background: rgb(221, 223, 223);
    padding: 2px;
    height: 40px;
}
.mastery {
    border-radius: 5px;
    margin: auto;
    width: 125px;
}

</style>