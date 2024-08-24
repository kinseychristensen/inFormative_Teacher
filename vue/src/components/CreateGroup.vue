<template>
    <div>
        <form id="add-group-form" v-on:submit.prevent="addNewGroup">
          <label for="new-group-name">Group Name:</label>
          <input type="text" v-model="newGroup.groupName" id="new-group-name"/>
          <label for="new-group-desc">Group Description:</label>
          <textarea id="new-group-desc" v-model="newGroup.description"></textarea>
          <label for="group-color" id="group-color-label">Subject Color:</label>
          <select id="group-color" v-model="newGroup.color">
            <option v-for="color in colors" v-bind:key="color.val" v-bind:value="color.val">{{color.colorName}}</option>
            
          </select>

          <button>Create Group</button>
        </form>
    </div>
</template>
<script>

import GroupService from '../services/GroupService';

export default {
    props: ['classId', 'subjectId'],

    data () {
        return {
            createdGroup: {},
            isLoading: false,
            newGroup: {
          groupName: '',
          description: '',
          classId: 0,
          subjectId: 0,
          color: 0,
        },
        colors: [
        {val: 1, colorName: 'Pink'},
        {val: 2, colorName: 'Red'},
        {val: 3, colorName: 'Orange'},
        {val: 4, colorName: 'Yellow'},
        {val: 5, colorName: 'Green'},
        {val: 6, colorName: 'Blue'},
        {val: 7, colorName: 'Purple'},
        {val: 8, colorName: 'Gray'},
       ]
        };
    },
    methods: {
        handleError(error, verb) {
          if (error.response) {
            this.$store.commit('SET_NOTIFICATION',
              "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " new group. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " new group. Request could not be created.");
          }
        },

        
       async  addNewGroup(){
            this.newGroup.classId = this.classId;
            this.newGroup.subjectId = this.subjectId;

            try{
                this.isLoading = true;
                const response = await GroupService.createGroup(this.newGroup);
                this.createdGroup = response.data;
            }catch (error) {
                this.handleError(error, 'creating');
            }finally {
                this.$router.go(0);
            }

        }
    },


}


</script>

<style>

#add-group-form{
  display: flex;
  flex-direction: column;
  padding: 15px;
  gap: 3px;
}
</style>