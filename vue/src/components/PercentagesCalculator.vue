<template>

    <div v-if="isLoading">Loading...</div>
    <div v-else>
    {{ lesson.code }} - {{ lesson.description }}
  
    <!-- Ensure the chart gets the right data -->
    <PercentChart :data="chartData" :options="options" :chartId="chartId"/>
  
    <div>
    
    </div>
</div>
  </template>
  
  <script lang="ts">
  import ScoreService from '../services/ScoreService';
  import PercentChart from './PercentChart.vue';
  
  export default {
    props: ['notAttempted', 'below', 'approaching', 'proficient', 'mastered', 'lesson', 'students', 'chartId'],
    components: {
      PercentChart
    },
    data() {
      return {
        isLoading: false,
        notAttemptedScores: 0,
        belowScores: 0,
        approachScores: 0,
        profScores: 0,
        mastScores: 0,
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      };
    },
    computed: {
      chartData() {
        return [
          this.notAttemptedScores,
          this.belowScores,
          this.approachScores,
          this.profScores,
          this.mastScores
        ];
      },
     
    },
   

    methods: {
      handleError(error, verb) {
        if (error.response) {
          this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
        } else if (error.request) {
          this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Server could not be reached.");
        } else {
          this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Request could not be created.");
        }
      },
      
      retrieveTopScore() {
      this.isLoading = true;
      let promises = this.students.map(student => 
        ScoreService.getTopScore(student.id, this.lesson.id)
          .then(response => {
            let score = response.data;
            if (score.score == null) {
              score.score = 0;
            }
            if (score.score <= this.notAttempted) {
              this.notAttemptedScores += 1;
            } else if (score.score <= this.below) {
              this.belowScores += 1;
            } else if (score.score <= this.approaching) {
              this.approachScores += 1;
            } else if (score.score <= this.proficient) {
              this.profScores += 1;
            } else {
              this.mastScores += 1;
            }
          })
      );
      
      Promise.all(promises)
        .then(() => {
          this.isLoading = false;
        })
        .catch(error => {
          this.handleError(error, 'retrieving');
        });
    }
  },

    created() {
      this.retrieveTopScore();
    }
  };
  </script>
  