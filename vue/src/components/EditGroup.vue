<template>
    <div>
        <form id="edit-group-form" v-on:submit.prevent="saveEdits">
          <label for="new-group-name">Group Name:</label>
          <input type="text" v-model="editGroup.groupName" id="new-group-name"/>
          <label for="new-group-desc">Group Description:</label>
          <textarea id="new-group-desc" v-model="editGroup.description"></textarea>
          <label for="group-color" id="group-color-label">Subject Color:</label>
          <select id="group-color" v-model="editGroup.color">
            <option v-for="color in colors" v-bind:key="color.val" v-bind:value="color.val">{{color.colorName}}</option>
            
          </select>

          <button>Save Changes</button>
        </form>
    </div>
</template>
<script>

import GroupService from '../services/GroupService';

export default {
    props: ['group'],

    data () {
        return {
            createdGroup: {},
            editGroup: {
                groupId: 0,
                groupName: '',
                description: '',
                subjectId: 0,
                classId: 0,
                color: 0,
            },
            isLoading: false,
          
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
              "Error " + verb + " group. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " group. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " group. Request could not be created.");
          }
        },

        
       async  saveEdits(){
            try{
                this.isLoading = true;
                const response = await GroupService.editGroup(this.editGroup, this.group.groupId);
                this.createdGroup = response.data;
            }catch (error) {
                this.handleError(error, 'editing');
            }finally {
                this.$router.go(0);
            }

        },
    },
    created(){
        this.editGroup.classId = this.group.classId;
                this.editGroup.subjectId = this.group.subjectId;
                this.editGroup.groupId = this.group.groupId;
                this.editGroup.groupName = this.group.groupName;
                this.editGroup.color = this.group.color;
                this.editGroup.description = this.group.description;
      
    }


}


</script>

<style>

#edit-group-form{
  display: flex;
  flex-direction: column;
  padding: 15px;
  gap: 3px;
  flex-wrap: nowrap;
}
</style>