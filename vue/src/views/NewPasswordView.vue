<template>
 <div class="reset-container">
    <div class="reset-frame">
      <img src="@/assets/inFormativeLogo.png" alt="Logo" class="form-logo" /> 
      
      <h1 class="register-title">Password Reset</h1>
    
      <form class="form-input-group" id="email" v-on:submit.prevent="checkEmail" v-if="starting">
        <label for="username" class="bold-label">Email Address:  </label>  <input type="text" id="username" v-model="userEmail" required/>
        <p></p>
        <button type="submit" class="btn">Send Reset Code</button>
      
    </form>
    <p></p><p></p>
      <form class="form-input-group" id="confirmation" v-on:submit.prevent="verifyCode" v-if="emailSent">
        <label for="con-code" class="bold-label">Confirmation Code</label>
        <input type="text" id="con-code" v-model="confirmationCode" required />
     
      
        <p></p><p></p>
      <button type="submit" class="btn">Verify</button>
      </form>
    
<p></p><p></p>
      <form class="form-input-group" id="new-password" v-on:submit.prevent="updatePassword" v-if="confirmationPassed">
        <label for="password" class="bold-label">New Password:</label>
        <input type="password" id="password" v-model="user.password" required />
     
        <label for="confirmPassword" class="bold-label">Confirm Password:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
        <p></p><p></p>
      <button type="submit" class="btn">Save New Password</button>
      </form>
      <p>{{ errorMessage }}</p>
      <button  v-if="showLogin" class="btn"><router-link id="loginlink" v-bind:to="{ name: 'login' }">Proceed to Log in.</router-link></button>
      
  </div>

</div>

</template>

<script>
import authService from '../services/AuthService';
import emailjs from 'emailjs-com';

export default {
  data() {
    return {
        userEmail: '',
        errorMessage: '',
        starting: true,
        emailSent: false,
        confirmationPassed: false,
        confirmationCode: '',
        sentCode: '',
        confirmPassword: '',
        showLogin: false,
      user: {
        id: 0,
        username: '',
        password: '',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      serviceId: 'service_m0f0fzn',
      templateId: 'template_du2hefc',
      publicKey: '4SKTEiyCvMDGCUS_r',
    };
  },
 
  methods: {
    sendReset(){
        console.log('HERE')
            emailjs.send(this.serviceId, this.templateId, {
                email: this.userEmail,
                message: this.sentCode,
            }, this.publicKey)
            .then((response) => { 
                console.log("Email successfully sent!", response.status, response.text)
            },
     (error) => {
        console.log('Failed to send email:', error)
     })   
        console.log("THERE");
        this.emailSent = true;
        this.starting = false;
    },

    generateCode(){
        this.sentCode = '';
        const array = new Int8Array(6);
        self.crypto.getRandomValues(array);
        for(let thisNum of array){
            if(thisNum < 0){
                thisNum = thisNum * -1;}
                thisNum = (thisNum % 10);
                this.sentCode = this.sentCode + thisNum;
        } console.log(this.sentCode);
        this.sendReset();
    },

    async checkEmail (){
        this.errorMessage = "";
        try {
            const response = await authService.getId(this.userEmail);
            this.user.id= response.data;
        }catch (error) {
        this.handleError(error, 'cloning');
    }finally {
        if(this.user.id === 0){
            this.errorMessage="User not found.";
            this.emailSent = false;
        }else {
            this.generateCode();
        }
    }},

    verifyCode(){
        if(this.confirmationCode === this.sentCode){
            this.emailSent = false;
            this.confirmationPassed = true;   
            this.errorMessage = '';  
        }else {
            this.errorMessage = "That code does not match."
        }
       

    },


    async updatePassword() {
      if (this.user.password != this.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        try{
        const response = await authService.updatePassword(this.user, this.user.id)
          
            this.errorMessage = "Your password has been updated.";
            this.showLogin = true;
       
        
            }catch(error) {
            const response = error.response;
            }finally {
               this.confirmationPassed = false;
             
            }    
      }
    },

    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems saving the new password.';
    },
  },
};
</script>


<style scoped>
.reset-container{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75vh;
  background-color: rgb(245, 222, 123);

}
.reset-frame{
  background: rgb(255, 255, 255);
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;  
  text-align: center;
}

.register-title{
  margin-bottom: 1.5rem;
  font-size: 30px;
  text-align: center;
}
.form-input-group{
  margin-bottom: 10px;
}

label{
  display: block;
  margin-bottom: 2.5px;
}
.bold-label{
  font-weight: bold;
}

button.btn{

  width: 170px;
  padding: 2px;
  height: 30px;
  border: black;
  background-color: rgb(255, 217, 102,100);
  color: black;
  border-radius: 5px;
  font-size: 15px;
  cursor: pointer;
  transition: background-color .3s ease;
  font-weight: bold;  
}

button.btn:hover{
  background-color: yellow;
}

.login-link {
  text-align: center;
  margin-top: 20px;
}
.login-link a{
  color: blue;
}
.login-link a:hover{
  text-decoration: dashed;
}
img{
  width: 300px;
  
}
#loginlink {
    text-decoration: none;
}

</style>
