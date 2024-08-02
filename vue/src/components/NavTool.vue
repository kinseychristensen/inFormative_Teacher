<template>
  <div class="nav-tool">
    <router-link class="nav-button" v-bind:to="{ name: 'home' }">Home</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'profile' }">Settings</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'archive' }">Archive</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'logout' }">Logout</router-link>
  </div>
</template>

<script>
export default {
  name: 'NavTool'
}
</script>

<style scoped>
.nav-tool {
  display: flex;
  flex-direction: column;
  width: 200px; 
}

.nav-button {
  background-color: #f1c232ff;
  color: black;
  text-align: center;
  margin: 10px;
  padding: 35px;
  border-radius: 25px;
  text-decoration: none;
}

.nav-button:hover {
  background-color: rgb(4, 157, 217, 1);
}
</style>