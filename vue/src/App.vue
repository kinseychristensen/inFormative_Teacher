<template>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <div id="capstone-app">
   
    <div id="banner">
     
    <img  class="big-logo" src="@/assets/desktop_pixel_logo.png" alt="inFormative Logo"/>
    <img  class="small-logo" src="@/assets/phone_dots.png" alt="inFormative Logo"/>
  </div>
  <div id="nav" v-if="this.$store.state.token != ''">
   <NavTool/>

  </div>
     
    <div class="r-view">
    <router-view :key="route.fullPath"/>
    </div>
    
    <div id="nav-2">
      
      <router-link v-bind:to="{ name: 'contact' }">Contact Us</router-link>  | 
      <div><a href="https://www.freepik.com/free-vector/green-apple-fruit-white-background-icon-isolated_169502436.htm#page=2&query=green%20apple%20png&position=1&from_view=keyword&track=ais_hybrid&uuid=d26b36df-1546-40bd-9fff-4953491c108b">Apple Icon by studiogstock on Freepik</a></div>
       | 
      <router-link v-bind:to="{ name: 'contact' }">Donations Welcome</router-link> 
     
    </div>
  
  </div>
</template>
<script>
import NavTool from '@/components/NavTool.vue';
import { useRoute } from 'vue-router';


export default {
components: {
  NavTool
},

data(){
  return{
    route: useRoute()
  }
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
.r-view{
 width: 100vw;
height: auto;
 
}


.small-logo, .big-logo {
  width: 100vw;
  height: auto;
}

@media screen and (min-width: 601px){
.small-logo{
  display: none;
}
}

@media screen and (max-width: 600px) {
  
.big-logo {
  display: none;
}
}

</style>
