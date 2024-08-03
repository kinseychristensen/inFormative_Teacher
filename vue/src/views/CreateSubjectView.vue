<template>

    <div class="home-container">
        <NavTool class="nav-tool"/>
        <div class = "title-view">
          <h1 class="page-title">Create Subject</h1>
          <p class="logged-in-title">Subjects may not be edited after creation.  Please ensure you enter all information correctly.  For additional instructions, 
            please view our tutorial video. 
          </p>
          <p class="error">{{ errorMessage }}</p>
        </div>
        <Logo class="home-logo"/>
   
    <div class="class-container">
        <div class="loading" v-if="isLoading">Loading...</div>
    
       <div v-else>

       <div v-if="isEditing" class="subject-field">

        <form class="form-field" v-on:submit.prevent="checkSubForm">
          <div id="clone-search-label">Your work may already be done! Check to see if your subject standards are already in
          <router-link id="search-subs" class="button-link"
          v-bind:to="{ name: 'all-subjects'}">Our Database</router-link></div>
          <button class="complete-button">Complete</button>
          <label for="subject" class="sub-label">Title:</label>
          <input id="subject" v-model="newSubject.code"/>
          <label for="sub-desc" class="sub-desc-label">Description: </label>
          <textarea id="sub-desc" v-model="newSubject.description"/>
       
          <div class="mastery">
            <div class="mastery-item">
              <p>Not Attempted:</p>
            <input v-model.number="newSubject.notAttempted"/> </div>
            <div class="mastery-item">
              <p>Below:</p>
              <input v-model.number="newSubject.below"/></div>
            <div class="mastery-item">
              <p>Approaching:</p>
              <input v-model.number="newSubject.approaching"/></div>
            <div class="mastery-item">
              <p>Proficient:</p>
              <input v-model.number="newSubject.proficient"/></div>
            <div class="mastery-item">
              <p>Mastered:</p>
              <input v-model.number="newSubject.mastered"/></div>
          </div>

          <label for="class-select" class="class-sel-label">Add Subject To:</label>
          <select name="classToAddTo" id="class-select" v-model="newSubject.classId">
            <option v-bind:value="0">Please Select a Class</option>
            <option v-for="schoolClass in schoolClasses" v-bind:key="schoolClass.classId" v-bind:value="schoolClass.classId">{{ schoolClass.className }}</option>

          </select>

        </form>
      
          <div class="topic-display"> 
            <div id="units-lessons">
            <div id="units">Units</div>
            <div id="lessons">Lesson Standards</div></div>

            <div class="topic-loop" v-for="currTopic in newSubject.topics" v-bind:key="currTopic.id">
            
           
            <div class="standard">
              <div v-for="currLesson in currTopic.lessons" v-bind:key="currLesson.id">
            

              <form class="standard-form" v-on:submit.prevent="addLesson(currLesson, currTopic.id)">
                <label for="lesson-code" class="less-code-label">Lesson Code:</label>
                <input id="lesson-code" v-model.lazy="currLesson.code"/>
                <label for="lesson-desc" class="less-desc-label">Lesson Description:</label>
                <textarea id="less-desc" v-model.lazy="currLesson.description"></textarea>
                <button class="add-lesson-button">Add Another Lesson</button>
              </form>
              </div></div>
            
            <form v-on:submit.prevent="addTopic(currTopic)" class="topic-item">
              <div><label for="topic-code" class="top-code-label">Code:</label>
              <input id="topic-code" v-model.lazy="currTopic.code"/></div>
             <div> <label for="topic-desc" class="top-desc-label">Description:</label><p></p>
              <textarea id="topic-desc" v-model.lazy="currTopic.description"></textarea></div>
             <div> <button class="add-topic-button">Add Another Topic</button></div>
            </form>
          </div>
          </div>

        </div>
        <div v-else> 
          <div>Subject Title: {{ newSubject.code }}</div>
          <div>Subject Description: {{ newSubject.description }}</div>
            <div class="mastery">
            <div class="mastery-item">
              <p>Not Attempted: {{ newSubject.notAttempted }}</p>
            </div>
            <div class="mastery-item">
              <p>Below: {{ newSubject.below }}</p>
            </div>
            <div class="mastery-item">
              <p>Approaching:  {{ newSubject.approaching }}</p>
            </div>
            <div class="mastery-item">
              <p>Proficient:  {{ newSubject.proficient }}</p>
            </div>
            <div class="mastery-item">
              <p>Mastered:  {{ newSubject.mastered }}</p>
            </div>
          </div>
          <div id="units-lessons">
            <div id="units">Units</div>
            <div id="lessons">Lesson Standards</div></div>
          <div class="topic-display"> 
            <div class="topic-loop" v-for="currTopic in newSubject.topics" v-bind:key="currTopic.id">
            {{ currTopic.code }} : {{ currTopic.description }}
           
            <div class="standard">
              
              <div v-for="currLesson in currTopic.lessons" v-bind:key="currLesson.id">
                {{ currLesson.code }} : {{ currLesson.description }}
            </div>
            </div>
            </div>
            </div><p class="error">Important note: Incomplete rows will be ignored.  Please include both a short refrence code and a description for each unit and lesson you intend to use.</p>
          <button class="button-link" id="return-to-edit" @click="toggleReview">Return to Edit</button>
          <button class="button-link" id="complete-confirm" @click="createSubject">Confirm</button>
        </div>
      </div>
    </div>
  </div>  



    </template>
    
    <script>
    import Logo from '../components/Logo.vue';
    import NavTool from '@/components/NavTool.vue';
    import SubjectService from '../services/SubjectService';
    import ClassService from '../services/ClassService';
    

    
    export default {
      name: 'CreateSubjectView',
      components: {
        NavTool,
        Logo
    },

    data() {
      return {
        newSubject: {
          code: '',
          description: '',
          notAttempted: 0,
          below: 1,
          approaching: 2,
          proficient: 3,
          mastered: 4,
          classId: 0,
          topics: [
          {
            id: 0,
          code: '',
          description: '',
          lessons: [ {
            id: 0,
          code: '',
          description: '',
        }]}],
        },

       topicIdInd: 0,
        chosenSchool: '',
        schoolClasses: [],
        isLoading: true,
        newSubjectId: 0,
       errorMessage: '',
       isEditing: true,
 
       
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
       
        addLesson(currLesson, currTopicId){
          this.errorMessage='';
          if(currLesson.code ===''){
            this.errorMessage += "Lesson must have a short code for refrence.  It may be letters or numbers.  ";
          }
          if(currLesson.description === ''){
            this.errorMessage += "Lesson must have a description.  "
          }
          if(this.errorMessage === ''){
           let newId = currLesson.id + 1;
            this.newSubject.topics [currTopicId].lessons.push({id: newId, code: '', description: ''});
          }
        },

        addTopic(currTopic){
          this.errorMessage='';
          if(currTopic.code ===''){
            this.errorMessage += "Topic must have a short code for refrence.  It may be letters or numbers.  ";
          }
          if(currTopic.description === ''){
            this.errorMessage += "Topic must have a description.  "
          }
          if(this.errorMessage === ''){
            let newId = currTopic.id + 1;
            this.newSubject.topics.push({id: newId, code: '', description: '', lessons: [{id: 0, code: '', description: ''}]});
          }

        },
toggleReview(){
  this.isEditing = !this.isEditing;
},
    
checkSubForm(){
  this.errorMessage = '';
if (this.newSubject.code === '') {
    this.errorMessage += "Please set a title for the subject. ";
} 
if (this.newSubject.description === ''){
  this.errorMessage += "Please enter a description.  ";
}
if (this.newSubject.classId === 0) {
  this.errorMessage += "Subject must be assigned to a current class.  ";
 }
 if (this.newSubject.topics.length === 0) {
  this.errorMessage += "Subject must have at least one topic.  ";
 }
 if(this.errorMessage.length === 0){
  this.toggleReview();
  
} else {
        this.errorMessage += "Unable to complete.  Please correct errors and try again.";
      }
},

      async createSubject(){
      
        try {
          this.isLoading = true;
          console.log("Creating subject id");
        
          const response = await SubjectService.createSubject(this.newSubject, this.newSubject.classId);
          this.newSubjectId = response.data;
        }catch (error) {
          this.handleError(error, 'creating');
        }finally {
          console.log(this.newSubjectId);
          this.$router.push({name: 'subject-page', params: {subjectId: this.newSubjectId, classId: this.newSubject.classId}});

      }
        }, 

      
    
        async retrieveClass(){
    try {
      this.isLoading = true;
      const response = await ClassService.getCurrentClasses();
      this.schoolClasses = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.isLoading = false;
    } 

    },
  },
    created(){

      this.retrieveClass();
      
    }
    }
    
    
    </script>
    
    <style scoped>
    .home-container {
        display: grid;
        grid-template-columns: 250px 1fr 1fr;
        grid-template-areas: 
          "nav title logo"
          "nav class class"
          ". class class"
          ;
        gap: 15px;
      }


      .nav-tool {
        grid-area: nav;
        margin-right: 20px;
      }
      
      .home-logo {
        grid-area: logo;
        justify-self: right;
      }
      
      .home-title-view {
        grid-area: title;
        justify-content: center;
        text-align: center;
      }
      
      .class-container {
        grid-area: class;
      }

      

      .subject-field{
        display: grid;
        grid-template-columns: 1fr;
        grid-template-areas:
        "form "
        "topic-display "
        "add-topic "
        ;
        gap: 15px;
      }

      .form-field{
        grid-area: form;
        display: grid;
        grid-template-columns: 250px 1fr 250px;
        grid-template-areas:
        "search search search"
        "sub-label subject complete-button"
        "sub-desc-label sub-desc sub-desc"
        "mastery mastery mastery"
        ;
        gap: 15px;
      }

      .mastery{
        grid-area: mastery;
        display: flex;
        flex-direction: row;
        align-content: space-around;
        gap: 20px;
        
      }
      .mastery-item {
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }
      .mastery-item:nth-child(5n-4){
       background-color: #a4c2f4ff;
      }
      .mastery-item:nth-child(5n-3){
       background-color: #dd7e6bff;
      }
      .mastery-item:nth-child(5n-2){
       background-color: #f6b26bff;
      }
      .mastery-item:nth-child(5n-1){
       background-color: #ffd966ff;
      }
      .mastery-item:nth-child(5n-0){
       background-color: #93c47dff;
      }
      .sub-label{
        grid-area: sub-label;
      }
      #subject{
        grid-area: subject;
      }
      .complete-button{
        grid-area: complete-button;
      }
      .sub-desc-label{
        grid-area: sub-desc-label;
      }
      #sub-desc{
        grid-area: sub-desc;
      }
      .topic{
        grid-area: topic;
      }
      .topic-display{
        grid-area: topic-display;
      }
      .topic-loop{
        display: grid;
        grid-template-columns: 1fr 3fr;
        grid-template-areas: 
        "topic-item standard"
        ;
        gap: 5px;
        padding: 5px;
        border: 2px;
        border-color: black;

      }
      .topic-loop:nth-child(odd){
        background: #a4c2f4ff;
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }
      .topic-loop:nth-child(even){
        background: rgb(221, 223, 223);
        gap: 5px;
        padding: 15px;
        border: black;
        border-radius: 25px;
      }

      .topic-item{
        grid-area: topic-item;
        display: flexbox;
        flex-direction: column;
        align-content: flex-start;
        gap: 5px;
      }
      .standard{
        grid-area: standard;
        display: flexbox;
        flex-direction: row;
        gap: 20px;
      }

      .add-topic{
        grid-area: add-topic;
      }
      .error{
        color: red;
        font-size:x-large;
      }
      #topic-desc{
        flex-direction: stretch;

      }
      #units-lessons{
        display: grid;
        margin-top: 20px;
        grid-template-columns: 1fr 3fr;
        grid-template-areas: 
        "units lessons"
        
        ;
      }
    #units{
      grid-area: units;
    }
    #lessons{
      grid-area: lessons;
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
#return-to-edit{
  background-color: #dd7e6bff;
}
#complete-confirm{
  background-color: #93c47dff;
}
#clone-search-label {
  grid-area: search;
}
    </style>