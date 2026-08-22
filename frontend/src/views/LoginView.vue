<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter(); const auth = useAuthStore()
const mode = ref('work'); const username = ref('operator'); const password = ref('Demo@2026'); const loading = ref(false); const error = ref('')
function setMode(value) { mode.value = value; username.value = value === 'admin' ? 'supervisor' : 'operator'; password.value = 'Demo@2026' }
async function login() {
  loading.value = true; error.value = ''
  try { await auth.login(username.value, password.value, mode.value); router.push(mode.value === 'admin' ? '/admin/dashboard' : '/work/home') }
  catch { error.value = '账号或密码不正确，请检查后重试' } finally { loading.value = false }
}
</script>

<template>
  <div class="login-page">
    <section class="login-context">
      <div class="login-brand"><span>ZH</span><div><strong>ZhuaTech WMS</strong><small>知华科技仓储管理系统</small></div></div>
      <div class="warehouse-illustration" aria-hidden="true">
        <div class="rack rack-a"><i v-for="n in 12" :key="n"></i></div><div class="aisle"><span>01</span><b></b><span>02</span></div><div class="rack rack-b"><i v-for="n in 12" :key="n"></i></div>
        <div class="forklift"><i></i><span></span><b></b></div>
      </div>
      <div class="context-copy"><p>从到货预约到复核出库</p><h1>让每一次库内移动<br/>都有任务、有位置、有结果。</h1><ul><li>批次与效期追踪</li><li>移动扫描作业</li><li>实时库存与任务调度</li></ul></div>
      <footer>上海如静知华信息科技有限公司 · www.zhuatech.cn</footer>
    </section>
    <section class="login-panel">
      <div class="login-box">
        <p class="eyebrow">WELCOME BACK</p><h2>登录仓储系统</h2><p class="login-hint">选择工作入口并使用分配给您的仓库账号。</p>
        <div class="mode-tabs"><button :class="{ active: mode === 'work' }" @click="setMode('work')">仓库作业端</button><button :class="{ active: mode === 'admin' }" @click="setMode('admin')">运营管理端</button></div>
        <label><span>账号</span><input v-model="username" autocomplete="username" /></label>
        <label><span>密码</span><input v-model="password" type="password" autocomplete="current-password" @keyup.enter="login" /></label>
        <div class="login-row"><label class="remember"><input type="checkbox" checked /> 记住当前账号</label><a href="https://www.zhuatech.cn/" target="_blank">需要帮助？</a></div>
        <p v-if="error" class="form-error">{{ error }}</p><button class="login-submit" :disabled="loading" @click="login">{{ loading ? '正在登录…' : (mode === 'admin' ? '进入运营管理端' : '进入仓库作业端') }}</button>
        <div class="demo-note"><strong>演示账号</strong><span>{{ mode === 'admin' ? 'supervisor' : 'operator' }} / Demo@2026</span></div>
        <p class="license-note">社区源码版仅限个人非商业学习交流，商用需获得书面授权。</p>
      </div>
    </section>
  </div>
</template>
