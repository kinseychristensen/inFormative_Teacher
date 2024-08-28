<template>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <div id="capstone-app">
   
    <div id="banner">
      <div>white space</div>
    <img  id="logo" src="src/assets/desktop_pixel_logo.png" alt="inFormative Logo"/>
  </div>
     
  
    <router-view id="r-view"/>
    
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
  grid-area: nav;
 

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
#capstone-app{
  width: 100%;
  padding: 5px;
  display: grid;
  grid-template-areas: 
  "banner"
  "nav-bar"
    "r-view"
    "nav"
;
}
#r-view{
 padding: 2px;
 grid-area: r-view;
}

#banner{
  
  background-color: white;

grid-area: banner;
display: flexbox;
justify-content: space-between;
flex-direction: row;
justify-self: stretch;
}

</style>
