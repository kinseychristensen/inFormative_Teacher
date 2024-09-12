    <template>
        <div :class="theClass(score.score)">{{ level}} </div>
        </template>
        
        <script>
    
        import ScoreService from '../services/ScoreService';
    
    
    
        export default {
        props: ['studentId', 'lessonId', 'notAttempted', 'below', 'approaching', 'proficient', 'mastered'],
        components: {
           
          },
        data (){
            return {
                isLoading: true,
                score: {},
                level: '',
            };
        },
        methods: {
            async retrieveTopScore(){
            try {
              this.isLoading = true;
              const response = await ScoreService.getTopScore(this.studentId, this.lessonId);
              this.score = response.data;
            }catch (error) {
              this.handleError(error, 'retrieving');
            }finally {
                if(this.score.score == null){
                    this.score.score = 0}
              if(this.score.score <= this.notAttempted){
                this.level = 'Not Attempted';
              }else if (this.score.score <= this.below){
                this.level = "Below";
              }else if (this.score.score <= this.approaching){
                this.level = "Approaching";
              }else if (this.score.score <= this.proficient){
                this.level = "Proficient";
              }else {
                this.level = "Mastered";
              }
              }
            }, 

            theClass(colorVal){
                if(colorVal <= this.notAttempted)return "red";
                else if(colorVal <= this.below) return "orange";
                else if(colorVal <= this.approaching) return "yellow";
                else if(colorVal <= this.proficient) return "green";
                else return "blue";
            },

        },
        
        created(){
            this.retrieveTopScore();
        
        }
        
    }
    </script>
    <style scoped>

.red{
  background-color: #dd7e6bff;
}
.orange{
  background-color: #f6b26bff;
}
.yellow{
  background-color: #ffd966ff;
}
.green{
  background-color: #93c47dff;
}
.blue{
    background-color: #93caef;
}


</style>