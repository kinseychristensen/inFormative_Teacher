<template>

<div id="group-roster-grid">
    <div id="instructions">To remove a student from group, click their name.</div>
<div class="roster"><div class="roster-flex" v-for="student in currentGroupStudents" v-bind:key="student.id" @click="removeStudent(student.schoolId)">
    {{ student.firstName }} {{ student.lastName }}, 
</div></div>

<form id="add-student" v-on:submit.prevent=""><select v-model="addingStudent">
    <option v-bind:value="0">Select Student</option>
    <option v-for="student in studentsNotInGroup" v-bind:key="student.id" v-bind:value="student.id">
        {{ student.firstName }} {{ student.lastName }}, </option>
</select>
<button @click="addStudent">Add Student</button>
</form>

<button id="save-roster" @click="editRoster">Save Changes</button>
<div id="save-notes" v-if="unsavedChanges">Please save changes before exiting!</div>
</div>

</template>

<script>
import GroupService from '../services/GroupService';
import StudentService from '../services/StudentService';

export default {
    props: ['groupId', 'classId'],

    data() {
        return {
            isLoading: false,
            currentStudents: [],
            currentGroupStudents: [],
            initialGroupStudents: [],
            addingStudent: 0,
            unsavedChanges: false,
         
            
        };
    },
    computed: {
        studentsNotInGroup(){
            let students=this.currentStudents;

                this.currentGroupStudents.forEach(groupStudent => {
                    students = students.filter((item)=> {
                        return (item.id != groupStudent.id)
                    }) })
                        return students;
        },


    },

    methods: {
        handleError(error, verb) {
          if (error.response) {
            this.$store.commit('SET_NOTIFICATION',
              "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " new group. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " new group. Request could not be created.");
          }
        },

        addStudent(){
            if(this.addingStudent != 0){
                let newStudents = this.currentGroupStudents;

           this.unsavedChanges = true;
                for(let i=0; i<this.currentStudents.length; i++){
                    if(this.addingStudent === this.currentStudents[i].id){
                        newStudents.push(this.currentStudents[i]);
                    }
                }
          this.currentGroupStudents = newStudents;
            }
        },


        removeStudent(schoolId){
      const shouldRemove = confirm("Are you sure you want to remove this student?");

      if(shouldRemove){
        let updatedRoster = [];
        this.unsavedChanges = true;
        for(let i = 0; i<this.currentGroupStudents.length; i++){
          if (this.currentGroupStudents[i].schoolId != schoolId) {
            updatedRoster.push(this.currentGroupStudents[i]);
          }
        }
        this.currentGroupStudents = updatedRoster;  
      }
    },

   async getStudents(){
        try{
            this.isLoading = true;
            const response = await StudentService.getClassRoster(this.classId);
            this.currentStudents = response.data;
        }catch (error) {
                this.handleError(error, 'retrieving');
            }finally {
                this.getGroupRoster();
            }
   },

   async getGroupRoster(){
        try{
            this.isLoading = true;
            const response = await StudentService.getGroupRoster(this.groupId);
            this.currentGroupStudents = response.data;
            this.initialGroupStudents = response.data;
        }catch (error) {
                this.handleError(error, 'retrieving');
            }finally {
                this.isLoading=false;
            }
   },

   async editRoster(){
          try {
            this.isLoading=true;
            const response = await StudentService.editGroupRoster(this.currentGroupStudents, this.groupId);
            this.students = response.data;
          }catch (error) {
            this.handleError(error, 'editing');
          }finally {
                this.isLoading = false;
                this.unsavedChanges = false;
          }
        },

    },
    created() {
        this.getStudents();

    }



}

</script>

<style>

#group-roster-grid{
    display: grid;
    grid-template-columns: 1.5fr 1fr;
    grid-template-areas: 

    "roster instructions"
    "roster add-student"
    "roster add-student"
    "roster ."
    "save-notes save";
}   
#instructions {
    grid-area: instructions;
    margin-top: 15px;
}
.roster{
    grid-area: roster;
    display: flexbox;
    flex-wrap: nowrap;
    color: black;
  text-align: center;
  margin: 10px;
  padding: 10px;
  border-radius: 15px;
  text-decoration: none;
  background-color: white;

}
#add-student{
    grid-area: add-student;
}
#save-roster{
    grid-area: save;
}
#save-notes{
    grid-area: save-notes;
}
.roster-flex{
    padding: 3px;
}

</style>