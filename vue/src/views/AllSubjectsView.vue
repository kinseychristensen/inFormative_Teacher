<template>

<div class="class-container">
        <div class = "home-title-view">
          <h1 class="page-title">Subject Database</h1>
          <p class="logged-in-title">Here is a list of all completed subjects in our database.  Scroll or use the search bar to find what you need. </p>
        </div>
        
  
    <div class="loading" v-if="isLoading">Loading...</div>
    
    <div v-else class="all-subjects-field">
        <input type="text" v-model="searchTerm" id="search-bar" placeholder="Search"/>
        <router-link id="return-to-create" class="button-link" v-bind:to="{name: 'create-subject'}">Return to Create My Own</router-link>
        <div id="sub-loop">
            <div v-for="sub in searchSubjects" v-bind:key="sub.id" class="sub-grid">
                <div id="sub-code">{{ sub.code }}</div>
                <div id="sub-desc">{{ sub.description }}</div>
                <div id="class-selector">
                    <form id="flex-sub-clone" v-on:submit.prevent="makeClone(sub.id, selectedClassId)">
                    <label for="class-select" class="class-sel-label">Add Subject To:</label>
                    <select name="classToAddTo" id="class-select" v-model="selectedClassId">
                    <option v-bind:value="0">Please Select a Class</option>
                     <option v-for="schoolClass in schoolClasses" v-bind:key="schoolClass.classId" 
                        v-bind:value="schoolClass.classId">{{ schoolClass.className }}</option>
                    </select>
             
          <label for="sub-color" id="sub-color-label">Subject Color:</label>
          <select id="sub-color" v-model="colorPicked">
            <option v-for="color in colors" v-bind:key="color.val" v-bind:value="color.val">{{color.colorName}}</option>
            
          </select>



                    <button class="button-link" id="clone">Clone</button>
                    </form>
                </div>
                <div id="sub-tops">
                    <div v-for="tops in sub.topics" v-bind:key="tops.id">
                        <div id="topic-line">{{ tops.code }} : {{ tops.description }}</div>
                        <div id="sub-lessons">
                    <div v-for="lesson in tops.lessons" v-bind:key="lesson.id">
                        <div>{{ lesson.code }}: {{ lesson.description }}</div>
                    </div>
                    </div>
                </div>
                    </div>
        </div>
        </div>
    </div>
    </div>
  
    </template>
    
    <script>
   
   import SubjectService from '../services/SubjectService';
   import ClassService from '../services/ClassService';
    
    export default {
      name: 'AllSubjectsView',
     
    data() {
      return {
        schoolClasses: [],
        isLoading: true,
        selectedClassId: 0,
        colorPicked: 0,
        searchTerm: '',
        unfilteredSubjects: [{id: 0, code: ' ', description: ' '}],
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
    computed: {
        searchSubjects() {
            let subjects = this.unfilteredSubjects;

            if(this.searchTerm === ''){
                return subjects;
            }else{
                subjects = subjects.filter((item) => {
                    return item.description.toLowerCase().includes(this.searchTerm.toLowerCase())
                })

            }
            return subjects;
        
            }

    },
    methods: {
      handleError(error, verb) {
          if (error.response) {
            this.$store.commit('SET_NOTIFICATION',
              "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " subject list. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " subject list. Request could not be created.");
          }
        },

async makeClone(subjectId, classId){
    if(this.selectedClassId != 0){
    try {
        const response = await SubjectService.addSubject(subjectId, classId, this.colorPicked);

    }catch (error) {
        this.handleError(error, 'cloning');
    }finally {
        this.$router.push({name: 'home'});
    }
}
    },

        async retrieveClass(){
    try {
   
      const response = await ClassService.getCurrentClasses();
      this.schoolClasses = response.data;
    }catch (error) {
      this.handleError(error, 'retrieving');
    }finally {
      this.isLoading = false;
    } 

    },
      async retrieveSubjects(){
        try {
          this.isLoading = true;
          const response = await SubjectService.getAllSubjects()
           this.unfilteredSubjects = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.retrieveClass();
        }
        }, 
      
    
    },
    created(){
    
     this.retrieveSubjects();
      
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
      
      
      .home-nav-tool {
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
.all-subjects-field{
    display: grid;
    grid-template-columns: 1fr 250px;
    grid-template-areas: 
    "search-bar return-to-create"
    "sub-loop sub-loop"
    ;
}

    #search-bar{
        grid-area: search-bar;
    }
    #return-to-create{
        grid-area: return-to-create;
    }
    #sub-loop{
        grid-area: sub-loop;
    }
        .sub-grid{
        display: grid;
        grid-template-columns: 250px 1fr;
        grid-template-areas: 
        "sub-code sub-tops"
        "sub-desc sub-tops"
        ". sub-tops"
        "class-selector sub-tops"
       
        ;
        color: black;
        margin: 2px;
        padding: 10px;
        border-radius: 15px;
        border-color:  #dd7e6bff;
        border-style: double;
        text-decoration: none;
    }
    .sub-grid:nth-child(4n-3){
        border-color:  #dd7e6bff;
    }
    .sub-grid:nth-child(4n-2){
        border-color:  #f6b26bff;
    }
    .sub-grid:nth-child(4n-1){
        border-color:  #ffd966ff;
    }
    .sub-grid:nth-child(4n-0){
        border-color:  #93c47dff;
    }

#class-selector{
    grid-area: class-selector;
}
#flex-sub-clone{
    display: grid;
    grid-template-columns: 1fr;
    grid-template-areas:
    "class-sel-label"
    "class-select"
    "sub-color-label"
    "sub-color"
    "clone"
}
#class-sel-label{
  grid-area: class-sel-label;
}
#class-select{
  grid-area: class-select;
}
#sub-color-label{
  grid-area: sub-color-label;
}
#sub-color{
  grid-area: sub-color;
}

    .button-link{
        background-color:#93caef;;
        color: black;
        text-align: center;
        margin: 2px;
        padding: 10px;
        border-radius: 15px;
        text-decoration: none;
        width: 200px;
        }




#sub-code{
    grid-area: sub-code;
}
#sub-desc{
    grid-area: sub-desc;
}
#clone{
        background-color:  #ffd966ff;
        grid-area: clone;
}
#sub-tops{
    grid-area: sub-tops;
    display: flexbox;
    flex-direction: column;
    padding: 5px;
}
#sub-lessons{
    grid-area: sub-tops;
    display: flexbox;
    padding: 5px;
}
#topic-line {
      background-color: #b2c8a5;
    }

@media screen and (max-width: 600px){
.sub-grid{
        display: grid;
        grid-template-columns: 1fr 1fr;
        grid-template-areas: 
        "sub-code  sub-desc"
        "sub-tops sub-tops"
        "class-selector class-selector"
       
        ;
       
    }
  
  }
    </style>