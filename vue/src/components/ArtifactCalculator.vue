<template>

    <div v-if="isLoading">Loading...</div>
    <div v-else>
    {{ lesson.code }} - {{ lesson.description }}
  
    <!-- Ensure the chart gets the right data -->
    <ArtifactScatterChart :data="chartData" :options="options" :chartId="chartId"/>
  
    <div>
    
    </div>
</div>
  </template>
  
  <script lang="ts">

import ArtifactScatterChart from './ArtifactScatterChart.vue';
 
  
  export default {
    props: ['notAttempted', 'below', 'approaching', 'proficient', 'mastered', 'lesson', 'students', 'artifacts', 'chartId'],
    components: {
      ArtifactScatterChart,
    },
    data() {
      return {
        isLoading: false,
        scores: [],
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
     
    },}
   

 
  </script>
  