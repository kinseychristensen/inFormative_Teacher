<template>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <div id="capstone-app">
   
    <div id="banner">
     
    <img  id="big-logo" src="src/assets/desktop_pixel_logo.png" alt="inFormative Logo"/>
    <img  id="small-logo" src="src/assets/phone_dots.png" alt="inFormative Logo"/>
  </div>
  <div id="nav" v-if="this.$store.state.token != ''">
   <NavTool/>

  </div>
     
  
    <router-view id="r-view"/>
    
    <div id="nav-2">
      
      <router-link v-bind:to="{ name: 'home' }">Contact Us</router-link>&nbsp;&nbsp;
     
    </div>
  
  </div>
</template>
<script>
import NavTool from '@/components/NavTool.vue';
export default {
components: {
  NavTool
}
}
</script>

<style scoped>

#nav-2 {
  display: flex;
  justify-content: space-between;
  background-color: #f8f9fa;
  padding: 10px;
  border-bottom: 1px solid #dee2e6;
  margin-top: 20px;
 

}

#nav-2 a {
  color: #007bff;
  text-decoration: none;
}

#nav-2 a:hover {
  text-decoration: underline;
}
div{
  font: bold;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif
}
#capstone-app{
 display: flexbox;
}
#r-view{
 width: 100vw;
height: auto;
 
}


#small-logo, #big-logo {
  width: 100vw;
  height: auto;
}

@media screen and (min-width: 601px){
#small-logo{
  display: none;
}
}

@media screen and (max-width: 600px) {
  
#big-logo {
  display: none;
}
}

</style>
