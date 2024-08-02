<template>
     <div class="add-topic">
          <form v-on:submit.prevent="addTopic">
  <label for="topic-code" class="top-code-label">Topic Code:</label>
  <input id="topic-code" v-model="newTopic.code"/>
  <label for="topic-desc" class="top-desc-label">Topic Description:</label>
  <textarea id="topic-desc" v-model="newTopic.description"></textarea>
  <button class="add-topic-button">Add Topic</button>
</form>

        </div>
    
    <CreateLesson></CreateLesson>
    
    
    </template>
    <script>
        import CreateLesson from './CreateLesson.vue';

    export default {
        props: {
            topic: {
                type: Object,
                required: true
            }
        },
    
        data () {
            return{
        formShowing: false,
        editedTopic: {
          code: this.topic.code,
          description: this.topic.description,
          lessons: this.topic.lessons,
        },
        errorMessage: '',
            };
        }
    }
    
    
    </script>
    <style>
    
    
    </style>