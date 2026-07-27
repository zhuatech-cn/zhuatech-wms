<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { useRoute } from 'vue-router'
const route = useRoute()
const tabs = [
  { path: '/work/home', label: '工作台', icon: '⌂' },
  { path: '/work/tasks', label: '任务', icon: '≡' },
  { path: '/work/scan', label: '扫描', icon: '⌗', scan: true },
  { path: '/work/profile', label: '我的', icon: '○' },
]
</script>

<template>
  <div class="mobile-shell">
    <main class="mobile-stage"><router-view /></main>
    <nav class="mobile-tabbar">
      <router-link v-for="tab in tabs" :key="tab.path" :to="tab.path" :class="[{ active: route.path === tab.path }, { scan: tab.scan }]">
        <span>{{ tab.icon }}</span><small>{{ tab.label }}</small>
      </router-link>
    </nav>
  </div>
</template>
