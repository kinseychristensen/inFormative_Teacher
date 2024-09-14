<template>

    <div class ="root-view-container">
     
      <div id="root-loop-view"> 
          <h1>Welcome to inFormative!  
          </h1>
          <nav class="button-flex">
            <button class="about-button" @click="showAboutUs">About Us</button>
            <button class="about-button" @click="showOurStory">Our Story</button>
            <button class="about-button" @click="showWhy">Why inFormative?</button>
          </nav>
        
        <div id="about-us" v-if="aboutUs">
          <h3>About Us</h3>
            <p>We are passionate about helping educators, coaches, and therapists track meaningful, formative data in a simple and organized way.

            </p><p>At inFormative, we understand that formative data is often overlooked. This "soft data" captures the heart of student learning, 
          which goes beyond formal assignments and assessments. Whether through observations, group work, one-on-one feedback, exit tickets, or 
          session notes, formative data gives a clearer picture of student progress and mastery.

           </p><p>Our platform is designed with flexibility in mind, allowing teachers to effortlessly track student mastery toward their chosen 
          indicators. inFormative adapts to both elementary and secondary levels, supporting multiple subjects and shared classes. Whether you’re
           looking for insights on an individual student, a whole class, or small groups, our system generates clear and useful data reports that 
           can drive instruction forward.
          </p>
        </div>
        <div id="our-story" v-if="ourStory">
            <h3>Our Story</h3>
          <div id="story-flex">
            <p id="story-text">inFormative was created by a pair of sisters who are passionate about education and technology. One is a current fourth-grade teacher and 
          reading specialist, while the other is a former high school English teacher who transitioned into web development. Together, we’ve combined our 
          expertise to build a tool that bridges the gap between teaching and tech, empowering educators with the tools they need to support every student’s 
          growth.</p>
           <img src="/src/assets/founder.png" alt="image of two sisters" id="story-pic"/>
          </div>
        </div>
     
        <div id="why-choose" v-if="whyChoose">
          <h3>Why Choose inFormative?</h3>
          <ul>
            <li>Tailored for tracking formative data that matters.</li>
            <li>Easily share subjects across multiple classes.</li>
            <li>Generate data reports by individual student, class, or small group.</li>
            <li>Designed to be teacher-friendly for all grade levels.</li>
            <li>Free for teachers to join!</li>
          </ul></div>
        </div>
     

        <div id="login-flex-buttons">
          <h3>Interested in More?</h3>
          <p>While teachers, coaches, and therapists can join for free, we also offer district-specific servers for schools and districts that want a 
            custom solution. If your school or district is interested, feel free to reach out to us directly!</p>

        <p>Join us on this journey to make formative data meaningful and actionable.</p>


          <div class="button-flex">
          <router-link class="register-link, button" v-bind:to="{ name: 'register' }">Sign up</router-link>
          <router-link class="login-link, button" v-bind:to="{ name: 'login' }">Log in</router-link>
          </div>
        </div>
       </div>
      

    
  </template>
  
  <script>

  
  export default {
    components: {},
    data() {
      return {
        aboutUs: true,
        ourStory: false,
        whyChoose: false,

      };
    },
    methods: {
      showAboutUs(){
        this.aboutUs = true;
        this.ourStory = false;
        this.whyChoose = false;
      },
      showOurStory(){
        this.aboutUs = false;
        this.ourStory = true;
        this.whyChoose = false;
      },
      showWhy(){
        this.aboutUs = false;
        this.ourStory = false;
        this.whyChoose = true;
      },
    
    }
  };
  </script>
  
  <style scoped>
 .button{
    margin: auto;
    width: 170px;
    padding: 2px;
    height: 30px;
    border: black;
    background-color: rgb(241, 214, 11);
    color: black;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
    transition: background-color .3s ease;
    font-weight: bold; 
    gap: 5px;
    text-decoration: none;
    text-align: center;
  
  }
  #story-flex{
    display: flex;
    align-items: center;
    flex-direction: column;
    flex-wrap: nowrap;
  }
  #story-pic{
    border-radius: 15%;
    max-width: 200px;
    height: auto;
  }
  .about-button {
    margin: auto;
    max-width: 170px;
    padding: 2px;
    height: 30px;
    border: black;
    background-color: rgb(241, 214, 11);
    color: black;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
    transition: background-color .3s ease;
    font-weight: bold; 
    gap: 5px;
    text-decoration: none;
  }
  
  button.btn:hover{
    background-color: yellow;
  }
  .button-flex{
    display: flex;
    gap: 5px;
  }
  .register-link {
    text-align: center;
    margin-top: 20px;
  }
  .register-link a{
    color: blue;
  }
  .register-link a:hover{
    text-decoration: dashed;
  }


  @media screen and (max-width: 600px) {
  .root-view-container{
    justify-content: center;
      display: grid;
      grid-template-columns: 1fr;
      max-width: 600px;
    }
  #root-loop-view{
    background: rgb(255, 255, 255);
    padding: 2rem;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    text-align: left; 
    margin-top: 10px;
    margin-bottom: 10px;
  }
  #login-flex-buttons{
    text-align: center;
    max-width: 350px;
    padding: 20px;
    background-color: #93caef;
    margin: 2px;
    border-radius: 15%;
  
}

}
  @media screen and (min-width: 601px){
  .root-view-container{
   max-width: 1000px;
    margin: auto;
    display: grid;
    grid-template-columns: 2fr 1fr;
    grid-template-areas: 
    "about ."
    "about more"
    "about ."
    ;
    max-width: 100vw;
    
    margin: 10px;
    vertical-align: middle;
  } 
  #root-loop-view{
    max-width: 600px;
    grid-area: about;
  }
  #login-flex-buttons{
  
  width: 350px;
    padding: 20px;
    background-color: #93caef;
    border-radius: 15%;
    grid-area: more;
    
  }
   
  }
  
  </style>