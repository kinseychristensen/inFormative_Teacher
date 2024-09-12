<template>
    <div>
      <canvas :id="chartId" width="400" height="400"></canvas>
    </div>
    {{ data }}
  </template>
  
  <script>
  import { Chart } from 'chart.js/auto';
  
  export default {
    props: ['data', 'options', 'chartId'],
    mounted() {
      this.renderChart();
    },
    methods: {
      renderChart() {
        const ctx = document.getElementById(this.chartId).getContext('2d');
        if (this.chartInstance) {
          this.chartInstance.destroy();
        }
        this.chartInstance = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: ['Not Attempted', 'Below', 'Approaching', 'Proficient', 'Mastered'],
            datasets: [
              {
                backgroundColor: ['#dd7e6bff', '#f6b26bff', '#ffd966ff', '#93c47dff', '#93caef'],
                data: this.data,
              },
            ],
          },
          options: this.options,
        });
      }
    },
    beforeUnmount() {
      if (this.chartInstance) {
        this.chartInstance.destroy();
      }
    }
  };
  </script>
  