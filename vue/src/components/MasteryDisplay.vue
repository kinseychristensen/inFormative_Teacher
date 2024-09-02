<template>

        <div class="mastery-flex">
          <div class="topic-loop" v-for="topic in subject.topics" v-bind:key="topic.topicId" id="sub-box">
            <div id="topic-item">{{ topic.code }} : {{ topic.description }}</div>
            <div class="lesson-loop" v-for="lesson in topic.lessons" v-bind:key="lesson.lessonId">
               <div id="lesson-item"> {{ lesson.code }} : {{ lesson.description }}</div>
                <div id="mastery">
                    <MasteryCalculator :studentId = "studentId" :lessonId = "lesson.id" :notAttempted="subject.notAttempted"
                            :below = "subject.below" :approaching="subject.approaching" :proficient="subject.proficient"
                            :mastered = "subject.mastered"/>
                </div>
            </div>
           
          
       </div> </div>
        
        
        
        </template>
        
        <script>
    
        import ScoreService from '../services/ScoreService';
        import SubjectService from '../services/SubjectService';
import MasteryCalculator from './MasteryCalculator.vue';
      
    
    
    
        export default {
        props: ['subjectId', 'studentId'],
        components: {
           MasteryCalculator,
          },
        data (){
            return {
                isLoading: true,
                subject: {},
            };
        },
        
    
        methods: {
            async retrieveSubjectDetails(){
            try {
              this.isLoading = true;
              const response = await SubjectService.getSubjectDetails(this.subjectId);
              this.subject = response.data;
            }catch (error) {
              this.handleError(error, 'retrieving');
            }finally {
              this.isLoading = false;
            }
            }, 
        },
        
        created(){
            this.retrieveSubjectDetails();
        
        }
        
        }
        
        </script>
        <style>
        
        .mastery-flex{
            border-radius: 15px;
            margin: 10px;
            display: flexbox;
        }

    .topic-loop{
        max-width: 1000px;
       padding: 3px;   
       margin: auto;
       font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif
        }
    
        .topic-loop:nth-child(odd){
            background-color: white;
        }
        .topic-loop:nth-child(even){
            background-color:  #d9d9d9ff;
        }
     
        #topic-item{
            background-color:aqua;
        }
        .lesson-loop{
            display: grid;
            grid-template-columns: 2fr 1fr;
            border: 1px;
            border-color: black;
            border-style: dashed;
            vertical-align: center;
        }
        #lesson-item{
            text-align: left;
            
        }
        #mastery {
            vertical-align: center;
        }

    @media screen and (max-width: 600px) {
        .topic-loop{
            max-width: 80vw;
        }
    }
    
        
        
        </style>