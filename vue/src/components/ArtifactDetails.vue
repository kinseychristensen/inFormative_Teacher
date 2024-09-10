<template>

    
      
       <div>{{ artifact.description }}</div>
       
       <div> Date: {{ artifact.assignmentDate }} </div>
      
       <div>Type:  {{ artifact.artifactType }}</div> lesson: {{ artifact.lessonId }}
      
       
        
        
        
        </template>
        
        <script>
    
        import ArtifactService from '../services/ArtifactService';
       
        
        export default {
        props: ['artifactId'],
            
        data (){
            return {
                isLoading: true,
                artifact: {},
    
                
        
            };
        },
        
        
        methods: {
    
            async retrieveArtifact(){
            try {
              this.isLoading = true;
              const response = await ArtifactService.getArtifact(this.artifactId);
              this.artifact = response.data;
            }catch (error) {
              this.handleError(error, 'retrieving');
            }finally {
              this.isLoading = false;
            }
            }, 
    
            
        
        },
        
        created(){
            this.retrieveArtifact();
        
        }
        
        
        }
        
        </script>
        
        <style scoped>
        
        
     
        
        
        
        
        </style>