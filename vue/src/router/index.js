import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'


const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/login",
    children:[
      {
        path:'addclass',
        name:'AddClass',
        component:()=>import("@/views/AddClass"),
      },
      {
        path:'deleteclass',
        name:'DeleteClass',
        component:()=>import("@/views/DeleteClass"),
      },
      {
        path:'grades',
        name:'Grades',
        component:()=>import("@/views/Grades"),
      },
      {
        path:'addlesson',
        name:'AddLesson',
        component:()=>import("@/views/AddLesson"),
      },
      {
        path:'classmanage',
        name:'ClassManage',
        component:()=>import("@/views/ClassManage"),
      },
      {
        path:'gradeinput',
        name:'GradeInput',
        component:()=>import("@/views/GradeInput"),
      },
      {
        path:'gradeup',
        name:'GradeUp',
        component:()=>import("@/views/GradeUp"),
      },
      {
        path:'gradeview',
        name:'GradeView',
        component:()=>import("@/views/GradeView"),
      },
      {
        path:'classschedule',
        name:'ClassSchedule',
        component:()=>import("@/views/classSchedule"),
      },
      {
        path:'studentlist',
        name:'StudentList',
        component:()=>import("@/views/studentList"),
      },
    ]
  },
  {
    path: '/login',
    name:'Login',
    component: ()=>import("@/views/Login")//整点活
  },
  {
    path: '/register',
    name:'Register',
    component: ()=>import("@/views/Register")
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
