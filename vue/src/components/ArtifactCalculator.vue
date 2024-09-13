<template>
    <div v-if="isLoading">
        <h3>Student Growth Across Artifacts</h3>
        <p>This chart is not reactive.  Please refresh the page if you wish to change datapoints.</p>
      <button @click="showChart">Generate Growth Chart</button>
    </div>
    <div v-else>
      <!-- Ensure the chart gets the right data -->
       <h3>Student Growth Across Artifacts</h3>
       <div>The y axis is number of students, the x axis are the different artifacts used for the selected indicators. Hover over datapoints to see stacked items.</div>
     
      <div>
        <ArtifactScatterChart :data="chartData" :chartId="chartId" :options="options"/>
      </div>
      <p>This chart is not reactive.  Please refresh the page if you wish to change datapoints.</p>
    </div>
  </template>
  
  <script lang="ts">
  import ArtifactScatterChart from './ArtifactScatterChart.vue';
  
  export default {
    props: ['notAttempted', 'below', 'approaching', 'proficient', 'mastered', 'artifacts', 'chartId'],
    components: {
      ArtifactScatterChart
    },
    data() {
      return {
        isLoading: true,
        calculatedBelow: {},
        calculatedNotAttempted: {},
        calculatedProficient: {},
        calculatedApproaching: {},
        calculatedMastered: {},
        chartData: {
          datasets: []  // This is the structure for Chart.js datasets
        },
        options: {
  scales: {
    x: {
      type: 'category',
      labels: this.artifacts.map(artifact => artifact.description),
      position: 'bottom'
    },
    y: {
      beginAtZero: true,
    }
  },
  responsive: true,
  plugins: {
    legend: {
      display: true,
    },
    tooltip: {
      enabled: true,
    }
  },
  elements: {
    point: {
      radius: 15,          // Base radius of the points
      hoverRadius: 30,    // Larger on hover
      pointStyle: 'rect'  // Change shape to differentiate overlapping points
    }
  }
}


      };
    },
    methods: {
      calcNotAttempted() {
        let dataset = {
          label: 'Not Attempted',
          backgroundColor: '#dd7e6bff',
          data: [],
        };
        this.artifacts.forEach((artifact) => {
          let numScores = artifact.scores.filter(score => score.score <= this.notAttempted).length;
          dataset.data.push({ x: artifact.description, y: numScores });
        });
        this.calculatedNotAttempted = dataset;
      },
      
      calcBelow() {
        let dataset = {
          label: 'Below',
          backgroundColor: '#f6b26bff',
          data: [],
        };
        this.artifacts.forEach((artifact) => {
          let numScores = artifact.scores.filter(score => score.score > this.notAttempted && score.score <= this.below).length;
          dataset.data.push({ x: artifact.description, y: numScores });
        });
        this.calculatedBelow = dataset;
      },
      
      calcApproaching() {
        let dataset = {
          label: 'Approaching',
          backgroundColor: '#ffd966ff',
          data: [],
        };
        this.artifacts.forEach((artifact) => {
          let numScores = artifact.scores.filter(score => score.score > this.below && score.score <= this.approaching).length;
          dataset.data.push({ x: artifact.description, y: numScores });
        });
        this.calculatedApproaching = dataset;
      },
      
      calcProficient() {
        let dataset = {
          label: 'Proficient',
          backgroundColor: '#93c47dff',
          data: [],
        };
        this.artifacts.forEach((artifact) => {
          let numScores = artifact.scores.filter(score => score.score > this.approaching && score.score <= this.proficient).length;
          dataset.data.push({ x: artifact.description, y: numScores });
        });
        this.calculatedProficient = dataset;
      },
      
      calcMaster() {
        let dataset = {
          label: 'Mastered',
          backgroundColor: '#93caef',
          data: [],
        };
        this.artifacts.forEach((artifact) => {
          let numScores = artifact.scores.filter(score => score.score > this.proficient).length;
          dataset.data.push({ x: artifact.description, y: numScores });
        });
        this.calculatedMastered = dataset;
      },
      
      showChart() {
        this.calcNotAttempted();
        this.calcBelow();
        this.calcApproaching();
        this.calcProficient();
        this.calcMaster();
  
        // Consolidating datasets
        this.chartData.datasets = [
          this.calculatedNotAttempted, 
          this.calculatedBelow, 
          this.calculatedApproaching,
          this.calculatedProficient, 
          this.calculatedMastered
        ];
  
       
        this.isLoading = false;
      }
    }
  };
  </script>
  