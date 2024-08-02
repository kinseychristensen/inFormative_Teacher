<template>

  <div id="capstone-app">
    <router-view />
    <div id="nav">
      
      <router-link v-bind:to="{ name: 'home' }">Contact Us</router-link>&nbsp;&nbsp;
     
    </div>
  
  </div>
</template>

<style scoped>

#nav {
  display: flex;
  justify-content: space-between;
  background-color: #f8f9fa;
  padding: 10px;
  border-bottom: 1px solid #dee2e6;

}

#nav a {
  color: #007bff;
  text-decoration: none;
}

#nav a:hover {
  text-decoration: underline;
}
div{
  font: bold;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif
}
</style>
