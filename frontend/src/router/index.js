/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MobileShell from '../components/MobileShell.vue'
import AdminShell from '../components/AdminShell.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginView },
  { path: '/work', component: MobileShell, children: [
    { path: '', redirect: '/work/home' },
    { path: 'home', component: () => import('../views/mobile/MobileHome.vue') },
    { path: 'tasks', component: () => import('../views/mobile/MobileTasks.vue') },
    { path: 'scan', component: () => import('../views/mobile/MobileScan.vue') },
    { path: 'profile', component: () => import('../views/mobile/MobileProfile.vue') },
  ] },
  { path: '/admin', component: AdminShell, children: [
    { path: '', redirect: '/admin/dashboard' },
    { path: 'dashboard', component: () => import('../views/admin/AdminDashboard.vue') },
    { path: 'inbound', component: () => import('../views/admin/AdminInbound.vue') },
    { path: 'outbound', component: () => import('../views/admin/AdminOutbound.vue') },
    { path: 'inventory', component: () => import('../views/admin/AdminInventory.vue') },
    { path: 'tasks', component: () => import('../views/admin/AdminTasks.vue') },
    { path: 'warehouse', component: () => import('../views/admin/AdminWarehouse.vue') },
    { path: 'reports', component: () => import('../views/admin/AdminReports.vue') },
  ] },
]
const router = createRouter({ history: createWebHistory(), routes, scrollBehavior: () => ({ top: 0 }) })
router.beforeEach((to) => {
  if (to.path === '/login' || import.meta.env.VITE_DEMO_MODE === 'true' || localStorage.getItem('zhuatech_wms_token')) return true
  return '/login'
})
export default router
