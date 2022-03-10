import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
//import { compile } from 'vue/types/umd'
//引入echart文件
const echart = () => import('@/views/echart.vue')
const one = () => import('@/views/one.vue')
const two = () => import('@/views/two.vue')
const three = () => import('@/views/three.vue')
const four = () => import('@/views/four.vue')
const login = () => import('@/views/login')
const notfound = () => import('@/views/notfound.vue')
const home = () => import('@/views/home')
const aside = () => import('@/views/aside')
const userA = () => import('@/components/userA.vue')
const userB = () => import('@/components/userB.vue')
const adminA = () => import('@/components/adminA.vue')
const adminB = () => import('@/components/adminB.vue')
const maintain = () => import('@/views/maintain')
const customer = () => import('@/views/customer')
const service = () => import('@/views/service')
const owners = ()=>import('@/views/owners')
//引入card组件
const card = ()=>import('@/components/xb-card.vue')
//引入maintain2
const maintain2 = ()=> import('@/views/maintain2')
//引入maintain3
const maintain3 = () => import('@/views/maintain3')
const newview = () => import('@/views/new.vue')
Vue.use(VueRouter)



const userrules = [
  { path: '/userA', name: 'userA', component: userA },
  { path: '/userB', name: 'userB', component: userB },
]
const adminrules = [
  { path: '/adminA', name: 'adminA', component: adminA },
  { path: '/adminB', name: 'adminB', component: adminB },
]

//角色匹配路由表
const roleToRoutes = {
  'user': userrules,
  'admin': adminrules
}


const routes = [
  { path: '/', name: '首页面', redirect: '/echart' },
  { path: '/login', name: 'login', component: login },
  { path: '/echart', name: 'echart', component: echart },
  { path: '/home', name: 'home', component: home, 
  children: [
    { path: 'maintain', component: maintain, name: 'maintain' }, 
    { path: 'customer', component: customer, name: 'customer' },
    { path: 'service', component: service, name: 'service' }, 
    { path: 'owners', component: owners, name: 'owners' },
    { path:'card',component:card,name:'xb-card' },
    { path:'maintain2',component:maintain2,name:'maintain2'},
    { path:'maintain3',component:maintain3,name:'maintain3'},
    { path:'newview',component:newview,name:'newview'}
  ] 
  },
  { path: '/aside', name: 'aside', component: aside },
  { path: '/one', component: one, name: 'one' },//路由对象
  { path: '/two', component: two, name: 'two' },
  { path: '/three', component: three, name: 'three' },
  { path: '/four', component: four, name: 'four' },

   /* {path:'*',name:'notfound',component:notfound}
   */
]


export function asyncRoutes() {
  let role = store.state.role
  console.log(role)

  let currentRoutes = router.options.routes


  //let  current= currentRoutes.concat(roleToRoutes[role])
  let current = roleToRoutes[role]

  router.addRoutes(current)
  //路由options并不会随着addRouts动态响应，所以要在这里进行设置
  router.options.routes = currentRoutes.concat(current)
  console.log(router, '3636')
}

//
const router = new VueRouter({
  routes
})

/* router.beforeEach((to,from,next)=>{
  if(to.path=='login'){next()}else{
    if(user){next()}else{next('/login')}
  }
}) */



export default router
