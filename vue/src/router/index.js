import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import ClassView from '../views/ClassView.vue';
import SubjectView from '../views/SubjectView.vue';
import CreateClassView from '../views/CreateClassView.vue';
import CreateSubjectView from '../views/CreateSubjectView.vue';
import GroupsView from '../views/GroupsView.vue';
import ArchiveView from '../views/ArchiveView.vue';
import ProfileView from '../views/ProfileView.vue';
import StudentView from '../views/StudentView.vue';
import RosterView from '../views/ClassRosterView.vue';


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    
  },
  {
    path: "/class/:classId",
    name: "class-page",
    component: ClassView,
  },
  {
    path: "/subject/:subjectId",
    name: "subject-page",
    component: SubjectView,
  },  
  {
    path: "/subject/new",
    name: "create-subject",
    component: CreateSubjectView,
  },  
  {
    path: "/class/new",
    name: "create-class",
    component: CreateClassView,
  }, 
  {
    path: "/class/:classId/groups",
    name: "groups",
    component: GroupsView,
  },
  {
    path: "/archive",
    name: "archive",
    component: ArchiveView,
  },
  {
    path: "/profile",
    name: "profile",
    component: ProfileView,
  },
  {
    path: "/student/:studentId",
    name: "student",
    component: StudentView,
  },
  {
    path: "/class/:classId/roster",
    name: "roster",
    component: RosterView,
  },


];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;