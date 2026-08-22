/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import { defineStore } from 'pinia'
import http from '../api/http'

export const useAuthStore = defineStore('auth', {
  state: () => ({ token: localStorage.getItem('zhuatech_wms_token'), user: JSON.parse(localStorage.getItem('zhuatech_wms_user') || 'null') }),
  actions: {
    async login(username, password, mode = 'work') {
      if (import.meta.env.VITE_DEMO_MODE === 'true') {
        this.token = 'demo-token'
        this.user = mode === 'admin'
          ? { username: 'supervisor', fullName: '周主管', role: 'SUPERVISOR', warehouse: '上海嘉定一号仓' }
          : { username: 'operator', fullName: '陈师傅', role: 'OPERATOR', warehouse: '上海嘉定一号仓' }
      } else {
        const data = await http.post('/auth/login', { username, password })
        this.token = data.token; this.user = data.user
      }
      localStorage.setItem('zhuatech_wms_token', this.token)
      localStorage.setItem('zhuatech_wms_user', JSON.stringify(this.user))
    },
    logout() { this.token = null; this.user = null; localStorage.removeItem('zhuatech_wms_token'); localStorage.removeItem('zhuatech_wms_user') },
  },
})
