<template>

  <div id="capstone-app">
    <div id="top-nav">NAV</div>
    <img src="src/assets/informative_banner_logo.png"/>
     
  
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
  grid-area: bottom-nav;

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
 display: grid;
 grid-template-columns: auto;
 grid-template-areas: 
 "nav logo"
 "router router"
 "bottom-nav bottom-nav"
 ;
  padding: 5px;

}
#r-view{
  grid-area: router;
}
</style>
