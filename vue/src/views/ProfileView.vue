<template>

<div class="class-container">
        <div class = "home-title-view">
          <h1 class="page-title">My Settings</h1>
        </div>
      
   
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="class-field" id="settings-frame">
      
      <div  v-if="first">
        <div id="user-display">
        <div class="teacher-name"> Name: {{ teacher.firstName }} {{ teacher.lastName }}</div>
        <div class="teacher-email">Email: {{ teacher.username }}</div>
       <button @click="toggleUpdate" id="update-button" class="btn">{{buttonMsg}}</button>
       <p></p><p></p>
       <button class="btn"><router-link id="reset-button"  v-bind:to="{name: 'password-reset'}"
       >Reset Password</router-link></button>
      </div>
 
    <p></p><p></p>
       <div v-if="update">
        <form v-on:submit.prevent="verifyPassword">
          <label for="password1" id="password1-label"> Please enter your password to make changes:
            </label>
            <input type="password" id="password1" v-model="user.password"/>
            <p></p><p></p>
            <button class="btn">Verify</button>
        </form></div>
      
        <p></p><p></p>

       <div v-if="verified">
        <form v-on:submit.prevent="saveChanges" id="form-changes">
         
          <label for="first-name" id="first-name-label"> First Name: </label>
          <input type="text" id="first-name" v-model="editTeacher.firstName"/>
          <label for="last-name" id="last-name-label"> Last Name: </label>
          <input type="text" id="last-name" v-model="editTeacher.lastName"/>
          <label for="email" id="email-answer-label">  Email: </label>
          <input type="email" id="email-answer" v-model="editTeacher.username"/>
            
            <button id="save-button" class="btn">Save Changes</button>
        </form>
       </div>
      </div>

  
    </div>
  </div>
    </template>
    
    <script>
  
    import AuthService from '../services/AuthService';
import ClassService from '../services/ClassService';
    
    export default {
      name: 'ProfileView',
    
    data() {
      return {
        SchoolClasses: [],
        isLoading: true,
        teacher: '',
        update: false,
        buttonMsg: 'Update',
        user: {
          username: '',
          password: '',
        },
     
        editTeacher: {
          firstName: '',
          lastName: '',
          username: '',
        },
        verified: false,
        first: true,
      };
    },
    methods: {
      toggleUpdate(){
        if(this.update){
          this.update = false;
          this.buttonMsg = 'Update';
          this.verified = false;
          this.user.password = '';
          this.first = true;
        }else if(!this.update && this.verified) {
          this.update = false;
          this.buttonMsg = 'Update';
          this.verified = false;
          this.user.password = '';
          this.first = true;
          this.editTeacher.firstName = this.teacher.firstName;
          this.editTeacher.lastName = this.teacher.lastName;
          this.editTeacher.username = this.teacher.username;
        }else {
          this.update = true;
          this.first = true;
          this.buttonMsg = 'Cancel';
        }
      },
async verifyPassword() {

        AuthService.login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.verified = true;
            this.update = false;
          
}
})
        },

        async saveChanges(){
         
            AuthService.updateUser(this.editTeacher, this.teacher.id)
            .then(response => {

            if(response.status == 200) {
              this.$router.go(0);
            } })


        },


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
    
      async retrieveUser(){
        try {
          this.isLoading = true;
          const response = await AuthService.getUser();
          this.teacher = response.data;
          this.editTeacher.firstName = this.teacher.firstName;
          this.editTeacher.lastName = this.teacher.lastName;
          this.editTeacher.username = this.teacher.username;
          this.user.username = this.teacher.username;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.isLoading = false;
        }
        }, 
      
    
    },
    created(){
    
     this.retrieveUser();
      
    }
    }
    
    
    </script>
    
    <style scoped>
      
      .home-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
      
      .class-container {
      max-width: 1000px;
      margin: auto;

      }
      .class-field{
      display: flex;
      flex-direction: column;
  justify-content: center;
  min-width: 50vw;
  max-width: 100vw;

}
      
      #form-changes{
        display: grid;
        grid-template-columns: 1fr 2fr;
        grid-template-areas:
        "first-name-label first-name"
        "last-name-label last-name"
        "email-answer-label email-answer"
        ". save-button"
      }
      #first-name-label{
        grid-area: first-name-label;
      }
      #last-name-label{
        grid-area: last-name-label;
      }
      #first-name{
        grid-area: first-name;
      }
      #last-name{
        grid-area: last-name;
      }
      #email-answer-label{
        grid-area: email-answer-label;
      }
      #email-answer{
        grid-area: email-answer;
      }
      #save-button {
        grid-area: save-button;
      }
     #user-display{
      display: flexbox;
      flex-direction: column;
      flex-wrap: wrap;
      background-color: #93caef;
      gap: 20px;
      max-width: 1000px;  
      border-radius: 15px;
      padding: 15px;
      justify-content: space-evenly;
      align-items: center;
     }


button.btn{

width: 170px;
padding: 2px;
height: 30px;
border: black;
background-color: rgb(255, 217, 102,100);
color: black;
border-radius: 5px;
font-size: 15px;
cursor: pointer;
transition: background-color .3s ease;
font-weight: bold;  
margin: 2px;
text-decoration: none;
}

button.btn:hover{
background-color: yellow;
}
#reset-button{
  text-decoration: none;
}

@media screen and (max-width: 600px){
  #user-display{
      
      max-width: 100vw;  
     
     }
}


    </style>