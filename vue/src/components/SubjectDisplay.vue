<template>


<div class="subject-flex">
  <router-link v-for="subject in subjects" v-bind:key="subject.id" :class="theClass(subject.color)"
  v-bind:to="{name: 'subject-page', params: {subjectId: subject.id, classId: classId}}" class="subject-title">
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

.subject-flex{
display: flex;
flex-wrap: wrap;
gap: 5px;
}


.pink{
  background-color: rgb(237, 82, 139);
  min-width: 50px;
}
.red{
  background-color: red;
  min-width: 50px;
}
.orange{
  background-color: rgb(255, 115, 0);
  min-width: 50px;
}
.yellow{
  background-color: rgb(246, 255, 0);
  min-width: 50px;
}
.green{
  background-color: rgb(94, 255, 0);
  min-width: 50px;
}
.blue{
  background-color: rgb(0, 136, 255);
  min-width: 50px;
}
.purple{
  background-color: rgb(149, 35, 144);
  min-width: 50px;
}
.gray{
  background-color: rgb(136, 117, 117);
  min-width: 50px;
}






</style>