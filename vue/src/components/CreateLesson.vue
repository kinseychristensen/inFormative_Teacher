<template>
Lesson: 
{{ newLesson }}




</template>
<script>
export default {
    props: ['subject'],

    data () {
        return{
            newLesson: {
          id: 0,
          code: '',
          description: '',
          topicId: 0,
        },
        };
    }
}


</script>
<style>


</style>