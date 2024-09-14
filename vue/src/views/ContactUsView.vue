<template>

<div class = "home-title-view">
          <h1 class="page-title">Contact Our Team</h1>
          
        </div>
    <div class="contact-us-body">
        <div class="contact">
            <p class="logged-in-title">We love feedback! Please send us requests and recommendations for how to improve.</p>
        <ContactForm/>
    </div>
        <div class="donate">
           <p> Running a website sadly is not free.  If you would like to <a class="donate-button" h-ref="https://account.venmo.com/u/kittheavenger">donate to maintaince costs</a>, please consider sending funds.  
            We are very grateful for your support!</p>
            <img src="/src/assets/IMG_0623.jpg" alt="venmo-code" id="donate-pic"/>
            </div>
    </div>
    
    

</template>
<script> 


import ContactForm from '../components/ContactForm.vue';
export default {
    name: 'ContactUs',
    components: {
        ContactForm,
    }
    
}
    </script>
    <style>
    .home-title-view{
        text-align: center;
    }
    .contact-us-body{
        max-width: 1000px;
        margin: auto;
        display: grid;
        grid-template-columns: 1fr 350px;

    }
    .donate{
        background-color: #b2c8a5;
        border-radius: 15px;
        padding: 10px;
        justify-content: center;
        max-width: 320px;

    }
   
    #donate-pic{
        max-width: 300px;
        margin: auto;
        padding: 10px;
    }
    .donate-button{
        text-decoration: underline;
        color: blue;
    }
    @media screen and (max-width: 600px) {
        .contact-us-body{
        max-width: 1000px;
        margin: auto;
        display: grid;
        grid-template-columns: 1fr;

    }
    }

</style>