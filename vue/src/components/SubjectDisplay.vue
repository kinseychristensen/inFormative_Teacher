<template>


<div class="subject-flex">
  <router-link v-for="subject in subjects" v-bind:key="subject.id" id="sub-box" :class="theClass(subject.color)"
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


.subject-flex{
display: flex;
flex-wrap: wrap;
justify-content: space-around;
gap: 5px;
}
#sub-box{
  min-width: 100px;
  min-height: 100px;
  border-radius: 15px;
  text-align: center;
  text-decoration: none;
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






</style>