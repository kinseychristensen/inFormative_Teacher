<template>
    <div>
      <!-- Bind the canvas for the chart rendering -->
      <canvas :id="chartId" :width="300" :height="300"></canvas>
    </div>
  </template>
  
  <script>
  import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    LineElement,
    CategoryScale,
    PointElement,
    LinearScale
  } from 'chart.js';
  
  // Registering required components from Chart.js
  ChartJS.register(
    Title,
    Tooltip,
    Legend,
    LineElement,
    CategoryScale,
    PointElement,
    LinearScale
  );
  
  export default {
    props: ['data', 'options', 'chartId'],
    data() {
      return {
        chartInstance: null, // Storing the chart instance
      };
    },
    mounted() {
      this.renderChart(); // Render the chart when the component is mounted
    },
    watch: {
      data: {
        handler() {
          console.log('Data changed, re-rendering chart...');
          this.renderChart(); // Re-render the chart when the data changes
        },
        deep: false, // Avoid deep watching for nested objects unless necessary
      }
    },
    methods: {
      renderChart() {
        // Safeguard to ensure the canvas exists before rendering the chart
        const canvas = document.getElementById(this.chartId);
        if (!canvas) {
          console.error('Canvas element not found. Cannot render chart.');
          return;
        }
  
        const ctx = canvas.getContext('2d');
        if (!ctx) {
          console.error('Unable to get 2D context for canvas.');
          return;
        }
  
        // Destroy the previous chart instance if it exists
        if (this.chartInstance) {
          this.chartInstance.destroy();
        }
  
        // Error handling and logging for the Chart.js rendering
        try {
          console.log('Rendering chart with data:', this.data);
          console.log('Rendering chart with options:', this.options);
  
          // Create the scatter chart using Chart.js
          this.chartInstance = new ChartJS(ctx, {
            type: 'scatter', // Scatter chart type
            data: this.data, // Data passed from props
            options: this.options, // Chart options passed from props
          });
  
          console.log('Chart rendered successfully.');
        } catch (error) {
          console.error('Error rendering chart:', error);
        }
      }
    },
    beforeUnmount() {
      // Destroy the chart instance when the component is unmounted
      if (this.chartInstance) {
        this.chartInstance.destroy();
      }
    }
  };
  </script>
  