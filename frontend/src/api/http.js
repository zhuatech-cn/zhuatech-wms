/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import axios from 'axios'

const http = axios.create({ baseURL: '/api', timeout: 12000 })
http.interceptors.request.use((config) => {
  const token = localStorage.getItem('zhuatech_wms_token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})
http.interceptors.response.use((response) => response.data.data, (error) => Promise.reject(error))
export default http
