<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const route = useRoute(); const router = useRouter(); const auth = useAuthStore()
const nav = [
  { label: '运营总览', path: '/admin/dashboard', code: 'OV' },
  { label: '入库管理', path: '/admin/inbound', code: 'IN' },
  { label: '出库管理', path: '/admin/outbound', code: 'OUT' },
  { label: '库存中心', path: '/admin/inventory', code: 'ST' },
  { label: '任务调度', path: '/admin/tasks', code: 'WK' },
  { label: '仓库建模', path: '/admin/warehouse', code: 'WH' },
  { label: '绩效报表', path: '/admin/reports', code: 'RP' },
]
const current = computed(() => nav.find((item) => item.path === route.path)?.label || '仓储管理')
function logout() { auth.logout(); router.push('/login') }
</script>

<template>
  <div class="admin-shell">
    <aside class="admin-sidebar">
      <div class="admin-brand"><span class="brand-mark">ZH</span><div><strong>ZhuaTech WMS</strong><small>仓储运营管理台</small></div></div>
      <div class="warehouse-switch"><span>当前仓库</span><strong>上海嘉定一号仓</strong><i>切换</i></div>
      <nav class="admin-nav">
        <p>仓储作业</p>
        <router-link v-for="item in nav.slice(0,5)" :key="item.path" :to="item.path" :class="{ active: route.path === item.path }">
          <em>{{ item.code }}</em><span>{{ item.label }}</span><b v-if="item.path === '/admin/tasks'">12</b>
        </router-link>
        <p>基础与分析</p>
        <router-link v-for="item in nav.slice(5)" :key="item.path" :to="item.path" :class="{ active: route.path === item.path }">
          <em>{{ item.code }}</em><span>{{ item.label }}</span>
        </router-link>
      </nav>
      <div class="sidebar-user">
        <span class="avatar">周</span><div><strong>周主管</strong><small>仓储主管</small></div><button @click="logout" title="退出登录">退出</button>
      </div>
    </aside>
    <section class="admin-stage">
      <header class="admin-topbar">
        <div><span class="crumb">仓储运营 /</span><strong>{{ current }}</strong></div>
        <div class="topbar-actions"><label class="global-search"><span>⌕</span><input placeholder="搜索单号、SKU、库位" /></label><span class="shift-pill"><i></i> 白班运行中</span><button class="plain-button">消息 <b>3</b></button><span class="date-label">07 月 27 日 · 周一</span></div>
      </header>
      <main class="admin-content"><router-view /></main>
    </section>
  </div>
</template>
