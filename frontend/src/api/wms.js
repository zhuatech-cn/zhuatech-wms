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
export const checkWaveRelease = payload => {
  if (!demo) return http.post('/wms/wave-release-check', payload)
  const completionRate = payload.totalPieces ? Number((payload.pickedPieces / payload.totalPieces * 100).toFixed(1)) : 0
  const blocked = !payload.dockReady || payload.exceptionTasks >= 3 || (payload.minutesToCutoff <= 30 && completionRate < 70)
  const risk = payload.exceptionTasks > 0 || payload.minutesToCutoff <= 45 || completionRate < 95
  const decision = blocked ? 'BLOCKED' : risk ? 'RISK' : 'READY'
  const actions = []
  if (!payload.dockReady) actions.push('确认集货月台和装车资源后再释放')
  if (payload.exceptionTasks > 0) actions.push('清零异常任务或指定主管接受风险')
  if (completionRate < 95) actions.push('按截单时间优先完成剩余拣选与复核')
  if (!actions.length) actions.push('波次满足发运条件，可进入装车交接')
  return result({ waveNo: payload.waveNo, completionRate, remainingPieces: payload.totalPieces - payload.pickedPieces, decision, actions, releasable: decision === 'READY' })
}
