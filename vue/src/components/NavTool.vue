<template>
  <div class="nav-tool">
    <router-link class="nav-button" v-bind:to="{ name: 'home' }">Classes</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'all-students' }">Students</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'archive' }">Archive</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'profile' }">Settings</router-link>
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
  flex-direction: row;
  justify-content: space-between;
  width: 66vw;
}

.nav-button {
  background-color:  #d9d9d9ff;
  color: black;
  text-align: center;
  margin: 10px;
  padding: 15px;
  width: 150px;
  border-radius: 15px;
  text-decoration: none;
}

.nav-button:hover {
  background-color:  #93caef;;
}

@media screen and (max-width: 600px) {
  .nav-button {
  background-color:  #d9d9d9ff;
  color: black;
  text-align: center;
  margin: 5px;
  padding: 5px;
  width: 150px;
  border-radius: 15px;
  text-decoration: none;
  vertical-align: center;
}



}

</style>