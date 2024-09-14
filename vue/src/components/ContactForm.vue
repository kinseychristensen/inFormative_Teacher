<template>
    <div>
      <form @submit.prevent="sendEmail" id="contact-form">
        <input type="hidden" name="contact_number" value="697483" />
        
        <label for="name">Name</label>
        <input type="text" id="name" v-model="formData.user_name" name="user_name" required />
  
        <label for="email">Your Email</label>
        <input type="email" id="email" v-model="formData.user_email" name="user_email" required />
  
        <label for="message">Message</label>
        <textarea id="message" v-model="formData.message" name="message" required></textarea>
  
        <input type="submit" value="Send" />
      </form>
    </div>
  </template> 
  
  <script>
  import emailjs from '@emailjs/browser';
  
  export default {
    data() {
      return {
        formData: {
          user_name: '',
          user_email: '',
          message: ''
        }
      };
    },
    mounted() {
      // Initialize emailjs service when the component is mounted
      emailjs.init("4SKTEiyCvMDGCUS_r");
    },
    methods: {
      sendEmail() {
        // Send the form using EmailJS
        emailjs.send('service_m0f0fzn', 'template_ogyyech', this.formData)
          .then(() => {
            console.log('SUCCESS!');
            this.clearForm();  // Optional: clear form after success
          }, (error) => {
            console.log('FAILED...', error);
          });
      },
      clearForm() {
        // Clear form fields
        this.formData = {
          user_name: '',
          user_email: '',
          message: ''
        };
      }
    }
  };
  </script>
  
  <style scoped>
  /* Optional: Add some basic styling */
  form {
    display: flex;
    flex-direction: column;
    width: 300px;
  }
  
  input, textarea {
    margin-bottom: 10px;
  }
  
  input[type="submit"] {
    background-color: #4CAF50;
    color: white;
    cursor: pointer;
  }
  
  input[type="submit"]:hover {
    background-color: #45a049;
  }
  #contact-form{
    width: 450px;
  }
  #message{
    height: 200px;
  }
  </style>
  