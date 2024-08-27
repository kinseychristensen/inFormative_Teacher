<template>
    <div class = 'flexed'>
    <div class = root-container>
     <div id="root-view">
        <img src="@/assets/inFormativeLogo.png" alt="Logo" class="form-logo" />
        <p>About Us Blurb</p>
       <div id="button-flex">
        <router-link class="register-link, button" v-bind:to="{ name: 'register' }">Sign up</router-link>
       <router-link class="login-link, button" v-bind:to="{ name: 'login' }">Log in</router-link>
    </div>
    </div>
    </div>
  </div>
    
  </template>
  
  <script>

  
  export default {
    components: {},
    data() {
      return {
        
      };
    },
    methods: {
      
    }
  };
  </script>
  
  <style scoped>
  .root-container{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 75vh;
    background-color: rgb(245, 222, 123)
  
  }
  #root-view{
    background: rgb(255, 255, 255);
    padding: 2rem;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    width: 100%;
    max-width: 400px;  
    text-align: center; 
  }
  
  .login-title{
    margin-bottom: 1.5rem;
    font-size: 30px;
    text-align: center;
  }
  .form-input-group{
    margin-bottom: 10px;
  }
  
  label{
    display: block;
    margin-bottom: 2.5px;
  }
  .bold-label{
    font-weight: bold;
  }
  
  .button{
    margin: auto;
    width: 170px;
    padding: 2px;
    height: 30px;
    border: black;
    background-color: rgb(241, 214, 11);
    color: black;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
    transition: background-color .3s ease;
    font-weight: bold; 
    gap: 5px;
    text-decoration: none;
  
  }
  
  button.btn:hover{
    background-color: yellow;
  }
  #button-flex{
    display: flex;
    gap: 5px;
  }
  .register-link {
    text-align: center;
    margin-top: 20px;
  }
  .register-link a{
    color: blue;
  }
  .register-link a:hover{
    text-decoration: dashed;
  }
  img{
    width: 300px;
    
  }
  
  
  </style>