/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import http from './http'
import * as mock from './mock'

const demo = import.meta.env.VITE_DEMO_MODE === 'true'
const result = (data) => Promise.resolve(data)
export const getDashboard = () => demo ? result(mock.dashboard) : http.get('/wms/dashboard')
export const getTasks = () => demo ? result(mock.tasks) : http.get('/wms/tasks')
export const getMyTasks = () => demo ? result(mock.tasks.filter((item) => item.assignee === '陈师傅')) : http.get('/wms/tasks/mine')
export const getInventory = () => demo ? result(mock.inventory) : http.get('/wms/inventory')
export const getInbounds = () => demo ? result(mock.inbounds) : http.get('/wms/inbounds')
export const getWaves = () => demo ? result(mock.waves) : http.get('/wms/waves')
export const getZones = () => demo ? result(mock.zones) : http.get('/wms/zones')
export const updateTask = (id, payload) => demo ? result({ ...mock.tasks.find((item) => item.id === id), ...payload }) : http.patch(`/wms/tasks/${id}`, payload)
